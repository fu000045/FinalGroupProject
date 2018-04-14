package com.algonquin.finalgroupproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.algonquin.finalgroupproject.octranspo.OCBusNumberWindow;

public class MainActivity extends AppCompatActivity {
    private Button btn_quizCreater;
    //private Button btn_movieInfo;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.OCTrans:

                Toast.makeText(MainActivity.this, getString(R.string.oc_toast), Toast.LENGTH_SHORT).show();
                //Jump to MovieActivity.
                Intent intent_oc = new Intent(MainActivity.this, OCBusNumberWindow.class);
                startActivity(intent_oc);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }

}
