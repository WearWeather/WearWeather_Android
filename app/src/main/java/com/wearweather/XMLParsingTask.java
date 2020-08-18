package com.wearweather;

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
    private String receiveMsg = "";
    private String requestUrl;
    public Context mContext;

    public XMLParsingTask(Context context, String requestUrl) {

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
            boolean b_dataTime = false;
            boolean b_pm10Value = false;
            boolean b_pm25Value = false;
            boolean b_o3Value=false;

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


                    // 시작태그를 만나는순간 (JSON으로 치면 key을 대입해서 찾음)
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")) {

                        }
                        if (parser.getName().equals("dataTime"))
                            b_dataTime = true;
                        if (parser.getName().equals("pm10Value"))
                            b_pm10Value = true;
                        if (parser.getName().equals("pm25Value"))
                            b_pm25Value = true;
                        if (parser.getName().equals("o3Value"))
                            b_o3Value = true;
                        // TODO : 만약 더 많은 value을 원한다면, 이어서 기술

                        break;



                    // 시작태그와 종료태그 사이 값을 만나는순간 (JSON으로 치면 key를 넣어서 값을 얻음)
                    case XmlPullParser.TEXT:
                        if (b_dataTime) {
                            receiveMsg = receiveMsg + parser.getText().trim() + "_";
                            b_dataTime = false;
                        } else if (b_pm10Value) {
                            receiveMsg = receiveMsg + parser.getText().trim() + "_";
                            b_pm10Value = false;
                        } else if (b_pm25Value) {
                            receiveMsg = receiveMsg + parser.getText().trim() + "_";
                            b_pm25Value = false;
                        }
                        else if (b_o3Value) {
                            receiveMsg = receiveMsg + parser.getText().trim() + "_";
                            b_o3Value = false;
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

        //어답터 연결
        // MyAdapter adapter = new MyAdapter(getApplicationContext(), list);
        // recyclerView.setAdapter(adapter);
    }
}