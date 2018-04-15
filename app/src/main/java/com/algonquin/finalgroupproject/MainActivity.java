package com.algonquin.finalgroupproject;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.algonquin.finalgroupproject.octranspo.OCBusNumberWindow;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearlayout;
    //private Button btn_movieInfo;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearlayout = findViewById(R.id.linearlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //       ActionBar ab = getSupportActionBar();
        //      ab.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.Quiz:
                Snackbar snackbar = Snackbar
                        .make(linearlayout, "Quiz Creator Selected!", Snackbar.LENGTH_LONG);
                snackbar.show();

                //Jump to QuizCreaterActivity.
                Intent intent = new Intent(MainActivity.this, QuizCreatorActivity.class);
                startActivity(intent);
                break;

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Movie:

                Toast.makeText(MainActivity.this, "Movie List Selected!", Toast.LENGTH_SHORT).show();
                //Jump to MovieActivity.
                Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                startActivity(intent);
                break;

            case R.id.OCTrans:

                Toast.makeText(MainActivity.this, getString(R.string.oc_toast), Toast.LENGTH_SHORT).show();
                //Jump to MovieActivity.
                Intent intent_oc = new Intent(MainActivity.this, OCBusNumberWindow.class);
                startActivity(intent_oc);
                break;


            case R.id.Patient:
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(MainActivity.this, "Patient form Selected!", Toast.LENGTH_SHORT).show();
                        //Jump to PatientFormActivity.
                        Intent intent = new Intent(MainActivity.this, PatientIntakeFormActivity.class);
                        startActivity(intent);
                        return true;
                    }
                });
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }

}
