package com.alzheimerapp.pojo.alert;

public class Alert {

    private String uid;
    private String name;
    private String dateTime;

    public Alert() {
    }

    public Alert(String uid, String name, String dateTime) {
        this.uid = uid;
        this.name = name;
        this.dateTime = dateTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
