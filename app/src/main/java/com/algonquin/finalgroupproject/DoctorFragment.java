package com.algonquin.finalgroupproject;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
public class DoctorFragment extends Patient {
    final String ACTIVITY_NAME = "DoctorFragment";
    Button btn_add;
    Button btn_cancel;
    ListView listView;
    EditText editTextName;
    EditText editTextAddr;
    EditText editTextBirt;
    EditText editTextPhon;
    EditText editTextHeal;
    EditText editTextDoc;
    EditText editTextDen;
    EditText editTextOpt;
    ArrayList<String> list = new ArrayList<>();
    Cursor cursor;
    PatientDatabaseHelper patientDatabaseHelper;
    DoctorAdapter docAdapter;

    String name;
    String DBO;
    String phone;
    String address;
    String card;
    String description;

    public Context getCon(){
        return getApplicationContext();
    }
    public DoctorFragment(){}
    public DoctorFragment(String name, String DOB, String phone, String address, String card, String description){
        setName(name);
    }

    public void setName(String name){
        this.name=name;

    }

        public String getName(){
        return name;
    }
    public String getPhon(){
        return phone;
    }
    public String getAddr(){
        return address;
    }
    public String getHeal(){
        return card;
    }
    public String getBirt(){
        return DBO;
    }
    public String getDoc(){
        return description;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_fragment);
        docAdapter = new DoctorAdapter(this);

        patientDatabaseHelper = new PatientDatabaseHelper(this);
        final SQLiteDatabase db = patientDatabaseHelper.getWritableDatabase();
        PatientsActivity patientsActivity = new PatientsActivity();

        btn_add = findViewById(R.id.okay);
        btn_cancel = findViewById(R.id.cancel);
        editTextName = findViewById(R.id.edit_name);
        Log.i("`````````````","edit_name"+editTextName);
        editTextAddr = findViewById(R.id.edit_address);
        editTextBirt = findViewById(R.id.edit_bd);
        editTextPhon = findViewById(R.id.edit_phone);
        editTextHeal = findViewById(R.id.edit_health_card);
        editTextDoc = findViewById(R.id.edit_des_doctor);
        editTextDen = findViewById(R.id.edit_des_dentist);
        editTextOpt = findViewById(R.id.edit_des_optometrist);
        //prepare for doctor---to add the data which already have in the list
        String selectQuery = "SELECT  * FROM " + "doctorTable" ;
        cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_PHONE)));
            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_NAME)));
            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_BOD)));
            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_PHONE)));
            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_ADDRESS)));
            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_CARD)));
            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_DOC)));
            cursor.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor's  column count =" + cursor.getColumnCount());
        View view = LayoutInflater.from(getApplication()).inflate(R.layout.activity_patients, null);
        listView = view.findViewById(R.id.list_view);
        Log.i(ACTIVITY_NAME,"list_view is:"+listView);

        listView.setAdapter (docAdapter);
        docAdapter.notifyDataSetChanged();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    list.add(editTextName.getText().toString());
                list.add(editTextPhon.getText().toString());
                list.add(editTextAddr.getText().toString());
                list.add(editTextHeal.getText().toString());
                list.add(editTextBirt.getText().toString());
                list.add(editTextDoc.getText().toString());
              */
                ContentValues cValues = new ContentValues();
                cValues.put(PatientDatabaseHelper.KEY_NAME, editTextName.getText().toString());
                cValues.put(PatientDatabaseHelper.KEY_PHONE, editTextPhon.getText().toString());
                cValues.put(PatientDatabaseHelper.KEY_ADDRESS, editTextAddr.getText().toString());
                cValues.put(PatientDatabaseHelper.KEY_CARD, editTextHeal.getText().toString());
                cValues.put(PatientDatabaseHelper.KEY_BOD, editTextBirt.getText().toString());
                cValues.put(PatientDatabaseHelper.KEY_DOC, editTextDoc.getText().toString());
                db.insert("doctorTable", null, cValues);
                // listViewDoc.setAdapter(docAdapter);
                docAdapter.notifyDataSetChanged();
                editTextName.setText("");
                editTextPhon.setText("");
                editTextAddr.setText("");
                editTextHeal.setText("");
                editTextBirt.setText("");
                editTextDoc.setText("");
                finish();
                //cursor = db.rawQuery("SELECT  * FROM " + "doctorTable", null);
                Toast.makeText(DoctorFragment.this, "Successfully added!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             finish();
                Toast.makeText(DoctorFragment.this, "User clicked cancel!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class DoctorAdapter extends ArrayAdapter<String> {
        public DoctorAdapter(Context ctx){super(ctx,0);}
        public String getItem(int position){
            return list.get(position);
        }
        public int getCount(){
            return list.size();
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = DoctorFragment.this.getLayoutInflater();
            View result;
            result = inflater.inflate(R.layout.activity_patient, null);
            return result;
        }
    }
}
