package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

public class PatientViewStatisticActivity extends Activity {
    int endYear;

    int endMonth;
    int endDay;
    private int startYear;
    private int startMonth;
    private int startDay;
    private int resYear;
    private int resMonth;
    private int resDay;
    private  int setStartDate;
    PatientAgeDatabaseHelper patientAgeDatabaseHelper;

    TextView max;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_statistic);


        Intent intent = new Intent();
        intent.getExtras();

        patientAgeDatabaseHelper=new PatientAgeDatabaseHelper(this);
        max=findViewById(R.id.patient_view_max);


        max.setText(getMaxColumnData());
    }
    public int getMaxColumnData() {
        final SQLiteDatabase db = patientAgeDatabaseHelper.getWritableDatabase();
        final SQLiteStatement stmt = db
                .compileStatement("SELECT MAX(age) FROM ageTable");
        return (int) stmt.simpleQueryForLong();
    }
    PatientViewStatisticActivity(){

    }

    PatientViewStatisticActivity(int startDate){
        setStartDate(startDate);
    }
    public String getAge(){
        final SQLiteDatabase db = patientAgeDatabaseHelper.getWritableDatabase();
        ContentValues cValues= new ContentValues();
        cValues.put(PatientAgeDatabaseHelper.KEY_AGE,(getCurrentDate()-setStartDate));
        db.insert(PatientAgeDatabaseHelper.KEY_AGE, null, cValues);
        Log.i("++++++++++++++++++++++",""+(getCurrentDate()-setStartDate));
        return "Age is : "+(getCurrentDate()-setStartDate);
    }
    public void setStartDate(int startDate) {
        this.setStartDate = startDate;
    }
////
    public int getCurrentDate() {
        Calendar c=Calendar.getInstance();
         endYear = c.get(Calendar.YEAR);
         endMonth = c.get(Calendar.MONTH);
        endMonth++;
         endDay = c.get(Calendar.DAY_OF_MONTH);
        return endYear+endMonth+endDay;
    }















    public void setDateOfBirth(int sYear, int sMonth, int sDay)
    {
        startYear=sYear;
        startMonth=sMonth;
        startMonth++;
        startDay=sDay;

    }
    public void calcualteYear()
    {
        resYear=endYear-startYear;

    }

    public void calcualteMonth()
    {
        if(endMonth>=startMonth)
        {
            resMonth= endMonth-startMonth;
        }
        else
        {
            resMonth=endMonth-startMonth;
            resMonth=12+resMonth;
            resYear--;
        }

    }
    public void calcualteDay()
    {

        if(endDay>=startDay)
        {
            resDay= endDay-startDay;
        }
        else
        {
            resDay=endDay-startDay;
            resDay=30+resDay;
//resMonth--;
        }
    }

    public String getResult()
    {
        return resDay+":"+resMonth+":"+resYear;
    }


//    private String getAge(int year, int month, int day){
//        Calendar dob = Calendar.getInstance();
//        Calendar today = Calendar.getInstance();
//
//        dob.set(year, month, day);
//
//        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
//
//        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
//            age--;
//        }
//
//        Integer ageInt = new Integer(age);
//        String ageS = ageInt.toString();
//
//        return ageS;
//    }
}
