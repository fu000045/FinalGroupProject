package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class PatientsActivity extends Activity {

    Button btn_doctor;
    Button btn_dentist;
    Button btn_optometrist;
    ListView listViewDoc;
    ListView listViewDen;
    ListView listViewOpt;
    EditText editTextName;
    EditText editTextAddr;
    EditText editTextBirt;
    EditText editTextPhon;
    EditText editTextHeal;
    EditText editTextDoc;
    EditText editTextDen;
    EditText editTextOpt;
    ArrayList<String> listDoc = new ArrayList<>();
    ArrayList<String> listDen = new ArrayList<>();
    ArrayList<String> listOpt = new ArrayList<>();
    Cursor cursor;
    PatientDatabaseHelper patientDatabaseHelper;
    PatientAdapter docAdapter;
    PatientAdapter denAdapter;
    PatientAdapter optAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        docAdapter = new PatientAdapter(this);
        denAdapter = new PatientAdapter(this);
        optAdapter = new PatientAdapter(this);

        patientDatabaseHelper = new PatientDatabaseHelper(this);
        final SQLiteDatabase db = patientDatabaseHelper.getWritableDatabase();

        //prepare for doctor---to add the data which already have in the list
        String selectQuery = "SELECT  * FROM " + "doctorTable" ;
        cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            listDoc.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_NAME)));
            listDoc.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_BOD)));
            listDoc.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_PHONE)));
            listDoc.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_ADDRESS)));
            listDoc.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_CARD)));
            listDoc.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_DOC)));
            cursor.moveToNext();
        }
        //Log.i(ACTIVITY_NAME, "Cursor's  column count =" + cursor.getColumnCount());
        //listView.setAdapter (messageAdapter);
        docAdapter.notifyDataSetChanged();


        //add new patient to the database
        btn_doctor=findViewById(R.id.btn_doctor);
        btn_doctor.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              listDoc.add(editTextName.getText().toString());
                                              ContentValues cValues = new ContentValues();
                                              cValues.put(PatientDatabaseHelper.KEY_NAME, editTextName.getText().toString());
                                              db.insert("doctorTable", null, cValues);
                                              listViewDoc.setAdapter(docAdapter);
                                              docAdapter.notifyDataSetChanged();
                                              editTextName.setText("");
                                          }
//                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
//                editText.setText("");
//                cursor = db.query(false, ChatDatabaseHelper.TABLE_NAME,
//                        new String[] { ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE }, null, null, null, null, null, null);
//            }
                                          });
                                      }


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
//                Bundle bundle = new Bundle();
//                //  bundle.putBoolean("isTablet",isTablet);
//                bundle.putLong("ID",id);
//                bundle.putString("Message", messageAdapter.getItem(i));
//                if(isTablet){
//                    frag = new MessageFragment(ChatWindow.this);
//                    frag.setArguments(bundle);
//                    // FragmentTransaction transaction =  getChildFragmentManager();
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frameLayout, frag).commit();
//                }
//                else {
//                    Intent intent = new Intent(ChatWindow.this,MessageDetailsActivity.class);
//                    intent.putExtra("ID", id);
//                    intent.putExtra("Message", messageAdapter.getItem(i));//pass the Database ID and message to next activity
//                    startActivityForResult(intent,5);
//                }
//            }
//        });


    private class PatientAdapter extends ArrayAdapter<String> {
        public PatientAdapter(Context ctx){super(ctx,0);}


    }
}
