package com.dannylee;

import java.util.ArrayList;
import java.util.List;

public class SentenceResultItem {
    String sentence;
    ArrayList<List<String>> data = new ArrayList<>();

    public SentenceResultItem(String sentence) {
        this.sentence = sentence;
    }

    public void addTriple(Triple triple) {
        data.add(triple.toList());
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }
}
