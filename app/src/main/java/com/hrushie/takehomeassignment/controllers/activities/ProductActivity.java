package com.hrushie.takehomeassignment.controllers.activities;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.hrushie.takehomeassignment.R;
import com.hrushie.takehomeassignment.controllers.adapters.ProductPagerAdapter;
import com.hrushie.takehomeassignment.models.DataObject;
import com.hrushie.takehomeassignment.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

    }
}