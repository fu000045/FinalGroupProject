package com.algonquin.finalgroupproject;
import android.app.Activity;
import android.content.ContentValues;
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
    UpdateDoctorActivity updateDoctorFrag;
    String edit_birthday;
    String edit_phone;
    String edit_card;
    String edit_description;
    String edit_address;
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
        patientDatabaseHelper = new PatientDatabaseHelper(this);
        final SQLiteDatabase db = patientDatabaseHelper.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + "doctorTable";
        cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_NAME));
            String birthday = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_BOD));
            String phone = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_PHONE));
            String address = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_ADDRESS));
            String card = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_CARD));
            String description = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_DOC));
            Log.i("in PatientActivity","name that get from database is : "+name);
            DoctorFragment doctorFragment = new DoctorFragment(name, birthday, phone, address,card, description);
            listType.add(doctorFragment.getName());
            edit_birthday = doctorFragment.getBirt();
            edit_phone = doctorFragment.getPhon();
            edit_address = doctorFragment.getAddr();
            edit_card = doctorFragment.getHeal();
            edit_description = doctorFragment.getDoc();
//            listType.add(doctorFragment.getBirt());
//            listType.add(doctorFragment.getPhon());
//            listType.add(doctorFragment.getAddr());
//            listType.add(doctorFragment.getHeal());
//            listType.add(doctorFragment.getDoc());
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.activity_doctor_fragment, null);
            editText = view.findViewById(R.id.edit_name);
            Log.i("############list is :", "" + listType.toString());
            cursor.moveToNext();
        }
        listView.setAdapter(patientAdapter);
        patientAdapter.notifyDataSetChanged();
        btn_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientsActivity.this, DoctorFragment.class);
                startActivity(intent);}});

        btn_dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientsActivity.this, DoctorFragment.class);
                startActivity(intent);}});

        btn_optometrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientsActivity.this, DoctorFragment.class);
                startActivity(intent);}});
     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Bundle bundle = new Bundle();
                bundle.putLong("ID",id);
                bundle.putString("Message", patientAdapter.getItem(i));
                bundle.putString("Phone",edit_phone);
                bundle.putString("Description",edit_description);
                bundle.putString("Address",edit_address);
                bundle.putString("Card",edit_card);
                bundle.putString("Birthday",edit_birthday);
                    Intent intent = new Intent(PatientsActivity.this,PatientDetails.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("Message", patientAdapter.getItem(i));//pass the Database ID and message to next activity
                intent.putExtra("Phone", edit_phone);
                intent.putExtra("Description", edit_description);
                intent.putExtra("Address", edit_address);
                intent.putExtra("Card", edit_card);
                intent.putExtra("Birthday", edit_birthday);
//                    startActivityForResult(intent,5);
                startActivityForResult(intent,5);
            } });
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
            TextView message = view.findViewById(R.id.text_view_patient);
            message.setText(  getItem(position)  ); // get the string at position
            return view;
        }
    public long getItemId(int position){
        cursor.moveToPosition(position);
        long id = cursor.getLong(cursor.getColumnIndex(PatientDatabaseHelper.KEY_ID));
        return id;
    }}

    public void onActivityResult(int requestCode,int resultCode, Intent data){
        if ((requestCode == 5)&&(resultCode == Activity.RESULT_OK)){
            Log.i("PatientsActivity", "Returned to PatientActivity.onActivityResult");
            Long deleteId = data.getLongExtra("DeleteID", -1);
            deleteListMessage(deleteId);}

            else  if ((requestCode == 5)&&(resultCode == Activity.RESULT_CANCELED)){
            Log.i("PatientsActivity", "Returned to PatientActivity.onActivityResult");
            Long updateId = data.getLongExtra("UpdateID", -1);
            String updateName = data.getStringExtra("UpdateName");
            String updateAddress = data.getStringExtra("UpdatePhone");
            String updatePhone = data.getStringExtra("UpdateBirthday");
            String updateBirthday = data.getStringExtra("UpdateAddress");
            String updateCard = data.getStringExtra("UpdateCard");
            String updateDescription = data.getStringExtra("UpdateDescription");
        //    deleteListMessage(deleteId);
            updateMessage(updateId,updateName,updateAddress,updatePhone,updateBirthday,updateCard,updateDescription);
        }
    }
    public void updateMessage(Long id, String name, String address, String phone, String birthday, String card, String description){

        final SQLiteDatabase db = patientDatabaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PatientDatabaseHelper.KEY_ID,id); //These Fields should be your String values of actual column names
        cv.put(PatientDatabaseHelper.KEY_NAME,name);
        cv.put(PatientDatabaseHelper.KEY_BOD,birthday);
        cv.put(PatientDatabaseHelper.KEY_ADDRESS,address);
        cv.put(PatientDatabaseHelper.KEY_PHONE,phone);
        cv.put(PatientDatabaseHelper.KEY_CARD,card);
        cv.put(PatientDatabaseHelper.KEY_DOC,description);
        db.update("doctorTable",cv,PatientDatabaseHelper.KEY_ID+" = " + id,null);
        listType.clear();
        Log.i("in PatientsActivity","the list after delete which is clear "+listType);
        String selectQuery = "SELECT  * FROM " + "doctorTable";
        cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            listType.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_NAME)));
            Log.i("PatientsActivity", "SQL MESSAGE:" + cursor.getString( cursor.getColumnIndex( PatientDatabaseHelper.KEY_NAME) ) );
            cursor.moveToNext();
        }
        patientAdapter.notifyDataSetChanged();

    }
    public void deleteListMessage(Long id) {
        final SQLiteDatabase db = patientDatabaseHelper.getWritableDatabase();
        db.delete("doctorTable", PatientDatabaseHelper.KEY_ID+" = " + id , null);
        Log.i("in PatientsActivity","the list before delete which is clear "+listType);
        listType.clear();
        Log.i("in PatientsActivity","the list after delete which is clear "+listType);
        String selectQuery = "SELECT  * FROM " + "doctorTable";
        cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            listType.add(cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.KEY_NAME)));
            Log.i("PatientsActivity", "SQL MESSAGE:" + cursor.getString( cursor.getColumnIndex( PatientDatabaseHelper.KEY_NAME) ) );
            cursor.moveToNext();
        }
        patientAdapter.notifyDataSetChanged();
    }
    public void removeFragment() {getFragmentManager().beginTransaction().remove(updateDoctorFrag).commit();}
    @Override
    public void onDestroy(){
        cursor.close();
        super.onDestroy(); }
}
