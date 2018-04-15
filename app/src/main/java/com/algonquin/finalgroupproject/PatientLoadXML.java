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
    private TextView name ;
    private TextView address ;
    private TextView birthday ;
    private TextView phone;
    private TextView card;
    private TextView patientType;
    private TextView description;

    private TextView nameDC ;
    private TextView addressDC ;
    private TextView birthdayDC ;
    private TextView phoneDC;
    private TextView cardDC;
    private TextView patientTypeDC;
    private TextView descriptionDC;

    private TextView nameOP ;
    private TextView addressOP ;
    private TextView birthdayOP ;
    private TextView phoneOP;
    private TextView cardOP;
    private TextView patientTypeOP;
    private TextView descriptionOP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_load_xml);
        progressBar = findViewById(R.id.loadXML_progressBar);
        name = findViewById(R.id.loadXML_name);
        nameDC=findViewById(R.id.loadXML_doctor_name);
        nameOP=findViewById(R.id.loadXML_opt_name);
        address = findViewById(R.id.loadXML_address);
        addressDC=findViewById(R.id.loadXML_doctor_address);
        addressOP=findViewById(R.id.loadXML_opt_address);
        birthday = findViewById(R.id.loadXML_birthday);
        birthdayDC=findViewById(R.id.loadXML_doctor_birthday);
        birthdayOP=findViewById(R.id.loadXML_opt_birthday);
        card=findViewById(R.id.loadXML_card);
        cardDC=findViewById(R.id.loadXML_doctor_card);
        cardOP=findViewById(R.id.loadXML_opt_card);
        phone = findViewById(R.id.loadXML_phone);
        phoneDC=findViewById(R.id.loadXML_doctor_phone);
        phoneOP=findViewById(R.id.loadXML_opt_phone);
        patientType = findViewById(R.id.loadXML_type);
        patientTypeDC=findViewById(R.id.loadXML_doctor_description);
        patientTypeOP=findViewById(R.id.loadXML_opt_description);
        description=findViewById(R.id.loadXML_description);
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
                    //Are you currently at a Start Tag？
                    switch(eventType){
                        case XmlPullParser.START_TAG:
                            if(parser.getName().equalsIgnoreCase("patient"))
                                docName+=parser.getAttributeValue(null,"value");
                            break;
                        case XmlPullParser.TEXT:
                            docName+=parser.getText();
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
