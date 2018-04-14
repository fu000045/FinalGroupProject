package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class QuizDetail extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_detail);

        Bundle bundle = getIntent().getBundleExtra("QuizDetail");

        //start a FragmentTransaction to add a fragment to the FrameLayout
        QuizFragment myFragment = new QuizFragment();
        myFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.Quiz_Detail_Framelayout, myFragment).commit();
    }
}
