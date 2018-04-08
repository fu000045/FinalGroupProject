package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Movie1Detail extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie1_detail);

        Bundle bundle = getIntent().getBundleExtra("ChatItem");

        //start a FragmentTransaction to add a fragment to the FrameLayout
        Movie1Fragment myFragment = new Movie1Fragment();
        myFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.movie_Framelayout, myFragment).commit();
    }

}
