package com.algonquin.finalgroupproject;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class PatientListviewFragment extends Fragment {
    String usermessage;
    String phone;
    String birthday;
    String address;
    String card;
    String description;
    Long messageId;
    AddPatientActivity addPatientActivity = null;
    EditText edit_birthday;
    EditText edit_address;
    EditText edit_card ;
    EditText edit_description;
    EditText edit_phone;
    EditText message;
    public PatientListviewFragment(){ }
    public PatientListviewFragment(AddPatientActivity cw){
        addPatientActivity = cw;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        usermessage = bundle.getString("Message");
        messageId = bundle.getLong("ID");
        phone = bundle.getString("Phone");
        description = bundle.getString("Description");
        address = bundle.getString("Address");
        card = bundle.getString("Card");
        birthday = bundle.getString("Birthday");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View gui = inflater.inflate(R.layout.activity_patient_listview_fragment, null);
        TextView id = gui.findViewById(R.id.text_view_id);
        message = gui.findViewById(R.id.update_edit_text_name);
        edit_phone = gui.findViewById(R.id.update_edit_text_phone);
        edit_birthday = gui.findViewById(R.id.update_edit_text_birthday);
        edit_address = gui.findViewById(R.id.update_edit_text_address);
        edit_card = gui.findViewById(R.id.update_edit_text_card);
        edit_description = gui.findViewById(R.id.update_edit_text_description);
       // id.setText("ID:" + messageId );
        Bundle bundle = getArguments();
        bundle.getLong("ID");
        id.setText("ID:" + bundle.getLong("ID") );
        message.setText(bundle.getString("Message"));
        edit_phone.setText(bundle.getString("Phone"));
        edit_birthday.setText(bundle.getString("Birthday"));
        edit_address.setText(bundle.getString("Address"));
        edit_card.setText(bundle.getString("Card"));
        edit_description.setText(bundle.getString("Description"));
//        message.setText(usermessage);
//        edit_phone.setText(phone);
//        edit_birthday.setText(birthday);
//        edit_address.setText(address);
//        edit_card.setText(card);
//        edit_description.setText(description);
        Button button = gui.findViewById(R.id.btn_delete);
        Button btn_update=gui.findViewById(R.id.btn_update);

        btn_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.i("MessageFragment", "User clicked Delete Message button");
             //   addPatientActivity.updateMessate();
                if (addPatientActivity == null) {
                    Intent intent = new Intent();
                    intent.putExtra("UpdateID", messageId);
                    usermessage=message.getText().toString();
                    phone=edit_phone.getText().toString();
                    birthday=edit_birthday.getText().toString();
                    address=edit_address.getText().toString();
                    card=edit_card.getText().toString();
                    description=edit_description.getText().toString();
                    intent.putExtra("UpdateName", usermessage);
                    intent.putExtra("UpdatePhone", phone);
                    intent.putExtra("UpdateBirthday", birthday);
                    intent.putExtra("UpdateAddress", address);
                    intent.putExtra("UpdateCard", card);
                    intent.putExtra("UpdateDescription", description);
                    getActivity().setResult(Activity.RESULT_CANCELED, intent);
                    getActivity().finish();
                }
                else{
                    addPatientActivity.updateMessage(messageId,usermessage,address,phone,birthday,card,description);
                    addPatientActivity.removeFragment();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.i("MessageFragment", "User clicked Delete Message button");
                if (addPatientActivity == null) {
                    Intent intent = new Intent();
                    intent.putExtra("DeleteID", messageId);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
                else{
                    addPatientActivity.deleteListMessage(messageId);
                    addPatientActivity.removeFragment();
                }
            }
        });
        return gui;
    }
}
