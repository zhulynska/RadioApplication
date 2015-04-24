package com.example.kuntseva.radio2.com.example.kuntseva.view_plus_controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kuntseva.radio2.R;
import com.example.kuntseva.radio2.com.example.kuntseva.model.RadioData;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class DescriptionFrame extends ActionBarActivity {


    @InjectView(R.id.description)
    TextView description;
    ActionBarActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("", "onCreate DescriptionFrame!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_frame);
        ButterKnife.inject(this);
        Intent intent = this.getIntent();
        RadioData radioItem = (RadioData)intent.getSerializableExtra("radioData");
         description.setText(radioItem.get_description());
        setTitle(radioItem.getTitle());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_description_frame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
