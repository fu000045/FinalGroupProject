package com.algonquin.finalgroupproject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
public class PatientsActivity extends Activity {
    Button btn_doctor;
    Button btn_dentist;
    Button btn_optometrist;
    ListView listView;
    EditText editText;
    Cursor cursor;
    ArrayList<String> listType = new ArrayList<>();
    PatientDatabaseHelper patientDatabaseHelper;
    PatientAdapter patientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        patientAdapter = new PatientAdapter(this);
        listView = findViewById(R.id.list_view);
        btn_doctor = findViewById(R.id.btn_doctor);
        btn_dentist = findViewById(R.id.btn_dentist);
        btn_optometrist = findViewById(R.id.btn_optometrist);
        listView.setAdapter(patientAdapter);
        //load patients into list
        patientDatabaseHelper = new PatientDatabaseHelper(this);
        final SQLiteDatabase db = patientDatabaseHelper.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + "doctorTable";
        cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            // Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_PHONE)));
//            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_NAME)));
//            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_BOD)));
//            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_PHONE)));
//            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_ADDRESS)));
//            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_CARD)));
//            list.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_DOC)));


            String name = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_NAME));
            Log.i("in PatientActivity","name that get from database is : "+name);
            DoctorFragment doctorFragment = new DoctorFragment(name, "DOB", "PHONE", "ADDRESS", "CARD", "DES");

            listType.add(doctorFragment.getName());

            View view = LayoutInflater.from(getApplication()).inflate(R.layout.activity_doctor_fragment, null);
            editText = view.findViewById(R.id.edit_name);
            Log.i("############list is :", "" + listType.toString());
            cursor.moveToNext();
        }

        listView.setAdapter(patientAdapter);
        patientAdapter.notifyDataSetChanged();


        //add new patient to the database
//        btn_doctor.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View view) {
//                                              listDoc.add(editTextName.getText().toString());
//                                              ContentValues cValues = new ContentValues();
//                                              cValues.put(PatientDatabaseHelper.KEY_NAME, editTextName.getText().toString());
//                                              db.insert("doctorTable", null, cValues);
//                                             // listViewDoc.setAdapter(docAdapter);
//                                              docAdapter.notifyDataSetChanged();
//                                              editTextName.setText("");
//                                              cursor = db.rawQuery("SELECT  * FROM " + "doctorTable" ,null);
//                                              Toast.makeText(PatientsActivity.this, "Successfully added!", Toast.LENGTH_SHORT).show();
//                                             // finish();
//                                          }
//                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
//                editText.setText("");
//                cursor = db.query(false, ChatDatabaseHelper.TABLE_NAME,
//                        new String[] { ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE }, null, null, null, null, null, null);
        //           });

        btn_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientsActivity.this, DoctorFragment.class);
                startActivity(intent);
            }
        });

        btn_dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientsActivity.this, DoctorFragment.class);
                startActivity(intent);
            }
        });

        btn_optometrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientsActivity.this, DoctorFragment.class);
                startActivity(intent);
            }
        });
     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Bundle bundle = new Bundle();
                bundle.putLong("ID",id);
                bundle.putString("Message", patientAdapter.getItem(i));

                    Intent intent = new Intent(PatientsActivity.this,PatientDetails.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Message", patientAdapter.getItem(i));//pass the Database ID and message to next activity
                    startActivityForResult(intent,5);

            }
        });
    }

private class PatientAdapter extends ArrayAdapter<String> {
        public PatientAdapter(Context ctx){super(ctx,0);}

        public int getCount(){
            return listType.size();
        }

public String getItem(int position){
    return listType.get(position);
}

        public View getView(int position, View convertView, ViewGroup parent){
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.activity_patient, null);
            EditText message = view.findViewById(R.id.edit_view_patient);
            message.setText(  getItem(position)  ); // get the string at position
            return view;
        }
    }

    @Override
    public void onDestroy(){
        cursor.close();
        super.onDestroy(); }
}
