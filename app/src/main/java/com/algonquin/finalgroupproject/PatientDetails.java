package com.algonquin.finalgroupproject;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class PatientDetails extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        PatientListviewFragment frag = new PatientListviewFragment(null);
        Bundle bundle = getIntent().getExtras();
        frag.setArguments(bundle);
        fragmentTransaction.add(R.id.patient_frameLayout,frag).commit();
    }
}
