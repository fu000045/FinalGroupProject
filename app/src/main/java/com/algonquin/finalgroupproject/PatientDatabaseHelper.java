package com.algonquin.finalgroupproject;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by wenchongxu on 2018-04-05.
 */

public class PatientDatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "patient.db";
    private static int VERSION_NUM = 1;
    public final static String DOCTOR_TABLE_NAME = "doctorTable";
    public final static String DENTIST_TABLE_NAME = "dentistTable";
    public final static String OPTOMETRIST_TABLE_NAME = "optometristTable";
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
            + DOCTOR_TABLE_NAME +" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_NAME+" TEXT, "+KEY_ADDRESS+" TEXT, "+KEY_BOD+" TEXT, "+KEY_PHONE+" TEXT, "+KEY_CARD+" TEXT, "+KEY_DEN+" TEXT, "+KEY_OPT+" TEXT, "+KEY_DOC+" TEXT);";
    private static final String createDentistTable = "CREATE TABLE "
            + DENTIST_TABLE_NAME +" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_NAME+" TEXT, "+KEY_ADDRESS+" TEXT, "+KEY_BOD+" TEXT, "+KEY_PHONE+" TEXT, "+KEY_CARD+" TEXT, "+KEY_DEN+" TEXT);";
    private static final String createOptometristTable = "CREATE TABLE "
            + OPTOMETRIST_TABLE_NAME +" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_NAME+" TEXT, "+KEY_ADDRESS+" TEXT, "+KEY_BOD+" TEXT, "+KEY_PHONE+" TEXT, "+KEY_CARD+" TEXT, "+KEY_OPT+" TEXT);";

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(createDoctorOfficeTable);
        db.execSQL(createDentistTable);
        db.execSQL(createOptometristTable);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS " + "doctorTable");
        db.execSQL("DROP TABLE IF EXISTS " + "dentistTable");
        db.execSQL("DROP TABLE IF EXISTS " + "optometristTable");
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
}
