package com.mferrara.testSecurity.models;


import jakarta.persistence.*;


@Entity
public class Content {

    @Id
    @GeneratedValue
    private Long id;
    private String topic;
    private String text;
    private String authorName;

    public Content() {
    }

    public Content(Long id, String topic, String text, String authorName) {
        this.id = id;
        this.topic = topic;
        this.text = text;
        this.authorName = authorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
