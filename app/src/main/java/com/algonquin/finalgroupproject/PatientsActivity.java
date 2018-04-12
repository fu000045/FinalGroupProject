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
    Context newContext;
    ArrayList<Patient> list = new ArrayList<>();
    PatientDatabaseHelper patientDatabaseHelper;
    PatientAdapter patientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

//        newContext  = doctorFragment.getApplicationContext();
//        patientAdapter = new PatientAdapter(newContext);
        patientAdapter = new PatientAdapter(this);

        listView = findViewById(R.id.list_view);
        btn_doctor = findViewById(R.id.btn_doctor);
        btn_dentist = findViewById(R.id.btn_dentist);
        btn_optometrist = findViewById(R.id.btn_optometrist);

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

            //create object from data:

            String name = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_NAME));
            DoctorFragment doctorFragment = new DoctorFragment(name, "DOB", "PHONE", "ADDRESS", "CARD", "DES");
            list.add(doctorFragment);
            // list.add( new Patient() );
//            DoctorFragment doctorFragment = new DoctorFragment();
//            String name = doctorFragment.getName();
//            View result;
//            LayoutInflater inflater = DoctorFragment.class.getLayoutInflater();
//            result = inflater.inflate(R.layout.activity_patients, null);
//            EditText editText = result.findViewById(R.id.edit_name);
//            list.add(name);
            //phone

            View view = LayoutInflater.from(getApplication()).inflate(R.layout.activity_doctor_fragment, null);
            editText = view.findViewById(R.id.edit_name);
            // list.add(editText.getText().toString());
            Log.i("############list is :", "" + list.toString());
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
//     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
//                Bundle bundle = new Bundle();
//                //  bundle.putBoolean("isTablet",isTablet);
//                bundle.putLong("ID",id);
//                bundle.putString("Message", patientAdapter.getItem(i));
////                if(isTablet){
////                    frag = new MessageFragment(ChatWindow.this);
////                    frag.setArguments(bundle);
////                    // FragmentTransaction transaction =  getChildFragmentManager();
////                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
////                    transaction.replace(R.id.frameLayout, frag).commit();
////                }
//              //  else {
//                    Intent intent = new Intent(PatientsActivity.this,PatientDetails.class);
//                    intent.putExtra("ID", id);
//                    intent.putExtra("Message", patientAdapter.getItem(i));//pass the Database ID and message to next activity
//                    startActivityForResult(intent,5);
//              //  }
//            }
//        });}
    }

     public ListView getListView(){
         return listView;
    }



    private class PatientAdapter extends ArrayAdapter<Patient> {
        public PatientAdapter(Context ctx){super(ctx,0);}
        public int getCount(){
            return list.size();
        }
        public Patient getItem(int position){
            return list.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = PatientsActivity.this.getLayoutInflater();
            View result;
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.activity_patient, null);
           // if(position%2 == 0)
          //      result = inflater.inflate(R.layout.doctor_layout, null);
//            else
//                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = view.findViewById(R.id.text_view);
            message.setText(  getItem(position).getInformation()  ); // get the string at position


            //insert here to the list just name and DOB
            //list.add(doctorFragment.getName());

            return view;
        }

    }

    @Override
    public void onDestroy(){
        cursor.close();
        super.onDestroy(); }
}
