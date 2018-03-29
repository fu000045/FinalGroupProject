package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Movie1 extends Activity {
    private ListView listview;
    private ArrayList<ArrayList<String>> movieInfo = new ArrayList<>();
    private ProgressBar progressBar;
    private Button btn_addMovie;
    private TextView txt_movieNo;
    private TextView txt_movieTitle;
    private TextView txt_mainActors;
    private TextView txt_length;
    private TextView txt_genre;
    private TextView txt_description;

    //get a writable database
    private MovieDatabaseHelper dbHelper;
    private SQLiteDatabase db = null;

    //get table name and column name
    String tableName = dbHelper.MOVIE_TABLE_NAME;
    String keyMovieNo = dbHelper.MOVIE_KEY_ID;
    String keyMovieTitle = dbHelper.MOVIE_TITLE_ANSWER;
    String keyMainActors = dbHelper.MOVIE_MAINACTORS_ANSWER;
    String keyLength = dbHelper.MOVIE_LENGTH_ANSWER;
    String keyGenre = dbHelper.MOVIE_GENRE_ANSWER;
    String keyDescription = dbHelper.MOVIE_DESCRIPTION_ANSWER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie1);
        dbHelper = new MovieDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        listview = findViewById(R.id.listview_movieInfo);
        progressBar = findViewById(R.id.progressBar_movie);
        txt_movieNo = findViewById(R.id.edittext_newMovie);
        txt_movieTitle = findViewById(R.id.edittext_movieTitle);
        txt_mainActors = findViewById(R.id.e)
        txt_length = findViewById(R.id.edittext_length);
        txt_genre = findViewById(R.id.edittext_genre);
        txt_description =findViewById(R.id.edittext_description);
        btn_addMovie = findViewById(R.id.button_addMovie);

        progressBar.setVisibility(View.VISIBLE);



    }
}
