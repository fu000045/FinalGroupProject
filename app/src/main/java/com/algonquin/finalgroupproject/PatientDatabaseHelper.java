package com.algonquin.finalgroupproject;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by wenchongxu on 2018-04-05.
 * PatientDatabaseHelper is the class for database for all of the information in my patientclass
 */

public class PatientDatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "patient.db";
    private static int VERSION_NUM = 3;
    public final static String AGE_TABLE_NAME = "ageTable";
    public final static String DOCTOR_TABLE_NAME = "doctorTable";
    public final static String KEY_ID = "id";
    public final static String KEY_NAME = "name";
    public final static String KEY_ADDRESS = "address";
    public final static String KEY_BOD = "birthday";
    public final static String KEY_PHONE = "phoneNumber";
    public final static String KEY_CARD = "healthCardNumber";
    public final static String KEY_DOC = "doctor";
    public final static String KEY_DEN = "dentist";
    public final static String KEY_OPT = "optometrist";

    private static final String createDoctorOfficeTable = "CREATE TABLE "
            + DOCTOR_TABLE_NAME +" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_NAME+" TEXT, "+KEY_ADDRESS+" TEXT, "+KEY_BOD+" DATE, "+KEY_PHONE+" TEXT, "+KEY_CARD+" TEXT, "+KEY_DEN+" TEXT, "+KEY_OPT+" TEXT, "+KEY_DOC+" TEXT);";

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(createDoctorOfficeTable);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS " + "doctorTable");

        this.onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
    }


    @Override
    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion){
    }

    @Override
    public void onOpen (SQLiteDatabase db){
    }
    public PatientDatabaseHelper(Context ctx) {super(ctx, DATABASE_NAME, null, VERSION_NUM);}


    public int minAge(SQLiteDatabase db){
        Cursor c=db.rawQuery("SELECT MIN(birthday) as `MIN` FROM doctorTable; ",null);
        c.moveToFirst();
        int i=2018-Integer.parseInt(c.getString(c.getColumnIndex("MIN")).substring(0, 4));
        return i;
    }
    public int maxAge(SQLiteDatabase db){
        Cursor c=db.rawQuery("SELECT MAX(birthday) as `MAX` FROM doctorTable; ",null);
        c.moveToFirst();
        int i=2018-Integer.parseInt(c.getString(c.getColumnIndex("MAX")).substring(0, 4));
        return i;
    }

    public int avgAge(SQLiteDatabase db){
        Cursor c=db.rawQuery("SELECT AVG(birthday)as 'AVG' FROM doctorTable; ",null);
        c.moveToFirst();
        int i=2018-Integer.parseInt(c.getString(c.getColumnIndex("AVG")));
        return i;
    }
}
