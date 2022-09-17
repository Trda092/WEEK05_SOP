package com.example.week05_a;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class WordPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    protected Word word;

    public WordPublisher() {
        this.word = new Word();
    }

    @PostMapping(value = "/addBad/{word}")
    public ArrayList<String> addBadWord(@PathVariable("word") String word){
        this.word.getBadWords().add(word);
        return this.word.getBadWords();
    }
    @GetMapping(value = "/delBad/{word}")
    public ArrayList<String> delBadWord(@PathVariable("word") String s){
        this.word.getBadWords().remove(s);
        return this.word.getBadWords();
    }
    @PostMapping(value = "/addGood/{word}")
    public ArrayList<String> addGoodWord(@PathVariable("word") String word){
        this.word.getGoodWords().add(word);
        return this.word.getGoodWords();
    }
    @GetMapping(value = "/delGood/{word}")
    public ArrayList<String> delGoodWord(@PathVariable("word")String s){
        this.word.getGoodWords().remove(s);
        return this.word.getGoodWords();
    }
    @PostMapping(value = "/proof/{sentences}")
    public String proofSentence(@PathVariable("sentences") String s){
        boolean bad = false, good = false;
        for (int i = 0;i<this.word.getBadWords().size();i++){
            if (s.contains(this.word.getBadWords().get(i))){
                bad = true;
            }
        }
        for (int i = 0;i<this.word.getGoodWords().size();i++){
            if (s.contains(this.word.getGoodWords().get(i))){
                good = true;
            }
        }
        if (bad  && good ){
            rabbitTemplate.convertAndSend("FanoutExchange","",s);
            return "Found Good Word & Bad Word";
            }
        else if (good){
            rabbitTemplate.convertAndSend("DirectExchange","good",s);
            return "Found Good Word";
        }
        else if (bad) {
            rabbitTemplate.convertAndSend("DirectExchange", "bad", s);
            return "Found Bad Word";
        }
        return "Not Found";
    }

    @GetMapping(value = "/getSentence")
    public Sentence getSentences(){
        return (Sentence) (rabbitTemplate.convertSendAndReceive("DirectExchange","gq",""));
    }

    @GetMapping(value = "/getWordList")
    public Word getWordList(){
        return this.word;
    }
}
