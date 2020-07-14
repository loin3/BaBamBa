package com.example.enclosedmusicshareapp;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BlankYoutubeViewFragment extends Fragment {

    public BlankYoutubeViewFragment() {
    }

    public static BlankYoutubeViewFragment newInstance() {
        BlankYoutubeViewFragment fragment = new BlankYoutubeViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_youtube_view, container, false);
    }
}
