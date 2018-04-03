package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MovieActivity extends Activity {
    LinearLayout linearlayout;
    Button btn_movie1;
    Button btn_movie2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        linearlayout = findViewById(R.id.linearlayout);
        btn_movie1 = findViewById(R.id.Movie1);
        btn_movie2 = findViewById(R.id.Movie2);

        //From: Android Snackbar Example Tutorial [Web Page]
        //Retrieved from: https://www.journaldev.com/10324/android-snackbar-example-tutorial
        btn_movie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(linearlayout, "Welcome movie1", Snackbar.LENGTH_LONG);
                snackbar.show();

                //Jump to movie1
                Intent intent = new Intent(MovieActivity.this, Movie1.class);
                startActivity(intent);
            }
        });
        btn_movie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(linearlayout, "Welcome movie2", Snackbar.LENGTH_LONG);

                snackbar.show();
                Intent intent = new Intent(MovieActivity.this, Movie2.class);
                startActivity(intent);
            }
        });
        //Jump to Movie2.

    }
}
