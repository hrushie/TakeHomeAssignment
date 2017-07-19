package com.hrushie.takehomeassignment.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.hrushie.takehomeassignment.R;
import com.hrushie.takehomeassignment.controllers.adapters.ProductAdapter;
import com.hrushie.takehomeassignment.controllers.http.AsyncDownloader;
import com.hrushie.takehomeassignment.controllers.http.ProductUrl;
import com.hrushie.takehomeassignment.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    private ArrayList<Product> products = new ArrayList<>();
    private RecyclerView recyclerView;
    private int pageNumber = 0;
    private String rawJson;
    private ProductAdapter adapter;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.productRecyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        updateList(pageNumber);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached


                    loadMore(pageNumber);
                    loading = true;
                }
            }
        });
    }

    public void updateList(int page) {
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

                Product tempProduct = new Product();
                tempProduct.setProductiId(jsonActor.getString("productId"));
                tempProduct.setProductiName(jsonActor.getString("productName"));
                tempProduct.setPrice(jsonActor.getString("price"));
                tempProduct.setReviewRating(jsonActor.getInt("reviewRating"));
                tempProduct.setReviewCount(jsonActor.getInt("reviewCount"));
                tempProduct.setInStock(jsonActor.getBoolean("inStock"));
                tempProduct.setProductImage(jsonActor.getString("productImage"));
                tempProduct.setPagenumber(page);


                products.add(tempProduct);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new ProductAdapter(MainActivity.this, products);
        recyclerView.setAdapter(adapter);
        pageNumber = pageNumber + 30;

    }

    private void loadMore(int page) {

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

            if (dataSize == 0) {
                showNotFoundNotification();

                super.onBackPressed();
            }


            for (int i = 0; i < dataSize; i++) {
                JSONObject jsonActor = data.getJSONObject(i);

                Product tempProduct = new Product();
                tempProduct.setProductiId(jsonActor.getString("productId"));
                tempProduct.setProductiName(jsonActor.getString("productName"));
                tempProduct.setPrice(jsonActor.getString("price"));
                tempProduct.setReviewRating(jsonActor.getInt("reviewRating"));
                tempProduct.setReviewCount(jsonActor.getInt("reviewCount"));
                tempProduct.setInStock(jsonActor.getBoolean("inStock"));
                tempProduct.setProductImage(jsonActor.getString("productImage"));
                tempProduct.setPagenumber(page);


                products.add(tempProduct);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
        pageNumber = pageNumber + 30;

    }

    private void showNotFoundNotification() {
        Toast.makeText(this,
                "Sorry we couldn't find anything, please try again",
                Toast.LENGTH_SHORT).
                show();
    }

}
