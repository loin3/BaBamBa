package com.example.enclosedmusicshareapp;


import android.os.Bundle;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import static com.example.enclosedmusicshareapp.MusicPlayingActivity.songList;


public class YoutubeFragment extends YouTubePlayerFragment {

    private ListviewItem currentSong;
    private ListviewAdapter listviewAdapter;



    public YoutubeFragment() {
        // Required empty public constructor
    }

    public static YoutubeFragment newInstance(int position) {
        YoutubeFragment fragment = new YoutubeFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);

        fragment.setArguments(args);
        fragment.init();
        return fragment;
    }

    private void init(){
        initialize("AIzaSyBpNITvbs8PgrOXLXT4bw5cVvTsHtlYcqQ", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                currentSong = songList.get(getArguments().getInt("position"));
                currentSong.setPlaying(true);
                youTubePlayer.loadVideo(currentSong.getUrl());
                listviewAdapter.notifyDataSetChanged();

                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {

                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {
                        currentSong.setPlaying(false);
                        ListviewItem nextSong = getNextSong(getArguments().getInt("position"));
                        youTubePlayer.loadVideo((nextSong).getUrl());
                        nextSong.setPlaying(true);
                        currentSong = nextSong;
                        listviewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        currentSong.setPlaying(false);
        listviewAdapter.notifyDataSetChanged();
    }

    public void setAdapter(ListviewAdapter listviewAdapter){
        this.listviewAdapter = listviewAdapter;
    }

    public ListviewItem getNextSong(int currentPosition){
        if(currentPosition == songList.size() - 1){
            return songList.get(0);
        }
        else{
            return songList.get(currentPosition+1);
        }
    }
}
