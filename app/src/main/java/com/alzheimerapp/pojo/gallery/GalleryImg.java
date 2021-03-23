package com.alzheimerapp.pojo.gallery;

public class GalleryImg {

    private String id;
    private String urlImg;
    private String name;
    private String relation;

    public GalleryImg() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public GalleryImg(String urlImg, String name, String relation) {
        this.urlImg = urlImg;
        this.name = name;
        this.relation = relation;
    }
}
