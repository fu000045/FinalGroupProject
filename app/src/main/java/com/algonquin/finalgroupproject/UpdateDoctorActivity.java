package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateDoctorActivity extends Fragment {

    String usermessage;
    Long messageId;
    PatientsActivity patientsActivity = null;

    public UpdateDoctorActivity(){ }
    public UpdateDoctorActivity(PatientsActivity cw){
        patientsActivity = cw;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        usermessage = bundle.getString("Message");
        messageId = bundle.getLong("ID");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View gui = inflater.inflate(R.layout.activity_update_doctor, null);
        TextView id = gui.findViewById(R.id.text_view_id);
        EditText message =  gui.findViewById(R.id.update_edit_text_name);
        id.setText("ID:" + messageId );
        message.setText(usermessage);
        Button button = gui.findViewById(R.id.btn_delete);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.i("MessageFragment", "User clicked Delete Message button");
                //is for teblet
//                    patientsActivity.deleteListMessage(messageId);
//                    patientsActivity.removeFragment();
                Intent intent = new Intent();
                intent.putExtra("DeleteID", messageId);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });
        return gui;
    }
}
