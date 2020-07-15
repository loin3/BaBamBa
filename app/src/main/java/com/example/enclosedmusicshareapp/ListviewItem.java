package com.example.enclosedmusicshareapp;

import androidx.annotation.NonNull;

public class ListviewItem {
    private String title;
    private String url;
    private int isPlaying;

    public ListviewItem(String title, String url){
        setText(title, url);
    }

    public void setText(String title, String url){
        this.title = title;
        this.url = url;
        this.isPlaying = -1;
    }

    public String getTitle(){
        return this.title;
    }

    public String getUrl(){
        return this.url;
    }

    public void setPlaying(int isPlaying){this.isPlaying = isPlaying;}

    public int getPlaying(){return this.isPlaying;}

    @NonNull
    @Override
    public String toString() {
        return "title : " + title + " url : " + url + " playing : " + isPlaying;
    }
}
