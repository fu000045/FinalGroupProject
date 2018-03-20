package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PatientIntakeFormActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_intake_form);

        Button btn_doctorOffice = findViewById(R.id.doctorOffice);
        Button btn_dentist = findViewById(R.id.dentistPatient);
        Button btn_optometrist = findViewById(R.id.optometrist);

        btn_doctorOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientIntakeFormActivity.this, DoctorOfficeActivity.class);
                startActivity(intent);
            }
        });

        btn_dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientIntakeFormActivity.this, DentistActivity.class);
                startActivity(intent);
            }
        });

        btn_optometrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientIntakeFormActivity.this, OptometristActivity.class);
                startActivity(intent);
            }
        });
        }
}
