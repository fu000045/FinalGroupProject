package com.algonquin.finalgroupproject;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddPatientActivity extends Activity {
    PatientDatabaseHelper patientDatabaseHelper;
    final Context context = this;
    Boolean isTablet ;
    Cursor cursor;
    Button btn;
    Button btnDentist;
    Button btnOptometrist;
    ListView listView;
    ArrayList<String> list = new ArrayList<>();
    PatientAdapter patientAdapter;
    String doctorSelectQuery = "SELECT  * FROM " + PatientDatabaseHelper.DOCTOR_TABLE_NAME;
    String ageSelectQuery = "SELECT  * FROM " + PatientDatabaseHelper.AGE_TABLE_NAME;
    String name,phone,address,card,description,birthday;
    PatientListviewFragment frag = new PatientListviewFragment();
    private int endYear;
    private int endMonth;
    private int endDay;
    String updateName ;
    String updateAddress ;
    String updatePhone ;
    String updateBirthday ;
    String updateCard ;
    String updateDescription ;
    int forAge = 0;
    int age = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        patientAdapter = new PatientAdapter(this);
        phoneAdapter = new PhoneAdapter(this);
        cardAdapter = new CardAdapter(this);
        addressAdapter = new AddressAdapter(this);
        birthdayAdapter = new BirthdayAdapter(this);
        patientDatabaseHelper=new PatientDatabaseHelper(this);
        final SQLiteDatabase db = patientDatabaseHelper.getWritableDatabase();
        isTablet=(findViewById(R.id.patient_frameLayout)!=null);
        btn = findViewById(R.id.test1);
        btnDentist=findViewById(R.id.add_dentist);
        btnOptometrist=findViewById(R.id.add_opto);
        listView=findViewById(R.id.list_view);
        cursor = db.rawQuery(doctorSelectQuery,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Log.i("Test 1 ACTIVITY", "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_NAME)));
           //put the data that I have in the listview
            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_NAME)));
            cursor.moveToNext();
        }
        listView.setAdapter (patientAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //    Toast.makeText(AddPatientActivity.this, "Max age is :"+getMaxColumnData(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View newView = LayoutInflater.from(getApplication()).inflate(R.layout.doctor_dialog, null);
                builder.setView(newView);
                builder.setPositiveButton(R.string.patient_add_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        EditText message = newView.findViewById(R.id.new_message);EditText message2 = newView.findViewById(R.id.second_message);
                        EditText message3 = newView.findViewById(R.id.third_message);EditText message4 = newView.findViewById(R.id.fourth_message);
                        EditText message5 = newView.findViewById(R.id.fifty_message);EditText message6 = newView.findViewById(R.id.sixth_message);
                        patientDatabaseHelper = new PatientDatabaseHelper(context);
                        cursor = db.rawQuery(doctorSelectQuery,null);
                        list.add(message.getText().toString());
                        phoneList.add(message2.getText().toString());
                        addressList.add(message3.getText().toString());
                        cardList.add(message5.getText().toString());
                        birthdayList.add(message4.getText().toString());
                        ContentValues cValues= new ContentValues();
                        cValues.put(PatientDatabaseHelper.KEY_NAME,message.getText().toString());cValues.put(PatientDatabaseHelper.KEY_BOD,message2.getText().toString());
                        cValues.put(PatientDatabaseHelper.KEY_PHONE,message3.getText().toString());cValues.put(PatientDatabaseHelper.KEY_ADDRESS,message4.getText().toString());
                        cValues.put(PatientDatabaseHelper.KEY_CARD,message5.getText().toString());cValues.put(PatientDatabaseHelper.KEY_DOC,message6.getText().toString());
                        db.insert("doctorTable", null, cValues);
                        name=message.getText().toString();
                        phone=message2.getText().toString();
                        address=message3.getText().toString();
                        birthday=message4.getText().toString();
                        card=message5.getText().toString();
                        description=message6.getText().toString();
                        // listView.setAdapter (messageAdapter);
                        cardAdapter.notifyDataSetChanged();
                        birthdayAdapter.notifyDataSetChanged();
                        addressAdapter.notifyDataSetChanged();
                        phoneAdapter.notifyDataSetChanged();
                        patientAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
                      //  snackBarMessage = message.getText().toString();


//                        Intent showStats = new Intent(AddPatientActivity.this, PatientViewStatisticActivity.class);
//                        showStats.putExtra("age", forAge);
//                        startActivity(showStats);
                        cursor = db.rawQuery(doctorSelectQuery,null);
                    }
                });
                builder.setNegativeButton(R.string.patient_cancel_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }});

        btnDentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View newView = LayoutInflater.from(getApplication()).inflate(R.layout.dentist_dialog, null);
                builder.setView(newView);

                builder.setPositiveButton(R.string.patient_add_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        EditText message = newView.findViewById(R.id.new_message);EditText message2 = newView.findViewById(R.id.second_message);
                        EditText message3 = newView.findViewById(R.id.third_message);EditText message4 = newView.findViewById(R.id.fourth_message);
                        EditText message5 = newView.findViewById(R.id.fifty_message);EditText message6 = newView.findViewById(R.id.sixth_message);
                        patientDatabaseHelper = new PatientDatabaseHelper(context);
                        cursor = db.rawQuery(doctorSelectQuery,null);
                        list.add(message.getText().toString());
                        phoneList.add(message2.getText().toString());
                        addressList.add(message3.getText().toString());
                        cardList.add(message5.getText().toString());
                        birthdayList.add(message4.getText().toString());
                        ContentValues cValues= new ContentValues();
                        cValues.put(PatientDatabaseHelper.KEY_NAME,message.getText().toString());cValues.put(PatientDatabaseHelper.KEY_BOD,message2.getText().toString());
                        cValues.put(PatientDatabaseHelper.KEY_PHONE,message3.getText().toString());cValues.put(PatientDatabaseHelper.KEY_ADDRESS,message4.getText().toString());
                        cValues.put(PatientDatabaseHelper.KEY_CARD,message5.getText().toString());cValues.put(PatientDatabaseHelper.KEY_DOC,message6.getText().toString());
                        db.insert(PatientDatabaseHelper.DOCTOR_TABLE_NAME, null, cValues);
                        // listView.setAdapter (messageAdapter);
                        name=message.getText().toString();
                        phone=message2.getText().toString();
                        address=message3.getText().toString();
                        birthday=message4.getText().toString();
                        card=message5.getText().toString();
                        description=message6.getText().toString();
                        cardAdapter.notifyDataSetChanged();
                        birthdayAdapter.notifyDataSetChanged();
                        addressAdapter.notifyDataSetChanged();
                        phoneAdapter.notifyDataSetChanged();
                        patientAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()

                        cursor = db.rawQuery(doctorSelectQuery,null);
                    }
                });
                builder.setNegativeButton(R.string.patient_cancel_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }});

        btnOptometrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View newView = LayoutInflater.from(getApplication()).inflate(R.layout.dentist_dialog, null);
                builder.setView(newView);

                builder.setPositiveButton(R.string.patient_add_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        EditText message = newView.findViewById(R.id.new_message);EditText message2 = newView.findViewById(R.id.second_message);
                        EditText message3 = newView.findViewById(R.id.third_message);EditText message4 = newView.findViewById(R.id.fourth_message);
                        EditText message5 = newView.findViewById(R.id.fifty_message);EditText message6 = newView.findViewById(R.id.sixth_message);
                        patientDatabaseHelper = new PatientDatabaseHelper(context);

                        cursor = db.rawQuery(doctorSelectQuery,null);
                        list.add(message.getText().toString());
                        phoneList.add(message2.getText().toString());
                        addressList.add(message3.getText().toString());
                        cardList.add(message5.getText().toString());
                        birthdayList.add(message4.getText().toString());
                        ContentValues cValues= new ContentValues();
                        cValues.put(PatientDatabaseHelper.KEY_NAME,message.getText().toString());cValues.put(PatientDatabaseHelper.KEY_BOD,message2.getText().toString());
                        cValues.put(PatientDatabaseHelper.KEY_PHONE,message3.getText().toString());cValues.put(PatientDatabaseHelper.KEY_ADDRESS,message4.getText().toString());
                        cValues.put(PatientDatabaseHelper.KEY_CARD,message5.getText().toString());cValues.put(PatientDatabaseHelper.KEY_DOC,message6.getText().toString());
                        db.insert(PatientDatabaseHelper.DOCTOR_TABLE_NAME, null, cValues);
                        // listView.setAdapter (messageAdapter);
                        name=message.getText().toString();
                        phone=message2.getText().toString();
                        address=message3.getText().toString();
                        birthday=message4.getText().toString();
//
                        card=message5.getText().toString();
                        description=message6.getText().toString();
                        cardAdapter.notifyDataSetChanged();
                        birthdayAdapter.notifyDataSetChanged();
                        addressAdapter.notifyDataSetChanged();
                        phoneAdapter.notifyDataSetChanged();
                        patientAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
                        //  snackBarMessage = message.getText().toString();
                        // User clicked OK button
                        cursor = db.rawQuery(doctorSelectQuery,null);
                    }
                });
                builder.setNegativeButton(R.string.patient_cancel_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }});

//        cursor.getInteger( cursor.getColumnIndex(SQLiteAdapter.PRIME_ID) );
//        cursor.getString( cursor.getColumnIndex(SQLiteAdapter.USER_NAME) );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
//                id = patientAdapter.getItemId(i);
                cursor.moveToPosition(i);
//                cursor=db.rawQuery("SELECT * FROM "+PatientDatabaseHelper.DOCTOR_TABLE_NAME+" WHERE "+PatientDatabaseHelper.KEY_ID+" = "+id,null);
                    Bundle bundle = new Bundle();
                    bundle.putLong("ID", id);
                    bundle.putString("Message", patientAdapter.getItem(i));
//
//                bundle.putString("Birthday", birthdayAdapter.getItem(i));
//                    bundle.putString("Phone",phoneAdapter.getItem(i));
//                    //bundle.putString("Phone",phoneAdapter.getItem(i));
//                   // bundle.putString("Phone", cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_PHONE)));
//                    bundle.putString("Address", addressAdapter.getItem(i));
//                    bundle.putString("Card", cardAdapter.getItem(i));
//                    bundle.putString("Description", description);
//


                    bundle.putString("Birthday", cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_BOD)));
                    bundle.putString("Phone","6139812109");
                    //bundle.putString("Phone",phoneAdapter.getItem(i));
                   // bundle.putString("Phone", cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_PHONE)));
                    bundle.putString("Address", cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_ADDRESS)));
                    bundle.putString("Card", cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_CARD)));
                    bundle.putString("Description", "ladfgdfagj");
                    if (isTablet) {
                        frag = new PatientListviewFragment(AddPatientActivity.this);
                        frag.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.patient_frameLayout, frag).commit();
                    }
                    else
                        {
                            //cursor = db.rawQuery(doctorSelectQuery,null);
                        Intent intent = new Intent(AddPatientActivity.this, PatientDetails.class);
                        intent.putExtra("ID", id);
                        intent.putExtra("Message", patientAdapter.getItem(i));//pass the Database ID and message to next activity
                            intent.putExtra("Birthday", cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_BOD)));
                            intent.putExtra("Phone", cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_PHONE)));
                        intent.putExtra("Address", cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_ADDRESS)));
                        intent.putExtra("Card", cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_CARD)));
                        intent.putExtra("Description", "jdhghadfkjgdfkj");
//                            intent.putExtra("Birthday", birthdayAdapter.getItem(i));
//                            intent.putExtra("Phone", phoneAdapter.getItem(i));
//                            intent.putExtra("Address", addressAdapter.getItem(i));
//                            intent.putExtra("Card", cardAdapter.getItem(i));
//                            intent.putExtra("Description", description);
                        startActivityForResult(intent, 5);
                    }
                 } });
        }

    public void updateMessage(Long id, String name, String birthday,String phone,String address, String card, String description){
        final SQLiteDatabase db = patientDatabaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PatientDatabaseHelper.KEY_ID, id); //These Fields should be your String values of actual column names
        cv.put(PatientDatabaseHelper.KEY_NAME, name);
        cv.put(PatientDatabaseHelper.KEY_BOD, birthday);
        cv.put(PatientDatabaseHelper.KEY_ADDRESS, address);
        cv.put(PatientDatabaseHelper.KEY_PHONE, phone);
        cv.put(PatientDatabaseHelper.KEY_CARD, card);
        cv.put(PatientDatabaseHelper.KEY_DOC, description);
        db.update("doctorTable", cv, PatientDatabaseHelper.KEY_ID + " = " + id, null);
        list.clear();
        phoneList.clear();
        addressList.clear();
        cardList.clear();
        birthdayList.clear();
        cursor = db.rawQuery(doctorSelectQuery,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_NAME)));
            cursor.moveToNext(); }
        cardAdapter.notifyDataSetChanged();
        birthdayAdapter.notifyDataSetChanged();
        addressAdapter.notifyDataSetChanged();
        phoneAdapter.notifyDataSetChanged();
        patientAdapter.notifyDataSetChanged();
    }
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        if ((requestCode == 5)&&(resultCode == Activity.RESULT_OK)){
            Log.i("AddPatientActivity", "Returned to ChatWindow.onActivityResult");
            Long deleteId = data.getLongExtra("DeleteID", -1);
            deleteListMessage(deleteId);}
        else if ((requestCode == 5) && (resultCode == Activity.RESULT_CANCELED)) {
            Long updateId = data.getLongExtra("UpdateID", -1);
             updateName = data.getStringExtra("UpdateName");
             updateAddress = data.getStringExtra("UpdatePhone");
             updatePhone = data.getStringExtra("UpdateBirthday");
             updateBirthday = data.getStringExtra("UpdateAddress");
             updateCard = data.getStringExtra("UpdateCard");
             updateDescription = data.getStringExtra("UpdateDescription");
            updateMessage(updateId,updateName,updateAddress,updatePhone,updateBirthday,updateCard,updateDescription);}}
    public void deleteListMessage(Long id) {
        final SQLiteDatabase db = patientDatabaseHelper.getWritableDatabase();
        db.delete("doctorTable", PatientDatabaseHelper.KEY_ID+" = " + id , null);
        list.clear();
        phoneList.clear();
        addressList.clear();
        cardList.clear();
        birthdayList.clear();
        cursor = db.query(false, "doctorTable",
                new String[]{PatientDatabaseHelper.KEY_ID, PatientDatabaseHelper.KEY_NAME},null, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_NAME)));
           // Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString( cursor.getColumnIndex( PatientDatabaseHelper.KEY_MESSAGE) ) );
            cursor.moveToNext();}
        cardAdapter.notifyDataSetChanged();
        birthdayAdapter.notifyDataSetChanged();
        addressAdapter.notifyDataSetChanged();
        phoneAdapter.notifyDataSetChanged();
        patientAdapter.notifyDataSetChanged();
    }

    private class PatientAdapter extends ArrayAdapter<String> {
        public PatientAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount() {
            return list.size();
        }
        public String getItem(int position) {
            return list.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.activity_patient, null);
            TextView message = view.findViewById(R.id.text_view_patient);
            message.setText(getItem(position)); // get the string at position
            return view;}
        public long getItemId(int position) {
            cursor.moveToPosition(position);
            long id = 0;
            if(cursor.getCount()>0) {
                 id = cursor.getLong(cursor.getColumnIndex(PatientDatabaseHelper.KEY_ID));}
            return id;}}

    private class BirthdayAdapter extends ArrayAdapter<String> {
        public BirthdayAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount() {
            return birthdayList.size();
        }
        public String getItem(int position) {
            return birthdayList.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.activity_patient, null);
            TextView message = view.findViewById(R.id.text_view_patient);
            message.setText(getItem(position)); // get the string at position
            return view;}
        public long getItemId(int position) {
            cursor.moveToPosition(position);
            long id = 0;
            if(cursor.getCount()>0) {
                id = cursor.getLong(cursor.getColumnIndex(PatientDatabaseHelper.KEY_ID));}
            return id;}}

    private class AddressAdapter extends ArrayAdapter<String> {
        public AddressAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount() {
            return addressList.size();
        }
        public String getItem(int position) {
            return addressList.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.activity_patient, null);
            TextView message = view.findViewById(R.id.text_view_patient);
            message.setText(getItem(position)); // get the string at position
            return view;}
        public long getItemId(int position) {
            cursor.moveToPosition(position);
            long id = 0;
            if(cursor.getCount()>0) {
                id = cursor.getLong(cursor.getColumnIndex(PatientDatabaseHelper.KEY_ID));}
            return id;}}

    private class CardAdapter extends ArrayAdapter<String> {
        public CardAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount() {
            return cardList.size();
        }
        public String getItem(int position) {
            return cardList.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.activity_patient, null);
            TextView message = view.findViewById(R.id.text_view_patient);
            message.setText(getItem(position)); // get the string at position
            return view;}
        public long getItemId(int position) {
            cursor.moveToPosition(position);
            long id = 0;
            if(cursor.getCount()>0) {
                id = cursor.getLong(cursor.getColumnIndex(PatientDatabaseHelper.KEY_ID));}
            return id;}}

    private class PhoneAdapter extends ArrayAdapter<String> {
        public PhoneAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount() {
            return phoneList.size();
        }
        public String getItem(int position) {
            return phoneList.get(position);}
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.activity_patient, null);
            TextView message = view.findViewById(R.id.text_view_patient);
            message.setText(getItem(position)); // get the string at position
            return view;}
        public long getItemId(int position) {
            cursor.moveToPosition(position);
            long id = 0;
            if(cursor.getCount()>0) {
                id = cursor.getLong(cursor.getColumnIndex(PatientDatabaseHelper.KEY_ID));}
            return id;}}

    public void removeFragment()
    {
        getFragmentManager().beginTransaction().remove(frag).commit();
    }
    @Override
    public void onDestroy(){
        cursor.close();
        super.onDestroy(); }

ArrayList<String> birthdayList = new ArrayList<>();
    ArrayList<String> phoneList = new ArrayList<>();
    ArrayList<String> addressList = new ArrayList<>();
    ArrayList<String> cardList = new ArrayList<>();
    BirthdayAdapter birthdayAdapter;
    CardAdapter cardAdapter;
    PhoneAdapter phoneAdapter;
    AddressAdapter addressAdapter;
}
