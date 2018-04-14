package com.algonquin.finalgroupproject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieFromWebActivity extends AppCompatActivity {
    private ImageView img_movie;
    private TextView text_title;
    private TextView text_actors;
    private TextView text_length;
    private TextView text_description;
    private TextView text_rating;
    private TextView text_genre;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_from_web);

        text_title = findViewById(R.id.title);
        text_actors = findViewById(R.id.actors);
        text_length = findViewById(R.id.length);
        text_description = findViewById(R.id.description);
        text_rating = findViewById(R.id.rating);
        text_genre = findViewById(R.id.genre);
        progressBar = findViewById(R.id.progress);

        //set progress bar to be visible
        progressBar.setVisibility(View.VISIBLE);
        //start a thread to parse xml information
        MovieQuery movie = new MovieQuery();
        movie.execute();
    }
    public class MovieQuery extends AsyncTask<String, Integer, String> {

        String title;
        String actors;
        String length;
        String description;
        String rating;
        String genre;
        String movieName;
        Bitmap movie;

        private Boolean isImg_movie;
        private Boolean isTitle;
        private Boolean isActors;
        private Boolean isLength;
        private Boolean isDescription;
        private Boolean isRating;
        private Boolean isGenre;

        protected String doInBackground(String... args){
            //From: Parsing XML Data.[Web Page] Retrieved from: https://developer.android.com/training/basics/network-ops/xml.html
            // Given a string representation of a URL, sets up a connection and gets an input stream.
            URL url;
            InputStream inStream = null;
            try {
                //conncet to an URL, and parser the xml info
                url = new URL("http://torunski.ca/CST2335/MovieInfo.xml");
                HttpURLConnection conn;
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                inStream = conn.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(inStream, null);
                int eventType = parser.getEventType();

                //while not end of document
                while(eventType != XmlPullParser.END_DOCUMENT){
                    //check eventType
                    switch(eventType){
                        //if eventType is START_TAG, parser the info needed
                        case XmlPullParser.START_TAG:
                            String tagname = parser.getName();
                            //if tag name is "URL", get the http value
                           if (parser.getName().equalsIgnoreCase("URL")){
                                //parser the image name
                                movieName = parser.getAttributeValue(null, "value");

                                //if image file exists, read it
                                String imagefile = movieName + ".png";
                                if(fileExistance(imagefile)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(imagefile);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    movie = BitmapFactory.decodeStream(fis);
                                    Log.i(imagefile, " image is found locally.");
                                }
                                publishProgress(100);
                            }
                            break;
                           case XmlPullParser.TEXT:
                               //String text = parser.getText();
                               if(isTitle){
                                   title = parser.getText();
                               }
                               else if(isActors){
                                   actors = parser.getText();
                               }
                               else if(isLength){
                                   length = parser.getText();
                               }
                               else if(isDescription){
                                   description = parser.getText();
                               }
                               else if(isRating){
                                   rating = parser.getText();
                               }
                               else if(isGenre){
                                   genre = parser.getText();
                               }
                               break;
                           case XmlPullParser.END_TAG:
                               if(parser.getText().equals("Title")){
                                   isTitle = false;
                               }

                               else if(parser.getText().equals("Actors")){
                                   isActors = false;
                               }
                               else if(parser.getText().equals("Length")){
                                   isLength = false;
                               }
                               else if(parser.getText().equals("Description")){
                                   isDescription = false;
                               }
                               else if(parser.getText().equals("Rating")){
                                   isRating = false;
                               }
                               else if(parser.getText().equals("Genre")){
                                   isGenre = false;
                               }
                               break;
                               default:
                                   break;
                    }
                    eventType = parser.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        //check if image file exists
        boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        //each time publishprogress() is called, show the progress of GUI
        @Override
        protected void onProgressUpdate(Integer... values) {
            //set progress bar to be visible
            progressBar.setVisibility(View.VISIBLE);
            //set the progress bar
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        //get the info from doInBackground(), and update the info on GUI
        @Override
        protected void onPostExecute(String s) {
            if(title != null) {
                text_title.setText(getString(R.string.title_web) + title);
            }
            if(actors != null) {
                text_actors.setText(getString(R.string.actors_web) + actors);
            }
            if(length != null) {
                text_length.setText(getString(R.string.length_web) + length);
            }
            if(description != null) {
                text_description.setText(getString(R.string.description_web) + description);
            }
            if(rating != null) {
                text_rating.setText(getString(R.string.rating_web) + rating);
            }
            if(genre != null) {
                text_genre.setText(getString(R.string.genre_web) + genre);
            }
            if(movie != null) {
                img_movie.setImageBitmap(movie);
            }

            super.onPostExecute(s);
            //set progress bar to be invisible
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}