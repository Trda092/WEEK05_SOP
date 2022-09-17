package com.example.week05_a;

import java.util.*;

public class Word {
    private ArrayList<String> badWords;
    private ArrayList<String> goodWords;

    public Word() {
        this.badWords = new ArrayList<String>(Arrays.asList("fuck", "olo"));
        this.goodWords = new ArrayList<String>(Arrays.asList("happy", "enjoy", "life"));
    }

    public ArrayList<String> getBadWords() {
        return badWords;
    }

    public void setBadWords(ArrayList<String> badWords) {
        this.badWords = badWords;
    }

    public ArrayList<String> getGoodWords() {
        return goodWords;
    }

    public void setGoodWords(ArrayList<String> goodWords) {
        this.goodWords = goodWords;
    }
}
