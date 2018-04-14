package com.algonquin.finalgroupproject.octranspo;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.algonquin.finalgroupproject.R;

import java.util.ArrayList;

public class OCBusNumberWindow extends AppCompatActivity {


    Toolbar toolbar;

    Button buttonChat;
    ListView listViewChat;
    EditText editTextChat;
    ArrayList<String> list = new ArrayList<>();

    private SQLiteDatabase db;
    private ChatAdapter messageAdapter;
    OCDatabaseHelper chatDatabase;
    Cursor cursor;

    private FrameLayout extraFrame;
    private static final int REQUEST_CODE = 1;
    protected OCBusFragment messageF;
    Bundle bundleF;
    private boolean isTablet;


    protected static final String ACTIVITY_NAME = "Activity Name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oc_bus_number_window);

        extraFrame = findViewById(R.id.FrameLayoutFL);

        if (extraFrame == null) {
            isTablet = false;
        } else {
            isTablet = true;
        }


        chatDatabase = new OCDatabaseHelper(this);
        db = chatDatabase.getWritableDatabase();


        // retrieving data by Cursor
        cursor = db.query(false, OCDatabaseHelper.TABLE_NAME,
                new String[]{OCDatabaseHelper.KEY_ID, OCDatabaseHelper.KEY_MESSAGE},
                null, null, null, null, null, null);

        cursor.moveToFirst();


        int numRows = cursor.getCount();
        System.out.println(numRows);

        int messageIndex = cursor.getColumnIndex(OCDatabaseHelper.KEY_MESSAGE);
        System.out.println(messageIndex);

        for (int i = 0; i < numRows; i++) {
            list.add(cursor.getString(messageIndex));
            cursor.moveToNext();
        }

        //resets the iteration of results, move back to the first row
        cursor.moveToFirst();


        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(OCDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor’s  column count = " + cursor.getColumnCount());
            Log.i(ACTIVITY_NAME, "Cursor’s  column name = " + cursor.getColumnName(messageIndex));
            cursor.moveToNext();
        }

        listViewChat = findViewById(R.id.listViewChat);
        editTextChat = findViewById(R.id.editChat);

        messageAdapter = new ChatAdapter(this);
        listViewChat.setAdapter(messageAdapter);


        buttonChat = findViewById(R.id.sendButton);
        buttonChat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                ContentValues contentValues = new ContentValues();

                contentValues.put(OCDatabaseHelper.KEY_MESSAGE, editTextChat.getText().toString());

                db.insert(OCDatabaseHelper.TABLE_NAME, "", contentValues);


                list.add(editTextChat.getText().toString());
                messageAdapter.notifyDataSetChanged();
                editTextChat.setText("");
            }
        });


        listViewChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                // create the new OCBusFragment
                messageF = new OCBusFragment();

                bundleF = new Bundle();
                bundleF.putLong(OCDatabaseHelper.KEY_ID, l); // add id to bundle
                bundleF.putString(OCDatabaseHelper.KEY_MESSAGE, cursor.getString(cursor.getColumnIndex(OCDatabaseHelper.KEY_MESSAGE))); // add message to bundle
                bundleF.putBoolean("isTablet", isTablet);

                // if on a tablet
                if (isTablet) {

                    messageF.setArguments(bundleF);


                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.FrameLayoutFL, messageF);
                    getFragmentManager().popBackStack();
                    ft.addToBackStack(null);
                    ft.commit();
                } else {
                    Intent startMessageDetails = new Intent(getApplicationContext(), OCDetails.class);
                    startMessageDetails.putExtras(bundleF);
                    startActivityForResult(startMessageDetails, REQUEST_CODE);
                }
            }
        });


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final Button button = findViewById(R.id.button);
        final Context context = this;


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                AlertDialog.Builder actionBuilder = new AlertDialog.Builder(context);
                actionBuilder.setTitle(getString(R.string.oc_about_title));
                actionBuilder.setMessage(getString(R.string.oc_about_context1) + "\n" + getString(R.string.oc_about_context2) + "\n\n" + getString(R.string.oc_about_context3) + "\n" + getString(R.string.oc_about_context4) + "\n" + getString(R.string.oc_about_context5) + "\n" + getString(R.string.oc_about_context6) + "\n" + getString(R.string.oc_about_context7) + "\n" + getString(R.string.oc_about_context8));
                actionBuilder.setPositiveButton(getString(R.string.oc_about_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                AlertDialog dialog = actionBuilder.create();
                dialog.show();

                Log.d("Toolbar", "OC Information selected");

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == OCBusFragment.RESULT_CODE) {
            deleteMessage(data.getExtras().getLong(OCDatabaseHelper.KEY_ID));
        }
    }


    public void deleteMessage(long id) {
        db.delete(OCDatabaseHelper.TABLE_NAME, OCDatabaseHelper.KEY_ID + " = " + (int) id, null);
        cursor = db.query(false, OCDatabaseHelper.TABLE_NAME, new String[]{OCDatabaseHelper.KEY_ID, OCDatabaseHelper.KEY_MESSAGE}, null, null, null, null, null, null);
        updateMessagesList(cursor);
        messageAdapter.notifyDataSetChanged();
    }

    public void updateMessagesList(Cursor c) {
        list.clear();
        Log.i(ACTIVITY_NAME, "Cursor's column count: " + c.getColumnCount());
        for (int i = 0; i < c.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, String.format("Column %d: %s ", i, c.getColumnName(i)));
        }

        c.moveToFirst();
        while (!c.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL Message: " + c.getString(c.getColumnIndex(OCDatabaseHelper.KEY_MESSAGE))); // show message in log
            list.add(c.getString(c.getColumnIndex(OCDatabaseHelper.KEY_MESSAGE))); // add message to messages ArrayList
            c.moveToNext();
        }
    }


    public void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy");
        super.onDestroy();
        // close cursor
        if (cursor != null) {
            cursor.close();
            Log.i(ACTIVITY_NAME, "cursor closed");
        }
        // close database
        if (chatDatabase != null) {
            chatDatabase.close();
            Log.i(ACTIVITY_NAME, "database closed");
        }
    }

    private class ChatAdapter extends ArrayAdapter<String> {


        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }


        @Override
        public long getItemId(int position) {
            cursor = db.query(false, OCDatabaseHelper.TABLE_NAME, new String[]{OCDatabaseHelper.KEY_ID, OCDatabaseHelper.KEY_MESSAGE}, null, null, null, null, null, null);
            cursor.moveToPosition(position);
            return cursor.getInt(cursor.getColumnIndex(OCDatabaseHelper.KEY_ID));
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = OCBusNumberWindow.this.getLayoutInflater();


            View result = null;
            result = inflater.inflate(R.layout.oc_bus_incoming, null);

            // messageText
            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            message.setTextSize(25);
            return result;
        }

        public int getId(int position) {
            return position;
        }

    }

    public boolean frameCheck = false;


}
