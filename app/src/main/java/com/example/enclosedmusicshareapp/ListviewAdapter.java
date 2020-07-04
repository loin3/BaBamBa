package com.example.enclosedmusicshareapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListviewAdapter extends BaseAdapter {
    private ArrayList<ListviewItem> songList;

    public ListviewAdapter(ArrayList<ListviewItem> data){
        songList = data;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int position) {
        return songList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview_item, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        final ListviewItem listviewItem = songList.get(position);
        String text = listviewItem.getTitle() + " - " + listviewItem.getSinger();
        textView.setText(text);

        return convertView;
    }

    public void addItem(String title, String singer, String url){
        ListviewItem item = new ListviewItem(title, singer, url);
        songList.add(item);
    }
}