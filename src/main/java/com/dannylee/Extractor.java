package com.dannylee;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;

import java.util.*;

public class Extractor {


    public Set<Triple> extract(String sentenceStr) {
        Set<Triple> result = new HashSet<Triple>();

        Map<Integer, List<CoNLLWord>> depencyGroupByHead = new HashMap<Integer, List<CoNLLWord>>();
        List<CoNLLWord> depencyList = new ArrayList<>();

        CoNLLSentence sentence = HanLP.parseDependency(sentenceStr);
        for (CoNLLWord word : sentence) {
            if (!depencyGroupByHead.containsKey(word.HEAD.ID)) {
                depencyGroupByHead.put(word.HEAD.ID, new ArrayList<CoNLLWord>());
            }

            depencyGroupByHead.get(word.HEAD.ID).add(word);
            depencyList.add(word);
        }

        for (int head_index : depencyGroupByHead.keySet()) {
            for (CoNLLWord row : depencyGroupByHead.get(head_index)) {
                String w = row.LEMMA;
                String r = row.DEPREL;
                String t = row.HEAD.LEMMA;
                int head = row.HEAD.ID;

                if (r.equals("主谓关系")) {
                    String headWord = depencyList.get(row.HEAD.ID - 1).LEMMA;
                    for (CoNLLWord item : depencyGroupByHead.get(head)) {
                        if (item.DEPREL.equals("动宾关系")) {
                            result.add(new Triple(w, headWord, item.LEMMA));
                        } else if (item.DEPREL.equals("动补结构")) {
                            for (CoNLLWord item2 : depencyGroupByHead.get(head)) {
                                if (item2.DEPREL.equals("右附加关系")) {
                                    for (CoNLLWord item3 : depencyGroupByHead.get(head)) {
                                        if (item3.DEPREL.equals("动宾关系")) {
                                            result.add(new Triple(w, headWord + item.LEMMA + item2.LEMMA, item3.LEMMA));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

}
