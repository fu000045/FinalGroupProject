package com.algonquin.finalgroupproject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
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

import static com.algonquin.finalgroupproject.MovieFromWebActivity.ACTIVITY_NAME;

public class MovieFromWebActivity extends Activity {
    protected  static final String ACTIVITY_NAME = "MovieFromWebActivity";
    TextView title;
    TextView actors;
    TextView length;
    TextView description;
    TextView rating;
    TextView genre;
    ImageView img_url;

    TextView title1;
    TextView actors1;
    TextView length1;
    TextView description1;
    TextView rating1;
    TextView genre1;
    ImageView img_url1;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_from_web);
        progressBar = findViewById(R.id.progress);
        //progressBar.setVisibility(View.VISIBLE);

        title = findViewById(R.id.title);
        actors = findViewById(R.id.actors);
        length = findViewById(R.id.length);
        description = findViewById(R.id.description);
        rating = findViewById(R.id.rating);
        genre = findViewById(R.id.genre);
        img_url = findViewById(R.id.url);

        title1 = findViewById(R.id.title1);
        actors1 = findViewById(R.id.actors1);
        length1 = findViewById(R.id.length1);
        description1 = findViewById(R.id.description1);
        rating1 = findViewById(R.id.rating1);
        genre1 = findViewById(R.id.genre1);
        img_url1 = findViewById(R.id.url1);


        progressBar.setVisibility(View.VISIBLE);

        MovieQuery movie = new MovieQuery();
        //String urlString = "http://http://torunski.ca/CST2335/MovieInfo.xml";
        movie.execute();
        Log.i(ACTIVITY_NAME,"In onCreate()");
    }

    public class MovieQuery extends AsyncTask<String, Integer, String> {
        private String mtitle, mtitle1,mactors,mactors1,mlength,mlength1,mdescription,mdescription1,mrating,mrating1,mgenre,mgenre1;
        private Bitmap mimg,mimg1;
        protected String doInBackground(String... args){
            Log.i(ACTIVITY_NAME, "In doInBackground");
            URL url;
            InputStream inStream = null;
            String imgURL;
            try {
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

                int movieCounter = 0;
                boolean isTitle = false;
                boolean isActors = false;
                boolean isLength = false;
                boolean isDescription = false;
                boolean isRating = false;
                boolean isGenre = false;
                boolean isImage = false;
                //while not end of document
                while(eventType != XmlPullParser.END_DOCUMENT){
                    //check eventType
                    switch(eventType){
                        case XmlPullParser.START_TAG:
                            String tagname = parser.getName();
                            if(parser.getName().equalsIgnoreCase("Movie")){
                                movieCounter++;
                            }
                            if(parser.getName().equalsIgnoreCase("Title")){
                                isTitle = true;
                            }
                            if(parser.getName().equalsIgnoreCase("Actors")){
                                isActors = true;
                            }
                            if(parser.getName().equalsIgnoreCase("length")){
                                isLength = true;
                            }
                            if(parser.getName().equalsIgnoreCase("Description")){
                                isDescription = true;
                            }
                            if(parser.getName().equalsIgnoreCase("Rating")){
                                isRating = true;
                            }
                            if(parser.getName().equalsIgnoreCase("Genre")){
                                isGenre = true;
                            }
                            if(parser.getName().equalsIgnoreCase("URL") && movieCounter == 1){
                                imgURL = parser.getAttributeValue(null, "value");
                                try {
                                    url = new URL(imgURL);
                                    mimg = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                } catch(IOException e) {
                                    System.out.println(e);
                                }
                            }else if(parser.getName().equalsIgnoreCase("URL") && movieCounter == 2){
                                imgURL = parser.getAttributeValue(null, "value");
                                try {
                                    url = new URL(imgURL);
                                    mimg1 = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                } catch(IOException e) {
                                    System.out.println(e);
                                }
                            }
                            //mimg = parser.getAttributeValue(null, "value");

                            break;
                        case XmlPullParser.TEXT:
                            if(movieCounter == 1){
                                if(isTitle){
                                    mtitle = parser.getText();
                                }
                                if(isActors){
                                    mactors = parser.getText();
                                }
                                if(isLength){
                                    mlength = parser.getText();
                                }
                                if(isDescription){
                                    mdescription = parser.getText();
                                }
                                if(isRating){
                                    mrating = parser.getText();
                                }
                                if(isGenre){
                                    mgenre = parser.getText();
                                }


                            }
                            if(movieCounter == 2){
                                if(isTitle){
                                    mtitle1 = parser.getText();
                                }
                                if(isActors){
                                    mactors1 = parser.getText();
                                }
                                if(isLength){
                                    mlength1 = parser.getText();
                                }
                                if(isDescription){
                                    mdescription1 = parser.getText();
                                }
                                if(isRating){
                                    mrating1 = parser.getText();
                                }
                                if(isGenre){
                                    mgenre1 = parser.getText();
                                }
                            }

                            break;
                        case XmlPullParser.END_TAG:
                            if(parser.getName().equalsIgnoreCase("Title")){
                                isTitle = false;
                            }
                            if(parser.getName().equalsIgnoreCase("Actors")){
                                isActors = false;
                            }
                            if(parser.getName().equalsIgnoreCase("length")){
                                isLength = false;
                            }
                            if(parser.getName().equalsIgnoreCase("Description")){
                                isDescription = false;
                            }
                            if(parser.getName().equalsIgnoreCase("Rating")){
                                isRating = false;
                            }
                            if(parser.getName().equalsIgnoreCase("Genre")){
                                isGenre = false;
                            }
                            break;
                    }
                    eventType=parser.next();
                }
            }
            catch(Exception e) {e.printStackTrace();}
            return null;
        }

        //each time publishprogress() is called, show the progress of GUI
        @Override
        protected void onProgressUpdate(Integer... values) {
            //set progress bar to be visible
            progressBar.setVisibility(View.VISIBLE);
            //set the progress bar
            progressBar.setProgress(values[0]);
        }

        //get the info from doInBackground(), and update the info on GUI
        @Override
        protected void onPostExecute(String s) {
            title.setText(mtitle);
            actors.setText(mactors);
            length.setText(mlength);
            description.setText(mdescription);
            rating.setText(mrating);
            genre.setText(mgenre);
            if(mimg != null) {
                img_url.setImageBitmap(mimg);
            }

            title1.setText(mtitle1);
            actors1.setText(mactors1);
            length1.setText(mlength1);
            description1.setText(mdescription1);
            rating1.setText(mrating1);
            genre1.setText(mgenre1);
            if(mimg1 != null) {
                img_url1.setImageBitmap(mimg1);
            }

            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}