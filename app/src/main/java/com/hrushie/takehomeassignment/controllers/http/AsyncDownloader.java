package com.hrushie.takehomeassignment.controllers.http;

import android.content.Context;
import android.os.AsyncTask;

import com.hrushie.takehomeassignment.controllers.activities.MainActivity;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by hrushie on 7/15/2017.
 * <p>
 * Public class to be re used for downloading JSON response using
 * OkHttp.
 * We extend AsyncTask because so we can make an Asynchronous call to retrieve the
 * response from theMovieDB.org API without affecting the GUI.
 */

public class AsyncDownloader extends AsyncTask<String, Integer, String> {

    private Context context;
    private Class classToLoad;
    private MainActivity mainActivity;

    public AsyncDownloader(Context ctx) {
        context = ctx;
    }

    private void showProgressDialog(String message) {
    }

    /**
     * onPreExecute runs on the UI thread before doInBackground.
     */

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog("Loading ....");
    }

    /**
     * doInBackground() runs in the background on a worker thread. This is where code that can block the GUI should go.
     */

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        Response response = null;
        String jsonData = null;

        try {
            response = call.execute();

            if (response.isSuccessful()) {
                jsonData = response.body().string();
            } else {
                jsonData = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    /**
     * onPostExecute can run on the  main (GUI) thread and receives
     * the result of doInBackground.
     */

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);
    }
}
