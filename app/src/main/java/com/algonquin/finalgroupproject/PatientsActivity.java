package com.algonquin.finalgroupproject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
public class PatientsActivity extends Activity {

    Button btn_doctor;
    Button btn_dentist;
    Button btn_optometrist;
    ListView listView;
    ListView listViewDen;
    ListView listViewOpt;

    ArrayList<String> list = new ArrayList<>();

    PatientAdapter docAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        docAdapter = new PatientAdapter(this);

        listView = findViewById(R.id.list_view);
//        listViewDen = findViewById(R.id.list_dentist);
//        listViewOpt = findViewById(R.id.list_optometrist);
        btn_doctor=findViewById(R.id.btn_doctor);
        btn_dentist=findViewById(R.id.btn_dentist);
        btn_optometrist=findViewById(R.id.btn_optometrist);


        listView.setAdapter (docAdapter);
        docAdapter.notifyDataSetChanged();

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
     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Bundle bundle = new Bundle();
                //  bundle.putBoolean("isTablet",isTablet);
                bundle.putLong("ID",id);
                bundle.putString("Message", docAdapter.getItem(i));
//                if(isTablet){
//                    frag = new MessageFragment(ChatWindow.this);
//                    frag.setArguments(bundle);
//                    // FragmentTransaction transaction =  getChildFragmentManager();
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frameLayout, frag).commit();
//                }
              //  else {
                    Intent intent = new Intent(PatientsActivity.this,PatientDetails.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Message", docAdapter.getItem(i));//pass the Database ID and message to next activity
                    startActivityForResult(intent,5);
              //  }
            }
        });
    }


    private class PatientAdapter extends ArrayAdapter<String> {
        public PatientAdapter(Context ctx){super(ctx,0);}
//        public int getCount(){
//            return listDoc.size();
//        }
        public String getItem(int position){
            return list.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = PatientsActivity.this.getLayoutInflater();
            View result;
           // if(position%2 == 0)
                result = inflater.inflate(R.layout.doctor_layout, null);
//            else
//                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = result.findViewById(R.id.text_view_name);
            message.setText(  getItem(position)  ); // get the string at position
            return result;
        }

    }

    @Override
    public void onDestroy(){
      //  cursor.close();
        super.onDestroy(); }
}
