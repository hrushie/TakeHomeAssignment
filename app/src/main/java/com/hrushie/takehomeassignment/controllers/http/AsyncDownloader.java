package com.hrushie.takehomeassignment.controllers.http;

import android.app.ProgressDialog;
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
 */

public class AsyncDownloader extends AsyncTask<String, Integer, String> {

    private ProgressDialog progressDialog;
    private Context context;
    private Class classToLoad;
    private ProgressDialog dialog;
    private MainActivity mainActivity;

    public AsyncDownloader(Context ctx) {
        context = ctx;
    }
    private void showProgressDialog(String message)
    {
        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage(message); // message

        progressDialog.setCancelable(false);

        progressDialog.show();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog("Loading ....");
    }

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

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);
        progressDialog.dismiss();

    }
}
