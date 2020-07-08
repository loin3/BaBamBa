package com.example.enclosedmusicshareapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

        final ListviewItem listviewItem = songList.get(position);

        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        String text = listviewItem.getTitle();
        textView.setText(text);

        ImageView imageView = convertView.findViewById(R.id.playingImageView);
        if(listviewItem.getPlaying() == true){
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
