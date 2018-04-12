package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.os.Bundle;

public class Patient extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
    }

    protected String information;


    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
