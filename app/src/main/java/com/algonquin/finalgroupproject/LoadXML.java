package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadXML extends Activity {
    protected static final String ACTIVITY_NAME = "WeatherForecastActivity";
    ProgressBar progressBar ;
    private TextView currentTemp ;
    private TextView minTemp ;
    private TextView maxTemp ;
   // private ImageView weatherImage ;
    private TextView windSpeed;
    private TextView patientType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_xml);

        progressBar = findViewById(R.id.progressBar);
        currentTemp = findViewById(R.id.loadXML_name);
        minTemp = findViewById(R.id.loadXML_address);
        maxTemp = findViewById(R.id.loadXML_birthday);
       // weatherImage = findViewById(R.id.imageWeatherID);
        windSpeed = findViewById(R.id.loadXML_phone);
        patientType = findViewById(R.id.loadXML_type);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        ForecastQuery forecast = new ForecastQuery();
        String urlString = "http://torunski.ca/CST2335/PatientList.xml";
        forecast.execute(urlString);
        Log.i(ACTIVITY_NAME,"In onCreate()");
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String speed ="speed", min="min", max="max",  currentTemperature="current", type="type";

        protected String doInBackground(String ... args) {
            Log.i(ACTIVITY_NAME, "In doInBackground");
            try {
                URL url = new URL(args[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    if (parser.getName().equals("Patient")) {
                        type =   parser.getAttributeValue(null, "type");
                        publishProgress(25);
//                        max =  parser.getAttributeValue(null, "max");
//                        publishProgress(50  );
//                        currentTemperature = parser.getAttributeValue(null, "value");
                     //   publishProgress(75);
                    }

//                    if (parser.getName().equals("speed")){
//                        speed= parser.getAttributeValue(null, "value");
//                        publishProgress(125);
//
//
//                    }

                }
            } catch(Exception e) {e.printStackTrace();}
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            Log.i(ACTIVITY_NAME, "In onProgressUpdate");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String result) {
           // String degree = Character.toString((char) 0x00B0);
//            currentTemp.setText(currentTemp.getText()+currentTemperature+degree+"C");
//            minTemp.setText(minTemp.getText()+min+degree+"C");
//            maxTemp.setText(maxTemp.getText()+max+degree+"C");
//          //  weatherImage.setImageBitmap(icon);
//            windSpeed.setText(windSpeed.getText()+speed);

            patientType.setText(patientType.getText()+type);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
