package com.example.enclosedmusicshareapp;

import java.util.ArrayList;

public class SongList {
    private static SongList instance;
    private ArrayList<ListviewItem> list;

    private SongList(){
        list = new ArrayList<>();
    };
    public static SongList getInstance(){
        if(instance == null){
            instance = new SongList();
        }
        return instance;
    }

    public void addSongToList(ListviewItem item){
        list.add(item);
    }

    public void deleteSongFromList(ListviewItem item){
        list.remove(item);
    }

    public ListviewItem getSongFromList(int position){
        return list.get(position);
    }

    public void clearSongList(){
        list.clear();
    }

    public int getListSize(){
        return list.size();
    }
}
