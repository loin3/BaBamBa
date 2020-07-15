package com.example.enclosedmusicshareapp;

import androidx.annotation.NonNull;

public class ListviewItem {
    private String title;
    private String url;
    private boolean isPlaying;

    public ListviewItem(String title, String url){
        setText(title, url);
    }

    public void setText(String title, String url){
        this.title = title;
        this.url = url;
        this.isPlaying = false;
    }

    public String getTitle(){
        return this.title;
    }

    public String getUrl(){
        return this.url;
    }

    public void setPlaying(boolean isPlaying){this.isPlaying = isPlaying;}

    public boolean getPlaying(){return this.isPlaying;}

    @NonNull
    @Override
    public String toString() {
        return "title : " + title + " url : " + url;
    }
}
