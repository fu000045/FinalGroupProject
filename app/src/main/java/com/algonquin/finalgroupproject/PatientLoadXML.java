package com.algonquin.finalgroupproject;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.net.HttpURLConnection;
import java.net.URL;

public class PatientLoadXML extends Activity {
    protected static final String ACTIVITY_NAME = "LoadXMLActivity";
    ProgressBar progressBar ;


    private TextView nameDC ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_load_xml);
        progressBar = findViewById(R.id.loadXML_progressBar);

        nameDC=findViewById(R.id.loadXML_doctor_name);

        progressBar.setVisibility(View.VISIBLE);
        PatientQuery patient = new PatientQuery();
        String urlString = "http://torunski.ca/CST2335/PatientList.xml";
        patient.execute(urlString);
        Log.i(ACTIVITY_NAME,"In onCreate()");
    }

    public class PatientQuery extends AsyncTask<String, Integer, String> {
       // private String speed ="speed", min="min", max="max",  currentTemperature="current", type="type";
        private String docName,optName,denName,docBir,optBir,denBir,docPho,denPho,optPho,docAddr,denAddr,optAddr,descriptionn,cardd,docTy,denTy,optTy;

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
                int eventType = parser.getEventType();
                while(eventType!=XmlPullParser.END_DOCUMENT){
                    switch(eventType){
                        case XmlPullParser.START_TAG:
                            if(parser.getName().equalsIgnoreCase("patient"))
                                docName+=parser.getAttributeValue(null,"value");
                            onProgressUpdate(50);
                                    break;
                        case XmlPullParser.TEXT:
                            docName+=parser.getText();
                            onProgressUpdate(100);
                            break;
                        case XmlPullParser.END_TAG:
                    }
                    eventType=parser.next();
                }
            }
            catch(Exception e) {e.printStackTrace();}
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            Log.i(ACTIVITY_NAME, "In onProgressUpdate");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);}
        @Override
        protected void onPostExecute(String result) {
            nameDC.setText(docName);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
