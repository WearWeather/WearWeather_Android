package com.wearweatherapp.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class XMLParsingTask extends AsyncTask<String, Void, String> {

    private static final String TAG = "XMLParsingTask";
    private String receiveMsg;
    private String requestUrl;
    public Context mContext;

    public XMLParsingTask(Context context, String requestUrl) {
        receiveMsg = "";
        this.requestUrl = requestUrl;
        this.mContext = context;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            boolean b_pm10Value = false; //미세먼지
            boolean b_pm25Value = false; //초미세먼지
            boolean b_o3Value = false; //오존
            boolean b_coValue = false; //일산화탄소

            URL url = new URL(requestUrl);
            InputStream is = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")) {

                        }
                        if (parser.getName().equals("pm10Value"))
                            b_pm10Value = true;
                        if (parser.getName().equals("pm25Value"))
                            b_pm25Value = true;
                        if (parser.getName().equals("o3Value"))
                            b_o3Value = true;
                        if (parser.getName().equals("coValue"))
                            b_coValue = true;
                        break;
                    // 시작태그와 종료태그 사이 값을 만나는순간 (JSON으로 치면 key를 넣어서 값을 얻음)
                    case XmlPullParser.TEXT:
                        if (b_pm10Value) {
                            receiveMsg = receiveMsg + parser.getText().trim() + "_";
                            b_pm10Value = false;
                        } else if (b_pm25Value) {
                            receiveMsg = receiveMsg + parser.getText().trim() + "_";
                            b_pm25Value = false;
                        } else if (b_o3Value) {
                            receiveMsg = receiveMsg + parser.getText().trim() + "_";
                            b_o3Value = false;
                        } else if (b_coValue) {
                            receiveMsg = receiveMsg + parser.getText().trim() + "_";
                            b_coValue = false;
                        }
                        break;
                    // 종료태그를 만나는순간 (JSON으로 치면, value 찾음)
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, receiveMsg);
        return receiveMsg;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}