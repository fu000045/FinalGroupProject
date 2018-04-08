package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    private TextView txt_movieTitle;
    private TextView txt_mainActors;
    private TextView txt_length;
    private TextView txt_genre;
    private TextView txt_description;

    //   private String ACTIVITY_NAME = "Movie1";
//    private boolean isTablet = false;
//    private Cursor c;

    //get a writable database
    private MovieDatabaseHelper dbHelper;
    private SQLiteDatabase db = null;

    //get table name and column name
    String tableName = dbHelper.MOVIE_TABLE_NAME;
    String keyID = dbHelper.MOVIE_KEY_ID;
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

        txt_movieTitle = findViewById(R.id.edittext_movieTitle);
        txt_mainActors = findViewById(R.id.edittext_mainActors);
        txt_length = findViewById(R.id.edittext_length);
        txt_genre = findViewById(R.id.edittext_genre);
        txt_description =findViewById(R.id.edittext_description);
        btn_addMovie = findViewById(R.id.button_addMovie);

        progressBar.setVisibility(View.VISIBLE);
        //isTablet = (findViewById(R.id.movie_Framelayout) != null);
        final MovieAdapter movieAdapter = new MovieAdapter(this);
        listview.setAdapter(movieAdapter);

        String query = "SELECT * FROM " + tableName + ";";
        Cursor c = db.rawQuery(query, null);

        //Read existed questions from database.
        c.moveToFirst();
        //counter is used to count the database record
        int counter = 0;
        while(!c.isAfterLast()){
            //long id = c.getLong(c.getColumnIndex(dbHelper.MOVIE_KEY_ID));
            //String movieNo = c.getString(c.getColumnIndex( dbHelper.MOVIE_KEY_ID));
            String movieTitle = c.getString( c.getColumnIndex( dbHelper.MOVIE_TITLE_ANSWER) );
            String mainActors = c.getString( c.getColumnIndex( dbHelper.MOVIE_MAINACTORS_ANSWER) );
            String length = c.getString( c.getColumnIndex( dbHelper.MOVIE_LENGTH_ANSWER) );
            String genre = c.getString( c.getColumnIndex( dbHelper.MOVIE_GENRE_ANSWER) );
            String description = c.getString( c.getColumnIndex( dbHelper.MOVIE_DESCRIPTION_ANSWER) );
            //ArrayList here is used to store one question
            ArrayList<String> oneMovie = new ArrayList<String>();
            //oneMovie.add(movieNo);
            oneMovie.add(movieTitle);
            oneMovie.add(mainActors);
            oneMovie.add(length);
            oneMovie.add(genre);
            oneMovie.add(description);
            movieInfo.add(oneMovie);
            //for debug
//            Log.i("ViewQuizPool", "SQL QUESTION:" + question
//            Log.i("ViewQuizPool", "SQL ANSWERA:" + answerA );
//            Log.i("ViewQuizPool", "SQL ANSWERB:" + answerB );
//            Log.i("ViewQuizPool", "SQL ANSWERC:" + answerC );
//            Log.i("ViewQuizPool", "SQL ANSWERD:" + answerD );

            //update progress bar
            counter++;
            if(counter != c.getCount()){
                progressBar.setProgress(counter * 100 /(c.getCount()));
            }else{
                progressBar.setVisibility(View.INVISIBLE);
            }

            c.moveToNext();
        }
        //this restarts the process of getCount() & getView() to retrieve chat history
        movieAdapter.notifyDataSetChanged();

        //Print colomn name.
        Log.i("Movie1", "Cursor’s  column count = " + c.getColumnCount());
        for(int i = 0; c.getColumnCount() > i; i++){
            Log.i("Movie1", "Coloumn " + i + " : " + c.getColumnName(i));
        }

        //when click send button, clear the edit text and add the new message into database, update listview as well
        btn_addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //New question.
                ArrayList<String> oneMovie = new ArrayList<String>();
                //String movieNo = txt_movieNo.getText().toString();
                // oneMovie.add(movieNo);
                String movieTitle = txt_movieTitle.getText().toString();
                oneMovie.add(movieTitle);
                String mainActors = txt_mainActors.getText().toString();
                oneMovie.add(mainActors);
                String length = txt_length.getText().toString();
                oneMovie.add(length);
                String genre = txt_genre.getText().toString();
                oneMovie.add(genre);
                String description = txt_description.getText().toString();
                oneMovie.add(description);
                movieInfo.add(oneMovie);

                //this restarts the process of getCount() & getView()
                movieAdapter.notifyDataSetChanged();

                //when the user clicks “Send”, clear the edittext
                //txt_movieNo.setText("");
                txt_movieTitle.setText("");
                txt_mainActors.setText("");
                txt_length.setText("");
                txt_genre.setText("");
                txt_description.setText("");

//                //Write new question to database.
                ContentValues cv = new ContentValues();
                // cv.put(keyMovieNo, movieNo);
                cv.put(keyMovieTitle, movieTitle);
                cv.put(keyMainActors, mainActors);
                cv.put(keyLength, length);
                cv.put(keyGenre, genre);
                cv.put(keyDescription, description);
                db.insert(tableName,null,cv);

            }
        });

        //click a question will display the detailes on another fragment layout.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ArrayList<String> oneMovie = new ArrayList<>();
                oneMovie = movieAdapter.getItem(position);
                //String movieNo = oneMovie.get(0);
                String movieTitle = oneMovie.get(0);
                String mainActors = oneMovie.get(1);
                String length = oneMovie.get(2);
                String genre = oneMovie.get(3);
                String description = oneMovie.get(4);

                // long ID = id;
//                Long id_movie = movieAdapter.getId(position);

                //a bundle is used to pass message
                Movie1Fragment movie1Fragment = new Movie1Fragment();
                //pass data to fragment
                Bundle bundle = new Bundle();
                //bundle.putString("Movie No", movieNo);
                bundle.putString("Movie Title", movieTitle);
                bundle.putString("Main Actors", mainActors);
                bundle.putString("Length", length);
                bundle.putString("Genre", genre);
                bundle.putString("Description", description);
                // bundle.putLong("ID",Id);

//                if(isTablet){
//                    movie1Fragment.setArguments(bundle);
//                    //tell the MessageFragment this is a tablet
//                    movie1Fragment.setIsTablet(true);
//                    //start a FragmentTransaction to add a fragment to the FrameLayout
//                    getFragmentManager().beginTransaction().replace(R.id.movie_Framelayout,movie1Fragment).commit();
//                }else{
                Intent intent = new Intent(Movie1.this, Movie1Detail.class);
                intent.putExtra("Movie1Detail", bundle);
                startActivityForResult(intent, 820, bundle);
            }
        });
    }

    private class MovieAdapter extends ArrayAdapter<ArrayList<String>> {
        public MovieAdapter(Context ctx) {
            super(ctx, 0);
        }

        LayoutInflater inflater = Movie1.this.getLayoutInflater();

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View result;
            TextView movieTitle;
            TextView mainActors;
            TextView length;
            TextView genre;
            TextView description;

            result = inflater.inflate(R.layout.movie_layout, null);
            // movieNo = result.findViewById(R.id.movieList);
            movieTitle = result.findViewById(R.id.movie_title);
            mainActors = result.findViewById(R.id.main_actors);
            length = result.findViewById(R.id.movie_length);
            genre = result.findViewById(R.id.movie_genre);
            description = result.findViewById(R.id.movie_description);

            //movieNo.setText(getItem(position).get(0)); // get the string at position
            movieTitle.setText(getString(R.string.MovieTitle) + getItem(position).get(0));
            mainActors.setText(getString(R.string.MainActors) + getItem(position).get(1));
            length.setText(getString(R.string.MovieLength) + getItem(position).get(2));
            genre.setText(getString(R.string.MovieGenre) + getItem(position).get(3));
            description.setText(getString(R.string.MovieDescription) + getItem(position).get(4));

            return result;
        }

        @Override
        public int getCount() {
            return movieInfo.size();
        }

        @Override
        public ArrayList<String> getItem(int position) {
            return movieInfo.get(position);
        }
    }
}
