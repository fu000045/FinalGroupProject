package com.algonquin.finalgroupproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

public class Movie1Fragment extends Fragment {
    public View onCreate(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflater.inflate(R.layout.activity_movie1_detail, null);
         return super.onCreateView(inflater, container, savedInstanceState);
    }
}
