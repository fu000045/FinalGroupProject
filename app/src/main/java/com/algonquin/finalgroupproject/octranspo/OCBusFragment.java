package com.algonquin.finalgroupproject.octranspo;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.algonquin.finalgroupproject.R;
import com.algonquin.finalgroupproject.ocparse.OCBusNextStopData;
import com.algonquin.finalgroupproject.ocparse.OCBusNumberData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class OCBusFragment extends Fragment {


    private String stopNoDB;

    private ProgressBar progressBar;
    Button but1, but2, but3, but4, but5;
    public String butS1, butS2, butS3, butS4, butS5;
    public TextView textV1, textV2, textV3, textV4, textV5;
    public String busText1, busText2, busText3, busText4, busText5;


    public static ArrayList<String> al = new ArrayList<String>();
    public static ArrayList<String> al1 = new ArrayList<String>();

    public static final int RESULT_CODE = 10;
    Button deleteB;
    Bundle bundle;
    String message;
    long messageID;
    boolean isTablet;

    public OCBusFragment() {
        this.isTablet = false;
    }

    public void setIsTablet(boolean isTablet) {
        this.isTablet = isTablet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.oc_details_fragment, null);

        deleteB = view.findViewById(R.id.delete_B);

        // If new fragment
        if (savedInstanceState == null) {
            bundle = getArguments(); // get arguments from OCBusNumberWindow Activity or from the OCDetails activity
        }
        // fragment re-builted
        else {
            bundle = savedInstanceState;
        }

        progressBar = view.findViewById(R.id.progressPB);

        message = bundle.getString(OCDatabaseHelper.KEY_MESSAGE);
        messageID = bundle.getLong(OCDatabaseHelper.KEY_ID);


        isTablet = bundle.getBoolean("isTablet");

        // attach event handler
        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTablet) {
                    ((OCBusNumberWindow) getActivity()).deleteMessage(bundle.getLong(OCDatabaseHelper.KEY_ID));
                    getFragmentManager().popBackStack();
                } else {
                    getActivity().setResult(RESULT_CODE, getActivity().getIntent());
                    getActivity().finish();
                }

            }
        });

        but1 = (Button) view.findViewById(R.id.butB1);
        but2 = (Button) view.findViewById(R.id.butB2);
        but3 = (Button) view.findViewById(R.id.butB3);
        but4 = (Button) view.findViewById(R.id.butB4);
        but5 = (Button) view.findViewById(R.id.butB5);

        textV1 = (TextView) view.findViewById(R.id.texV1);
        textV2 = (TextView) view.findViewById(R.id.texV2);
        textV3 = (TextView) view.findViewById(R.id.texV3);
        textV4 = (TextView) view.findViewById(R.id.texV4);
        textV5 = (TextView) view.findViewById(R.id.texV5);


        DemoTask dt = new DemoTask();
        dt.execute();
        DemoTask1 dt1 = new DemoTask1();
        dt1.execute();
        DemoTask2 dt2 = new DemoTask2();
        dt2.execute();
        DemoTask3 dt3 = new DemoTask3();
        dt3.execute();
        DemoTask4 dt4 = new DemoTask4();
        dt4.execute();
        DemoTask5 dt5 = new DemoTask5();
        dt5.execute();


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(OCDatabaseHelper.KEY_MESSAGE, message);
        outState.putLong(OCDatabaseHelper.KEY_ID, messageID);
        outState.putBoolean("isTablet", isTablet);
    }


    class DemoTask extends AsyncTask<Void, Integer, String> {

        private String busText;
        private String desText;

        private String busText1;
        private String desText1;


        @Override
        protected String doInBackground(Void... args) {


            XmlPullParserFactory pullParserFactory;

            stopNoDB = message;
            Log.i("message from DB", message);

            try {


                pullParserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = pullParserFactory.newPullParser();

                URL url = new URL("https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=" + stopNoDB + ".xml");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setDoInput(true);
                http.connect();
                InputStream is = http.getInputStream();
                parser.setInput(is, "UTF-8");

                ArrayList<OCBusNumberData> countries = parseXML(parser);

                String text = "";
                String textDes = "";


                for (OCBusNumberData OCBusNumberData : countries) {

                    text += "Bus No. " + OCBusNumberData.getRouteNo();
                    textDes = OCBusNumberData.getStopDescription();

                    al.add(OCBusNumberData.getRouteNo());

                }

                Log.i("YEAH", text);
                busText = text;
                desText = textDes;


                butS1 = al.get(0);
                butS2 = al.get(2);
                butS3 = al.get(4);
                butS4 = al.get(6);
                butS5 = al.get(8);


            } catch (XmlPullParserException e) {

                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "doInBackground";
        }


    }

    class DemoTask1 extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... args) {


            XmlPullParserFactory pullParserFactory1;


            stopNoDB = message;
            Log.i("message from DB", message);
            Log.i("First one", "https://api.octranspo1.com/v1.2/GetNextTripsForStop?stopNo=" + stopNoDB + "&routeNo=" + al.get(0) + "&apiKey=ab27db5b435b8c8819ffb8095328e775&appID=223eb5c3");

            try {

                pullParserFactory1 = XmlPullParserFactory.newInstance();
                XmlPullParser parser1 = pullParserFactory1.newPullParser();


                URL url1 = new URL("https://api.octranspo1.com/v1.2/GetNextTripsForStop?apiKey=ab27db5b435b8c8819ffb8095328e775&appID=223eb5c3&stopNo=" + stopNoDB + "&routeNo=" + al.get(0) + ".xml");
                HttpURLConnection http1 = (HttpURLConnection) url1.openConnection();
                http1.setDoInput(true);
                http1.connect();
                InputStream is1 = http1.getInputStream();
                parser1.setInput(is1, "UTF-8");

                ArrayList<OCBusNextStopData> countries1 = parseXML1(parser1);

                String text1 = "";
                String textDes1 = "";


                for (OCBusNextStopData OCBusNextStopData : countries1) {

                    text1 += getString(R.string.oc_destination) + " " + OCBusNextStopData.getTripDestination() + " " + getString(R.string.oc_start_time) + " " + OCBusNextStopData.getTripStartTime() + " " + getString(R.string.oc_late_time) + " " + OCBusNextStopData.getAdjustedScheduleTime() + " " + getString(R.string.oc_latitude) + " " + OCBusNextStopData.getLatitude() + " " + getString(R.string.oc_longitude) + " " + OCBusNextStopData.getLongitude() + " " + getString(R.string.oc_gps) + " " + OCBusNextStopData.getGpsSpeed() + "\n";
                }

                Log.i("First one parsed", text1);

                busText1 = text1;


            } catch (XmlPullParserException e) {

                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return "doInBackground1";
        }

    }


    class DemoTask2 extends AsyncTask<Void, Void, String> {


        private String busText1;


        @Override
        protected String doInBackground(Void... args) {


            XmlPullParserFactory pullParserFactory1;


            stopNoDB = message;
            Log.i("message from DB", message);
            Log.i("Second one", "https://api.octranspo1.com/v1.2/GetNextTripsForStop?stopNo=" + stopNoDB + "&routeNo=" + al.get(2) + "&apiKey=ab27db5b435b8c8819ffb8095328e775&appID=223eb5c3");

            try {

                pullParserFactory1 = XmlPullParserFactory.newInstance();
                XmlPullParser parser1 = pullParserFactory1.newPullParser();


                URL url1 = new URL("https://api.octranspo1.com/v1.2/GetNextTripsForStop?apiKey=ab27db5b435b8c8819ffb8095328e775&appID=223eb5c3&stopNo=" + stopNoDB + "&routeNo=" + al.get(2) + ".xml");
                HttpURLConnection http1 = (HttpURLConnection) url1.openConnection();
                http1.setDoInput(true);
                http1.connect();
                InputStream is1 = http1.getInputStream();
                parser1.setInput(is1, "UTF-8");

                ArrayList<OCBusNextStopData> countries1 = parseXML1(parser1);

                String text1 = "";
                String textDes1 = "";


                for (OCBusNextStopData OCBusNextStopData : countries1) {

                    text1 += getString(R.string.oc_destination) + " " + OCBusNextStopData.getTripDestination() + " " + getString(R.string.oc_start_time) + " " + OCBusNextStopData.getTripStartTime() + " " + getString(R.string.oc_late_time) + " " + OCBusNextStopData.getAdjustedScheduleTime() + " " + getString(R.string.oc_latitude) + " " + OCBusNextStopData.getLatitude() + " " + getString(R.string.oc_longitude) + " " + OCBusNextStopData.getLongitude() + " " + getString(R.string.oc_gps) + " " + OCBusNextStopData.getGpsSpeed() + "\n";
                }

                Log.i("Second one parsed", text1);

                busText2 = text1;


            } catch (XmlPullParserException e) {

                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "doInBackground1";
        }
    }


    class DemoTask3 extends AsyncTask<Void, Void, String> {


        private String busText1;


        @Override
        protected String doInBackground(Void... args) {


            XmlPullParserFactory pullParserFactory1;

            stopNoDB = message;
            Log.i("message from DB", message);
            Log.i("Third one", "https://api.octranspo1.com/v1.2/GetNextTripsForStop?stopNo=" + stopNoDB + "&routeNo=" + al.get(4) + "&apiKey=ab27db5b435b8c8819ffb8095328e775&appID=223eb5c3");

            try {

                pullParserFactory1 = XmlPullParserFactory.newInstance();
                XmlPullParser parser1 = pullParserFactory1.newPullParser();


                URL url1 = new URL("https://api.octranspo1.com/v1.2/GetNextTripsForStop?apiKey=ab27db5b435b8c8819ffb8095328e775&appID=223eb5c3&stopNo=" + stopNoDB + "&routeNo=" + al.get(4) + ".xml");
                HttpURLConnection http1 = (HttpURLConnection) url1.openConnection();
                http1.setDoInput(true);
                http1.connect();
                InputStream is1 = http1.getInputStream();
                parser1.setInput(is1, "UTF-8");

                ArrayList<OCBusNextStopData> countries1 = parseXML1(parser1);

                String text1 = "";
                String textDes1 = "";


                for (OCBusNextStopData OCBusNextStopData : countries1) {

                    text1 += getString(R.string.oc_destination) + " " + OCBusNextStopData.getTripDestination() + " " + getString(R.string.oc_start_time) + " " + OCBusNextStopData.getTripStartTime() + " " + getString(R.string.oc_late_time) + " " + OCBusNextStopData.getAdjustedScheduleTime() + " " + getString(R.string.oc_latitude) + " " + OCBusNextStopData.getLatitude() + " " + getString(R.string.oc_longitude) + " " + OCBusNextStopData.getLongitude() + " " + getString(R.string.oc_gps) + " " + OCBusNextStopData.getGpsSpeed() + "\n";
                    //   textView.setText(country.getRouteNo());
                }

                Log.i("Third one parsed", text1);

                busText3 = text1;


            } catch (XmlPullParserException e) {

                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return "doInBackground1";
        }

    }

    class DemoTask4 extends AsyncTask<Void, Void, String> {


        private String busText1;


        @Override
        protected String doInBackground(Void... args) {


            XmlPullParserFactory pullParserFactory1;


            stopNoDB = message;
            Log.i("message from DB", message);
            Log.i("Fourth one", "https://api.octranspo1.com/v1.2/GetNextTripsForStop?stopNo=" + stopNoDB + "&routeNo=" + al.get(6) + "&apiKey=ab27db5b435b8c8819ffb8095328e775&appID=223eb5c3");

            try {

                pullParserFactory1 = XmlPullParserFactory.newInstance();
                XmlPullParser parser1 = pullParserFactory1.newPullParser();


                URL url1 = new URL("https://api.octranspo1.com/v1.2/GetNextTripsForStop?apiKey=ab27db5b435b8c8819ffb8095328e775&appID=223eb5c3&stopNo=" + stopNoDB + "&routeNo=" + al.get(6) + ".xml");
                HttpURLConnection http1 = (HttpURLConnection) url1.openConnection();
                http1.setDoInput(true);
                http1.connect();
                InputStream is1 = http1.getInputStream();
                parser1.setInput(is1, "UTF-8");

                ArrayList<OCBusNextStopData> countries1 = parseXML1(parser1);

                String text1 = "";
                String textDes1 = "";


                for (OCBusNextStopData OCBusNextStopData : countries1) {

                    text1 += getString(R.string.oc_destination) + " " + OCBusNextStopData.getTripDestination() + " " + getString(R.string.oc_start_time) + " " + OCBusNextStopData.getTripStartTime() + " " + getString(R.string.oc_late_time) + " " + OCBusNextStopData.getAdjustedScheduleTime() + " " + getString(R.string.oc_latitude) + " " + OCBusNextStopData.getLatitude() + " " + getString(R.string.oc_longitude) + " " + OCBusNextStopData.getLongitude() + " " + getString(R.string.oc_gps) + " " + OCBusNextStopData.getGpsSpeed() + "\n";
                }

                Log.i("Fourth one parsed", text1);

                busText4 = text1;


            } catch (XmlPullParserException e) {

                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return "doInBackground1";
        }


    }

    class DemoTask5 extends AsyncTask<Void, Integer, String> {


        @Override
        protected String doInBackground(Void... args) {


            XmlPullParserFactory pullParserFactory1;

            publishProgress(25);

            stopNoDB = message;
            Log.i("message from DB", message);
            Log.i("Fifth one", "https://api.octranspo1.com/v1.2/GetNextTripsForStop?stopNo=" + stopNoDB + "&routeNo=" + al.get(8) + "&apiKey=ab27db5b435b8c8819ffb8095328e775&appID=223eb5c3");

            try {

                pullParserFactory1 = XmlPullParserFactory.newInstance();
                XmlPullParser parser1 = pullParserFactory1.newPullParser();


                URL url1 = new URL("https://api.octranspo1.com/v1.2/GetNextTripsForStop?apiKey=ab27db5b435b8c8819ffb8095328e775&appID=223eb5c3&stopNo=" + stopNoDB + "&routeNo=" + al.get(8) + ".xml");
                HttpURLConnection http1 = (HttpURLConnection) url1.openConnection();
                http1.setDoInput(true);
                http1.connect();
                InputStream is1 = http1.getInputStream();
                parser1.setInput(is1, "UTF-8");

                ArrayList<OCBusNextStopData> countries1 = parseXML1(parser1);

                String text1 = "";
                String textDes1 = "";

                for (OCBusNextStopData OCBusNextStopData : countries1) {


                    text1 += getString(R.string.oc_destination) + " " + OCBusNextStopData.getTripDestination() + " " + getString(R.string.oc_start_time) + " " + OCBusNextStopData.getTripStartTime() + " " + getString(R.string.oc_late_time) + " " + OCBusNextStopData.getAdjustedScheduleTime() + " " + getString(R.string.oc_latitude) + " " + OCBusNextStopData.getLatitude() + " " + getString(R.string.oc_longitude) + " " + OCBusNextStopData.getLongitude() + " " + getString(R.string.oc_gps) + " " + OCBusNextStopData.getGpsSpeed() + "\n";
                }

                Log.i("Fifth one parsed", text1);
                busText5 = text1;

                publishProgress(50);


            } catch (XmlPullParserException e) {

                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            publishProgress(75);


            return "doInBackground1";
        }


        protected void onProgressUpdate(Integer... value) {
            // set the progress bar visibility to visible
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }


        protected void onPostExecute(String result) {
            // TODO: do something with the feed

            super.onPostExecute(result);
            //           textView.setText(busText);
            //           stopDescript.setText(desText);
            but1.setText(butS1);
            but2.setText(butS2);
            but3.setText(butS3);
            but4.setText(butS4);
            but5.setText(butS5);

            textV1.setText(busText1);
            textV2.setText(busText2);
            textV3.setText(busText3);
            textV4.setText(busText4);
            textV5.setText(busText5);

            progressBar.setVisibility(View.INVISIBLE);

            al.clear();
            al1.clear();


        }


    }


    private ArrayList<OCBusNumberData> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<OCBusNumberData> countries = null;
        int eventType = parser.getEventType();
        OCBusNumberData OCBusNumberData = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    countries = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:


                    name = parser.getName();
                    if (name.equals("Route")) {
                        OCBusNumberData = new OCBusNumberData();
                        //OCBusNumberData.id = parser.nextText();
                    } else if (OCBusNumberData != null) {

                        if (name.equals(("RouteNo"))) {
                            OCBusNumberData.routeNo = parser.nextText();
                        } else if (name.equals("Direction")) {
                            OCBusNumberData.direction = parser.nextText();
                        } else if (name.equals("RouteHeading")) {
                            OCBusNumberData.stopDescription = parser.nextText();
                        } else if (name.equals(("StopDescription"))) ;

                    }
                    break;


                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("Route") && OCBusNumberData != null) {
                        countries.add(OCBusNumberData);
                    }
            }
            eventType = parser.next();
        }

        return countries;

    }


    private ArrayList<OCBusNextStopData> parseXML1(XmlPullParser parser1) throws XmlPullParserException, IOException {
        ArrayList<OCBusNextStopData> countries1 = null;
        int eventType1 = parser1.getEventType();
        OCBusNextStopData OCBusNextStopData1 = null;

        while (eventType1 != XmlPullParser.END_DOCUMENT) {
            String name1;
            switch (eventType1) {
                case XmlPullParser.START_DOCUMENT:
                    countries1 = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:


                    name1 = parser1.getName();
                    if (name1.equals("Trip")) {
                        OCBusNextStopData1 = new OCBusNextStopData();
                        //country.id = parser.nextText();
                    } else if (OCBusNextStopData1 != null) {

                        if (name1.equals(("TripDestination"))) {
                            OCBusNextStopData1.tripDestination = parser1.nextText();
                        } else if (name1.equals("TripStartTime")) {
                            OCBusNextStopData1.tripStartTime = parser1.nextText();
                        } else if (name1.equals("AdjustedScheduleTime")) {
                            OCBusNextStopData1.adjustedScheduleTime = parser1.nextText();
                        } else if (name1.equals("Latitude")) {
                            OCBusNextStopData1.latitude = parser1.nextText();
                        } else if (name1.equals("Longitude")) {
                            OCBusNextStopData1.longitude = parser1.nextText();
                        } else if (name1.equals("GPSSpeed")) {
                            OCBusNextStopData1.gpsSpeed = parser1.nextText();
                        }
                    }
                    break;

                case XmlPullParser.END_TAG:
                    name1 = parser1.getName();
                    if (name1.equalsIgnoreCase("Trip") && OCBusNextStopData1 != null) {
                        countries1.add(OCBusNextStopData1);
                    }
            }
            eventType1 = parser1.next();
        }
        return countries1;
    }


}



