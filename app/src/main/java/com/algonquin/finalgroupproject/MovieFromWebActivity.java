package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MovieFromWebActivity extends Activity {
   //private ImageView img_movie;
    TextView tv1;
//    private TextView text_title;
//    private TextView text_actors;
//    private TextView text_length;
//    private TextView text_genre;
//    private TextView text_description;
//    private TextView text_rating;
//    private TextView text_http;
    //private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_from_web);

       // img_movie = findViewById(R.id.movieImage);
//        text_title = findViewById(R.id.title);
//        text_actors = findViewById(R.id.actors);
//        text_length = findViewById(R.id.length);
//        text_genre = findViewById(R.id.genre);
//        text_description = findViewById(R.id.description);
//        text_rating = findViewById(R.id.rating);
//        text_http = findViewById(R.id.http);
        try {
            InputStream is = getAssets().open("torunski.ca/CST2335/MovieInfo.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("movie");

            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;
                    tv1.setText(tv1.getText()+"\nTitle : " + getValue("Title", element2)+"\n");
                    tv1.setText(tv1.getText()+"Actors : " + getValue("Actors", element2)+"\n");
                    tv1.setText(tv1.getText()+"-----------------------");
                }
            }

        } catch (Exception e) {e.printStackTrace();}

    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

}
