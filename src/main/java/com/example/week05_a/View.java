package com.example.week05_a;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Route(value = "index")
public class View extends HorizontalLayout {
    private TextField wordField, SentenceField;
    private Button addGood, addBad, addSentences, showSentences;
    private Select<String> goodWordList, badWordList;
    private TextArea goodTextArea, badTextArea;
    private Notification notify;
    public View() {
        wordField = new TextField("Word");
        wordField.setSizeFull();
        SentenceField = new TextField("Sentence");
        SentenceField.setSizeFull();
        addGood = new Button("Add Good Word");
        addGood.setSizeFull();
        addBad = new Button("Add Bad Word");
        addBad.setSizeFull();
        addSentences = new Button("Add Sentence");
        addSentences.setSizeFull();
        showSentences = new Button("Show");
        showSentences.setSizeFull();
        goodWordList = new Select<String>();
        goodWordList.setLabel("Good Words");
        goodWordList.setSizeFull();
        badWordList = new Select<String>();
        badWordList.setLabel("Bad Words");
        badWordList.setSizeFull();
        goodTextArea = new TextArea();
        goodTextArea.setLabel("Good Sentences");
        goodTextArea.setSizeFull();
        badTextArea = new TextArea();
        badTextArea.setLabel("Bad sentences");
        badTextArea.setSizeFull();
        notify = new Notification();
        VerticalLayout v1 = new VerticalLayout();
        v1.add(wordField, addGood, addBad,goodWordList,badWordList);
        VerticalLayout v2 = new VerticalLayout();
        v2.add(SentenceField, addSentences, goodTextArea, badTextArea, showSentences);
        this.add(v1, v2);
        Word def = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getWordList")
                    .retrieve()
                    .bodyToMono(Word.class)
                    .block();
        System.out.println(def);
        goodWordList.setItems(def.getGoodWords());
        badWordList.setItems(def.getBadWords());

        addGood.addClickListener(event->{
            String newWord = wordField.getValue();
            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addGood/"+newWord)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            goodWordList.setItems(out);
            notify.show("Insert "+newWord+" to Good Word Lists Completed");
        });

        addBad.addClickListener(event->{
            String newWord = wordField.getValue();
            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addBad/"+newWord)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            badWordList.setItems(out);
            notify.show("Insert "+newWord+" to Bad Word Lists Completed");
        });

        addSentences.addClickListener(event->{
            String newSentences = SentenceField.getValue();
            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/proof/"+newSentences)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            notify.show(out);
        });

        showSentences.addClickListener(event->{
            Sentence out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getSentence")
                    .retrieve()
                    .bodyToMono(Sentence.class)
                    .block();
            goodTextArea.setValue(String.valueOf(out.getGoodSentences()));
            badTextArea.setValue(String.valueOf(out.getBadSentences()));
        });
    }
}
