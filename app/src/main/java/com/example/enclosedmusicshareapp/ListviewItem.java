package com.example.enclosedmusicshareapp;

public class ListviewItem {
    private String title;
    private String url;

    public ListviewItem(String title, String url){
        setText(title, url);
    }

    public void setText(String title, String url){
        this.title = title;
        this.url = url;
    }

    public String getTitle(){
        return this.title;
    }

    public String getUrl(){
        return this.url;
    }
}
