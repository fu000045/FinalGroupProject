package com.algonquin.finalgroupproject.octranspo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.algonquin.finalgroupproject.R;

import java.util.Locale;


public class OCFragment extends Fragment {
    public static final int RESULT_CODE = 10;
    TextView messageTV;
    TextView idTV;
    Button deleteB;
    Bundle bundle;
    String message;
    long messageID;
    boolean isTablet;

    public OCFragment() {
        this.isTablet = false;
    }

    public void setIsTablet(boolean isTablet) {
        this.isTablet = isTablet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.oc_fragment_detail, null);

        messageTV = view.findViewById(R.id.message_TV);
        idTV = view.findViewById(R.id.id_TV);
        deleteB = view.findViewById(R.id.delete_B);


        if (savedInstanceState == null) {
            bundle = getArguments();
        } else {
            bundle = savedInstanceState;
        }

        messageTV.setText(message);
        idTV.setText(String.format(Locale.CANADA, "%d", messageID));


        return view;
    }


}
