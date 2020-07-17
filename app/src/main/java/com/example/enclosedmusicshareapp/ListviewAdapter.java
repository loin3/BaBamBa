package com.example.enclosedmusicshareapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListviewAdapter extends BaseAdapter {
    private SongList songList;

    public ListviewAdapter(){
        songList = SongList.getInstance();
    }

    @Override
    public int getCount() {
        return songList.getListSize();
    }

    @Override
    public Object getItem(int position) {
        return songList.getSongFromList(position);
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

        final ListviewItem listviewItem = songList.getSongFromList(position);

        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        String text = listviewItem.getTitle();
        textView.setText(text);

        ImageView imageView = convertView.findViewById(R.id.playingImageView);
        if(listviewItem.getPlaying() != -1){
            imageView.setVisibility(View.VISIBLE);
            if(listviewItem.getPlaying() == 0){
                imageView.setImageResource(R.drawable.ic_media_play);
            }else if(listviewItem.getPlaying() == 1){
                imageView.setImageResource(R.drawable.ic_media_pause);
            }
        }else{
            imageView.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
