package com.example.kuntseva.radio2.com.example.kuntseva.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.kuntseva.radio2.com.example.kuntseva.model.RadioData;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public abstract class LoadDataTask extends AsyncTask<Void, Void, List<RadioData>> {


    private static String URL = "http://radio.meganavigator.com/stations";

    /**
     * Contains exception if it was on data loading.
     */
    private Exception exception;

    @Override
    protected List<RadioData> doInBackground(Void... params) {
        List<RadioData> data = new ArrayList<RadioData>();
        //make http call
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse response = httpClient.execute(new HttpGet(URL));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append(" ");
                }
                Log.d("Response", builder.toString());
                JSONArray resp = new JSONArray(builder.toString());
                data = RadioData.from(resp);
            } else {
                exception = new HttpResponseException(statusLine.getStatusCode(), "data can't be delivered!");
            }
        } catch (IOException e) {
            exception = e;
        } catch (JSONException e) {
            exception = e;
        }

        return data;
    }


    @Override
    protected void onPostExecute(List<RadioData> result) {
        super.onPostExecute(result);
        if (exception != null) {
            onException(exception);
        } else {
            onSuccess(result);
        }


    }

    public abstract void onSuccess(List<RadioData> result);

    public abstract void onException(Exception e);

}
