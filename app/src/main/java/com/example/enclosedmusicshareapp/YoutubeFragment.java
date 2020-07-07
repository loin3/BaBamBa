package com.example.enclosedmusicshareapp;


import android.os.Bundle;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import static com.example.enclosedmusicshareapp.MusicPlayingActivity.songList;


public class YoutubeFragment extends YouTubePlayerFragment {

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
                final ListviewItem song = songList.get(getArguments().getInt("position"));
                youTubePlayer.loadVideo(song.getUrl());


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
                        youTubePlayer.loadVideo(getNextSong(getArguments().getInt("position")).getUrl());
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

    public ListviewItem getNextSong(int currentPosition){
        if(currentPosition == songList.size() - 1){
            return songList.get(0);
        }
        else{
            return songList.get(currentPosition+1);
        }
    }
}
