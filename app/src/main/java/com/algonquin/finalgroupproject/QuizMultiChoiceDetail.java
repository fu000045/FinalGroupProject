package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class QuizMultiChoiceDetail extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_multi_choice_detail);

        Bundle bundle = getIntent().getBundleExtra("QuizMultiChoiceDetail");

        //start a FragmentTransaction to add a fragment to the FrameLayout
        QuizMultiChoiceFragment myFragment = new QuizMultiChoiceFragment();
        myFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.Quiz_MultiChoice_Framlayout, myFragment).commit();
    }
}
