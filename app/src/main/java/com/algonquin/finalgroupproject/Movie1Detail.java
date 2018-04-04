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

    TextView txt_movieTitle;
    TextView txt_mainActors;
    TextView txt_length;
    TextView txt_description;
    TextView txt_genre;
    Button btn_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie1_detail);
        txt_movieTitle = findViewById(R.id.movieTitle_framelayout);
        txt_mainActors = findViewById(R.id.mainActors_framelayout);
        txt_length = findViewById(R.id.length_framelayout);
        txt_genre = findViewById(R.id.genre_framelayout);
        txt_description = findViewById(R.id.description_framelayout);
        btn_delete = findViewById(R.id.delete_question);

        //start a FragmentTransaction to add a fragment to the FrameLayout
        Movie1Fragment myFragment = new Movie1Fragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.movie_Framelayout, myFragment).addToBackStack(null).commit();

        //Get message passed by ViewQuizPool
        Bundle bundle = getIntent().getBundleExtra("Movie1Detail");

        String movieTitle = bundle.getString("Movie Title");
        String mainActors = bundle.getString("Main Actors");
        String length = bundle.getString("Length");
        String genre = bundle.getString("Genre");
        String description = bundle.getString("Description");


        txt_movieTitle.setText(String.valueOf(movieTitle));
        txt_mainActors.setText(String.valueOf(mainActors));
        txt_length.setText(String.valueOf(length));
        txt_genre.setText(String.valueOf(genre));
        txt_description.setText(String.valueOf(description));

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Movie1Detail.this);
                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.movie_dialog_message)
                        .setTitle(R.string.movie_dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .show();
            }
        });
        //start a FragmentTransaction to add a fragment to the FrameLayout
    }
}
