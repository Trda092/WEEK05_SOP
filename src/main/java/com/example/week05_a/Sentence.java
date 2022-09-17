package com.example.week05_a;

import java.io.Serializable;
import java.util.*;

public class Sentence implements Serializable {
    private ArrayList<String> badSentences;
    private ArrayList<String> goodSentences;

    public Sentence() {
        this.badSentences = new ArrayList<String>();
        this.goodSentences = new ArrayList<String>();
    }

    public ArrayList<String> getBadSentences() {
        return badSentences;
    }

    public void setBadSentences(ArrayList<String> badSentences) {
        this.badSentences = badSentences;
    }

    public ArrayList<String> getGoodSentences() {
        return goodSentences;
    }

    public void setGoodSentences(ArrayList<String> goodSentences) {
        this.goodSentences = goodSentences;
    }
}
