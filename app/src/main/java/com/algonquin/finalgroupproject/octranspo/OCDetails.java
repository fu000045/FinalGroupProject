package com.algonquin.finalgroupproject.octranspo;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.algonquin.finalgroupproject.R;

public class OCDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oc_info_details);


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        OCBusFragment mf = new OCBusFragment();
        ft.add(R.id.activityMessageDetails, mf);

        mf.setArguments(getIntent().getExtras());
        ft.commit();
    }
}
