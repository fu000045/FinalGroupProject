package com.algonquin.finalgroupproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Yali Fu on 3/24/2018.
 */

public class QuizMultiChoiceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflater.inflate(R.layout.activity_quiz_detail, null);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
