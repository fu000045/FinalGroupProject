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
    String phone;
    String birthday;
    String address;
    String card;
    String description;
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
        phone = bundle.getString("Phone");
        description = bundle.getString("Description");
        address = bundle.getString("Address");
        card = bundle.getString("Card");
        birthday = bundle.getString("Birthday");
        messageId = bundle.getLong("ID");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View gui = inflater.inflate(R.layout.activity_update_doctor, null);
        TextView id = gui.findViewById(R.id.text_view_id);
        EditText message =  gui.findViewById(R.id.update_edit_text_name);
        EditText edit_phone = gui.findViewById(R.id.update_edit_text_phone);
        EditText edit_birthday = gui.findViewById(R.id.update_edit_text_birthday);
        EditText edit_address = gui.findViewById(R.id.update_edit_text_address);
        EditText edit_card = gui.findViewById(R.id.update_edit_text_card);
        EditText edit_description = gui.findViewById(R.id.update_edit_text_description);
        id.setText("ID:" + messageId );
        message.setText(usermessage);
        edit_phone.setText(phone);
        edit_birthday.setText(birthday);
        edit_address.setText(address);
        edit_card.setText(card);
        edit_description.setText(description);
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

        Button btn_update = gui.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.i("MessageFragment", "User clicked Delete Message button");
                //is for teblet
//                    patientsActivity.deleteListMessage(messageId);
//                    patientsActivity.removeFragment();
                Intent intent = new Intent();
                intent.putExtra("UpdateID", messageId);
                intent.putExtra("UpdateName", usermessage);
                intent.putExtra("UpdatePhone", phone);
                intent.putExtra("UpdateBirthday", birthday);
                intent.putExtra("UpdateAddress", address);
                intent.putExtra("UpdateCard", card);
                intent.putExtra("UpdateDescription", description);
                getActivity().setResult(Activity.RESULT_CANCELED, intent);
                getActivity().finish();
            }
        });
        return gui;
    }
}
