package com.hrushie.takehomeassignment.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hrushie.takehomeassignment.R;
import com.hrushie.takehomeassignment.controllers.http.AsyncDownloader;
import com.hrushie.takehomeassignment.controllers.http.ProductUrl;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * detail activity which comes from RecyclerView to
 * display as the details about the products from the lists.
 */
public class ProductActivity extends AppCompatActivity {
    private String rawJson;
    private String productid;
    private TextView tvname, tvprice, tvcountreview, instock;
    private RatingBar rbreviews;
    private WebView wvdescshort, wvdesclong;
    private ImageView ivproduct;
    private int pageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ivproduct = (ImageView) findViewById(R.id.product_image);
        tvname = (TextView) findViewById(R.id.tvproductname);
        wvdescshort = (WebView) findViewById(R.id.webviewdescshort);
        wvdesclong = (WebView) findViewById(R.id.webviewdesclong);
        tvprice = (TextView) findViewById(R.id.tvprice);
        tvcountreview = (TextView) findViewById(R.id.tvcountreview);
        instock = (TextView) findViewById(R.id.instock);
        rbreviews = (RatingBar) findViewById(R.id.rbreviews);
        int displayWidth = getWindowManager().getDefaultDisplay().getHeight();
        ivproduct.getLayoutParams().height = displayWidth / 2;
        Intent intent = getIntent();
        productid = intent.getExtras().getString("id");
        pageNumber = intent.getExtras().getInt("pagenumber");
        searchList(pageNumber);
    }

    /**
     * Method for Getting the details about the product from JSON
     */
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
                if (id.equals(productid)) {
                    tvname.setText(jsonActor.getString("productName"));
                    tvprice.setText(jsonActor.getString("price"));
                    tvcountreview.setText("(" + String.valueOf(jsonActor.getString("reviewCount")) + ")");
                    rbreviews.setRating(jsonActor.getInt("reviewRating"));
                    wvdescshort.loadData(jsonActor.getString("shortDescription"), "text/html", "UTF-8");
                    wvdesclong.loadData(jsonActor.getString("longDescription"), "text/html", "UTF-8");
                    if (jsonActor.getBoolean("inStock") == true) {
                        instock.setText("In Stock");
                        instock.setTextColor(getResources().getColor(R.color.colorAvailable));
                    } else {
                        instock.setText("Out of Stock");
                        instock.setTextColor(getResources().getColor(R.color.colorNotAvailable));
                    }
                    Picasso.with(this)
                            .load(jsonActor.getString("productImage"))
                            .resize(600, 200) // resizes the image to these dimensions (in pixel)
                            .centerInside()
                            .into(ivproduct);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}