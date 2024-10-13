package com.example.sampark.Models;

public class message {
    String uid, textmessage;
    Long timestamp;

    public message(String uid, String textmessage, Long timestamp) {
        this.uid = uid;
        this.textmessage = textmessage;
        this.timestamp = timestamp;
    }

    public message(String uid, String textmessage) {
        this.uid = uid;
        this.textmessage = textmessage;
    }
    public message(){}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTextmessage() {
        return textmessage;
    }

    public void setTextmessage(String textmessage) {
        this.textmessage = textmessage;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
