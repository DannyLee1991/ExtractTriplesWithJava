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
            batchExtract();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void batchExtract() throws IOException {
        File outputJson = new File(OUTPUT_JSON);
        if (outputJson.exists()) {
            outputJson.delete();
        }
        FileWriter fw = new FileWriter(outputJson);

        File file = new File(SENTENCE_TXT_PATH);
        int totalLines = getTotalLines(file);
        Gson gson = new Gson();
        int crtLine = 0;
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
                    fw.write(itemResult + "," + "\n");
                    fw.flush();
                    System.out.println(crtLine + " " + totalLines + " " + ((float) crtLine / (float) totalLines) * 100 + " data: " + itemResult);
                }
                crtLine += 1;
            }
            fw.flush();
            fw.close();

            bf.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getTotalLines(File file) throws IOException {
        long startTime = System.currentTimeMillis();
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        reader.skip(Long.MAX_VALUE);
        int lines = reader.getLineNumber();
        reader.close();
        long endTime = System.currentTimeMillis();

        System.out.println("统计文件行数运行时间： " + (endTime - startTime) + "ms");
        return lines;
    }
}
