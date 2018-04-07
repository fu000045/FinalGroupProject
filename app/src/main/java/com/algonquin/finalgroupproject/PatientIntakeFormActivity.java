package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PatientIntakeFormActivity extends Activity {

    //public ListView list ;
    Button btn_patients;
    Button btn_download;
    Button btn_view;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_intake_form);

       // list = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);
         btn_patients = findViewById(R.id.button_patients);
         btn_download = findViewById(R.id.button_download);
         btn_view = findViewById(R.id.button_view_statistics);

        btn_patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientIntakeFormActivity.this, PatientsActivity.class);
                startActivity(intent);
            }
        });
//
//        btn_download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(PatientIntakeFormActivity.this, DentistActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btn_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(PatientIntakeFormActivity.this, OptometristActivity.class);
//                startActivity(intent);
//            }
//        });
        }


      //  public ListView getList(){
//        return list;
//        }



}
