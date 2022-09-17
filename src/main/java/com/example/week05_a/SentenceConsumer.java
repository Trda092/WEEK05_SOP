package com.example.week05_a;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SentenceConsumer {
    protected Sentence sentences;


    public SentenceConsumer() {
        this.sentences = new Sentence();
    }
    @RabbitListener(queues = "BadWord")
    public void addBadSentence(String s){
        sentences.getBadSentences().add(s);
        System.out.println(sentences.getBadSentences());
    }
    @RabbitListener(queues = "GoodWord")
    public void addGoodSentence(String s){
        sentences.getGoodSentences().add(s);
        System.out.println(sentences.getGoodSentences());
    }
    @RabbitListener(queues = "GetQueue")
    public Sentence getSentences(){
        return sentences;
    }
}
