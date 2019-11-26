package com.dannylee;

import java.util.ArrayList;
import java.util.List;

public class Triple {

    private String w1;
    private String r;
    private String w2;

    public Triple(String w1, String r, String w2) {
        this.w1 = w1;
        this.r = r;
        this.w2 = w2;
    }

    public String getW1() {
        return w1;
    }

    public String getR() {
        return r;
    }

    public String getW2() {
        return w2;
    }

    @Override
    public String toString() {
        return this.w1 + "," + this.r + "," + this.w2;
    }

    public List<String> toList() {
        ArrayList<String> result = new ArrayList<>();
        result.add(w1);
        result.add(r);
        result.add(w2);
        return result;
    }
}
