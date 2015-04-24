package com.example.kuntseva.radio2.com.example.kuntseva.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RadioData implements Serializable {

    private static final String TITLE = "title";

    private static final String STREAM_URL = "stream_url";
    private static final String DESCRIPTION = "description";
    private static final String COVER_URL = "cover_url";

    private String _title;
    private String _url;
    private String _description;
    private String _cover_url;


    public RadioData(String title, String url, String description, String cover_url) {
        _title = title;
        _url = url;
        _description = description;
        _cover_url = cover_url;
    }


    public static List<RadioData> from(JSONArray data) throws JSONException {


        List<RadioData> radios = new ArrayList<RadioData>();
        JSONObject e = null;
        for (int i = 0; i < data.length(); i++) {

            e = data.getJSONObject(i);

            radios.add(new RadioData(e.getString(TITLE), e.getString(STREAM_URL), e.getString(DESCRIPTION), e.getString(COVER_URL)));
        }
        return radios;
    }

    public String getTitle() {
        return _title;
    }

    public String getURL() {
        return _url;
    }

    public String get_description() {
        return _description;
    }

    public String get_cover_url() {
        return _cover_url;
    }
}
