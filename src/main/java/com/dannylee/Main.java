package com.dannylee;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    static final String SENTENCE_TXT_PATH = "./resources/sentences.txt";
    static final String OUTPUT_JSON = "./output/result.json";


    public static void main(String[] args) {
        try {
            File outputJson = new File(OUTPUT_JSON);
            if (outputJson.exists()) {
                outputJson.delete();
            }
            FileWriter fw = new FileWriter(outputJson);
            fw.write("[");
            List<String> result = batchExtract();
            for (int i = 0; i <= result.size(); i++) {
                String sentJson = result.get(i);
                fw.write(sentJson);
                if (i % 100 == 0) {
                    fw.flush();
                }
            }
            fw.write("]");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static List<String> batchExtract() {
        List<String> result = new ArrayList<>();
        File file = new File(SENTENCE_TXT_PATH);
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bf = new BufferedReader(reader);
            String sentence;
            // 按行读取字符串
            Extractor extractor = new Extractor();
            while ((sentence = bf.readLine()) != null) {
                SentenceResultItem sentResultItem = new SentenceResultItem(sentence);
                for (Triple item : extractor.extract(sentence)) {
                    sentResultItem.addTriple(item);
                }
                if (!sentResultItem.isEmpty()) {
                    String itemResult = gson.toJson(sentResultItem);
                    System.out.println(itemResult);
                    result.add(itemResult);
                }
            }
            bf.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
