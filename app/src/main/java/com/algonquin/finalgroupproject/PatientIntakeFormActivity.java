package com.algonquin.finalgroupproject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PatientIntakeFormActivity extends Activity {

    Button btn_patients;
    Button btn_download;
    Button btn_view;
    Button btn_help;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_intake_form);

        progressBar = findViewById(R.id.progressBar);
         btn_patients = findViewById(R.id.button_patients);
         btn_download = findViewById(R.id.button_download);
         btn_view = findViewById(R.id.button_view_statistics);
         btn_help = findViewById(R.id.btn_help);

        btn_patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(PatientIntakeFormActivity.this, PatientsActivity.class);
                Intent intent = new Intent(PatientIntakeFormActivity.this, AddPatientActivity.class);
                startActivity(intent);
                Toast toast = Toast.makeText(PatientIntakeFormActivity.this, "User clicked add update or delete", Toast.LENGTH_LONG);
                toast.show();
            }
        });
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientIntakeFormActivity.this, PatientLoadXML.class);
                startActivity(intent);
            }
        });

        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            onOptionsItemSelected(1);
            }
        });}
    public boolean onOptionsItemSelected(int i){
        switch( i ) {
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle(R.string.dialog_title2);
                builder.setTitle("HELP");
// Create the AlertDialog
                builder.setMessage("Wenchong Xu ; 1 ; add and delete and update the information and various button can have various functions");
                AlertDialog dialog = builder.create();
                dialog.show();
                Snackbar.make(findViewById(R.id.btn_help), "HELP BUTTON CLICKED", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
        return true;
        }


}
