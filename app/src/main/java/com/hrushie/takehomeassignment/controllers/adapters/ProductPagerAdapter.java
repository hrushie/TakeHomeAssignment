package com.hrushie.takehomeassignment.controllers.adapters;

/**
 * Created by hrushie on 7/17/2017.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrushie.takehomeassignment.R;
import com.hrushie.takehomeassignment.models.DataObject;
import com.hrushie.takehomeassignment.models.Product;

import java.util.ArrayList;
import java.util.List;
public class ProductPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Product> dataObjectList;
    private LayoutInflater layoutInflater;
    public ProductPagerAdapter(Context context, ArrayList<Product> dataObjectList){
        this.context = context;
        this.layoutInflater = (LayoutInflater)this.context.getSystemService(this.context.LAYOUT_INFLATER_SERVICE);
        this.dataObjectList = dataObjectList;
    }
    @Override
    public int getCount() {
        return dataObjectList.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = this.layoutInflater.inflate(R.layout.pager_list_item, container, false);
        ImageView displayImage = (ImageView)view.findViewById(R.id.large_image);
        TextView imageText = (TextView)view.findViewById(R.id.image_name);
       // displayImage.setImageResource(this.dataObjectList.get(position).getImageId());
        imageText.setText(this.dataObjectList.get(position).getProductiName());
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}