package com.alzheimerapp.pojo.auth;

public class UserP {

    private String imageP;

    private String nameP;

    private String locationP;

    private String phoneP;

    private String urlShare;

    public UserP() {
    }

    public UserP(String imageP, String nameP, String locationP, String phoneP, String urlShare) {
        this.imageP = imageP;
        this.nameP = nameP;
        this.locationP = locationP;
        this.phoneP = phoneP;
        this.urlShare = urlShare;
    }

    public String getImageP() {
        return imageP;
    }

    public void setImageP(String imageP) {
        this.imageP = imageP;
    }

    public String getNameP() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public String getLocationP() {
        return locationP;
    }

    public void setLocationP(String locationP) {
        this.locationP = locationP;
    }

    public String getPhoneP() {
        return phoneP;
    }

    public void setPhoneP(String phoneP) {
        this.phoneP = phoneP;
    }

    public String getUrlShare() {
        return urlShare;
    }

    public void setUrlShare(String urlShare) {
        this.urlShare = urlShare;
    }
}
