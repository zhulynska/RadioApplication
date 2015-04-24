package com.example.kuntseva.radio2.com.example.kuntseva.view_plus_controller;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuntseva.radio2.R;
import com.example.kuntseva.radio2.com.example.kuntseva.model.RadioData;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ItemRadioFrame extends ActionBarActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener {
    RadioData radioData;
    static String PAUSE = "Pause";
    static String PLAY = "Play";
    static String RESUME = "Resume";
    int length ;
    Intent myIntent;

    final String LOG_TAG = this.getClass().getSimpleName();
    @InjectView(R.id.buttonItem)
    Button buttonItem;
    @InjectView(R.id.textViewItem)
    TextView textViewItem;
    @InjectView(R.id.imageViewItem)
    ImageView imageViewItem;

    MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Log.i(LOG_TAG, "onCreate ItemRadioFrame!");

            setContentView(R.layout.activity_item_radio_frame);
            ButterKnife.inject(this);

            Intent intent = this.getIntent();

            radioData = (RadioData) intent.getSerializableExtra("radioData");

            if (radioData == null) throw new Exception("Can't receive obj from intent ");
            buttonItem.setText(PLAY);
            buttonItem.setOnClickListener(this);

            textViewItem.setText(radioData.getTitle());
            Picasso.with(this).load(radioData.get_cover_url()).into(imageViewItem);
            imageViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickImage(v);
                }
            });
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setDataSource(radioData.getURL());

            setTitle(radioData.getTitle());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Can't create mediaPlayer", e);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "Start ItemRadioFrame!!!");
    }

    public void onClickImage(View v) {
        myIntent = new Intent(ItemRadioFrame.this, DescriptionFrame.class);
        myIntent.putExtra("radioData", radioData);
        setResult(RESULT_OK, myIntent);
        startActivity(myIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_radio_frame, menu);
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
    public void onClick(View v) {
          try {
            boolean b = true;


            if (buttonItem.getText().equals(PLAY) && b == true) {

                mediaPlayer.prepareAsync();

                buttonItem.setText(PAUSE);
                b = false;
            } else if (buttonItem.getText().equals(PAUSE)) {

                mediaPlayer.pause();

                length = mediaPlayer.getCurrentPosition();
                buttonItem.setText(RESUME);
                Log.v(LOG_TAG, "seek to" + length);
                b = false;
            } else if (buttonItem.getText().equals(RESUME) && b == true) {
                mediaPlayer.seekTo(length);
                mediaPlayer.start();
                buttonItem.setText(PAUSE);
                b = false;
            }
        } catch (Exception e) {
            Log.v(LOG_TAG, "On click exception", e);
        }
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mp != null && !mp.isPlaying())
            mp.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myIntent = new Intent(ItemRadioFrame.this, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(myIntent);
    }


}
