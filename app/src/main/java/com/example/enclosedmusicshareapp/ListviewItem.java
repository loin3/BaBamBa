package com.example.enclosedmusicshareapp;

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
}
