package com.hrushie.takehomeassignment.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrushie.takehomeassignment.R;
import com.hrushie.takehomeassignment.controllers.adapters.ProductAdapter;
import com.hrushie.takehomeassignment.controllers.http.AsyncDownloader;
import com.hrushie.takehomeassignment.controllers.http.ProductUrl;
import com.hrushie.takehomeassignment.models.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class ProductActivity extends AppCompatActivity {
    private String rawJson;
    String productid;
    TextView tvname;
    WebView wvdescshort, wvdesclong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ImageView ivproduct = (ImageView) findViewById(R.id.product_image);
        tvname = (TextView)findViewById(R.id.tvproductname);
        wvdescshort = (WebView)findViewById(R.id.webviewdescshort);
        wvdesclong = (WebView)findViewById(R.id.webviewdesclong);

        Intent intent = getIntent();
        productid = intent.getExtras().getString("id");
        String imgurl = intent.getExtras().getString("image");
        int pagenumber = intent.getExtras().getInt("pagenumber");

        searchList(pagenumber);


        Picasso.with(this)
                .load(imgurl)
                .into(ivproduct);

    }

    public void searchList(int page) {
        ProductUrl url = ProductUrl.getInstance();

        String getActorHttpMethod = url.getProductQuery(page);

        AsyncDownloader downloader = new AsyncDownloader(this);
        try {
            rawJson = downloader.execute(getActorHttpMethod).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JSONObject results = null;

        try {
            results = new JSONObject(rawJson);
            JSONArray data = results.getJSONArray("products");

            int dataSize = data.length();

            for (int i = 0; i < dataSize; i++) {
                JSONObject jsonActor = data.getJSONObject(i);
                String id = jsonActor.getString("productId");
                if (id.equals(productid) ){
                    tvname.setText(jsonActor.getString("productName"));
                    wvdescshort.loadData(jsonActor.getString("shortDescription"), "text/html", "UTF-8");
                    wvdesclong.loadData(jsonActor.getString("longDescription"), "text/html", "UTF-8");
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}