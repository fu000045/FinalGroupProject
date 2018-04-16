package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MovieActivity extends Activity {
    LinearLayout linearlayout;
    Button btn_movie1;
    Button btn_button_website;
    Button btn_button_statistic;
    Button btn_button_help;
    Button btn_return;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        linearlayout = findViewById(R.id.linearlayout);
        btn_movie1 = findViewById(R.id.btn_movie_all);
        btn_button_website = findViewById(R.id.btn_movieFromWebsite);
        btn_button_statistic = findViewById(R.id.btn_movie_statistic);
        btn_button_help = findViewById(R.id.btn_help);
        btn_return = findViewById(R.id.btn_return);


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
        btn_button_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(linearlayout, "Loading movies from XML", Snackbar.LENGTH_LONG);

                snackbar.show();
                Intent intent = new Intent(MovieActivity.this, MovieFromWebActivity.class);
                startActivity(intent);
            }
        });

        btn_button_statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_button_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = MovieActivity.this.getLayoutInflater();

                //Pop up a dialogue box.
                AlertDialog.Builder builder = new AlertDialog.Builder(MovieActivity.this);
                builder.setView(inflater.inflate(R.layout.help_layout, null));
                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setTitle(R.string.help)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .show();
            }
        });
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MovieActivity.this, R.string.toast_movie, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MovieActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
