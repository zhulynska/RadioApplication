package com.example.kuntseva.radio2.com.example.kuntseva.view_plus_controller;

import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.kuntseva.radio2.com.example.kuntseva.task.LoadDataTask;
import com.example.kuntseva.radio2.R;
import com.example.kuntseva.radio2.com.example.kuntseva.model.RadioData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    MyListAdapter sAdapter = null;
    private Map<String, Object> m = null;
    ArrayList<Map<String, Object>> myCollection;
    RadioData radioItem;

    static ArrayList<RadioData> data = new ArrayList<RadioData>();
    LoadDataTask mLoadDataTask;
    final String LOG_TAG = this.getClass().getSimpleName();
    static String PAUSE = "Pause";
    static String PLAY = "Play";

    @InjectView(R.id.listView1)
    ListView list1;
    @InjectView(R.id.tableLayout1)
    TableLayout tableLayout1;

    @InjectView(R.id.tableRow1)
    TableRow tableRow1;
    AudioManager am;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(LOG_TAG, "onCreate MainActivity!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setTitle("Internet Radio");
        loadRadioData();
    }


    private void loadRadioData() {
        if (mLoadDataTask != null) {
            mLoadDataTask.cancel(true);
        }
        mLoadDataTask = new LoadDataTask() {
            @Override
            public void onSuccess(List<RadioData> result) {
                displayResult(result);
            }

            @Override
            public void onException(Exception e) {
                showErrorNotification(e);
            }
        };
        mLoadDataTask.execute();
    }


    private void showErrorNotification(Exception e) {
        Toast.makeText(this, "data loading exception: " + e, Toast.LENGTH_LONG).show();
        Log.e(LOG_TAG, "data loading exception: " + e);
        e.printStackTrace();
    }

    private void displayResult(List<RadioData> result) {
        data = (ArrayList) result;
        sAdapter = new MyListAdapter(this, load(result), R.layout.layout1, new String[]{"img", "title"}, new int[]{R.id.img1, R.id.textV1});
        list1.setAdapter(sAdapter);
        list1.setOnItemClickListener(this);



    }


    ArrayList<Map<String, Object>> load(List<RadioData> result) {
        myCollection = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < result.size(); i++) {
            m = new HashMap<String, Object>();
            ImageView img = new ImageView(this);
            Picasso.with(this).load(result.get(i).get_cover_url()).into(img);
            m.put("img", img);
            m.put("title", result.get(i).getTitle());
            myCollection.add(m);

        }
        return myCollection;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
       if (id == R.id.action_settings) {
          Intent  myIntent = new Intent(MainActivity.this, AboutAuthorActivity.class);
            startActivity(myIntent);
      return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<RadioData> result = data;

        Intent intent = new Intent(MainActivity.this, ItemRadioFrame.class);
        intent.putExtra("radioData", result.get(position));
        setResult(RESULT_OK, intent);
        finish();

        startActivity(intent);
    }
}


    /* void displayResult1(List<RadioData> result) {
         Log.i(LOG_TAG, "second list creating!");

         for(int i = 0; i< result.size(); i++) {
             TableRow r = new TableRow(this);
             tableLayout1.addView(r);
            // tableLayout1.addView(r, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            ImageView img = new ImageView(this);
            Picasso.with(this).load(result.get(i).get_cover_url()).into(img);

             img.setLayoutParams(new TableRow.LayoutParams(
                     TableRow.LayoutParams.MATCH_PARENT,
                     TableRow.LayoutParams.MATCH_PARENT,1.0f));

             r.addView(img);



             TextView text = new TextView(this);
             text.setText(result.get(i).getTitle());


             text.setLayoutParams(new TableRow.LayoutParams(
                     TableRow.LayoutParams.MATCH_PARENT,
                     TableRow.LayoutParams.MATCH_PARENT, 1.0f));
             r.addView(text);

             radioItem = result.get(i);
             r.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {



                     Intent intent = new Intent(MainActivity.this, ItemRadioFrame.class);
                     intent.putExtra("radioData", radioItem);
                     setResult(RESULT_OK, intent);
                     finish();
                     startActivity(intent);
                 }
             });
          }
     }
 */
