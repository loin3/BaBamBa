package com.example.enclosedmusicshareapp;

public class ListviewItem {
    private String title;
    private String singer;
    private String url;

    public ListviewItem(String title, String singer, String url){
        setText(title, singer, url);
    }

    public void setText(String title, String singer, String url){
        this.title = title;
        this.singer = singer;
        this.url = url;
    }

    public String getTitle(){
        return this.title;
    }

    public String getSinger(){
        return this.singer;
    }

    public String getUrl(){
        return this.url;
    }
}
