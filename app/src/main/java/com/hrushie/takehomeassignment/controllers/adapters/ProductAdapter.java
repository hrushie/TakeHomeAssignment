package com.hrushie.takehomeassignment.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hrushie.takehomeassignment.R;
import com.hrushie.takehomeassignment.controllers.activities.ProductActivity;
import com.hrushie.takehomeassignment.models.Product;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hrushie on 7/15/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private static List<Product> products;
    private Context context;

    public ProductAdapter(Context ctx, List<Product> productList) {
        context = ctx;
        products = productList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView productName;
        public TextView productPrice;
        public TextView productStatus;
        public TextView productReviewCount;
        public ImageView productImage;
        public RatingBar productReview;


        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tv_name);
            productPrice = itemView.findViewById(R.id.tv_price);
            productImage = itemView.findViewById(R.id.iv_productpic);
            productReview = itemView.findViewById(R.id.MyRating);
            productReviewCount = itemView.findViewById(R.id.tv_reviewCount);
            productStatus = itemView.findViewById(R.id.tv_inStock);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            Intent myIntent = new Intent(context, ProductActivity.class);
            context.startActivity(myIntent);


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.products_list_item,
                        parent,
                        false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Product product = products.get(position);

        holder.productName.setText(product.getProductiName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        holder.productReviewCount.setText("(" + String.valueOf(product.getReviewCount()) + ")");
        holder.productReview.setRating(product.getReviewRating());
        Picasso.with(context)
                .load(product.getProductImage())
                .into(holder.productImage);


    }

    @Override
    public int getItemCount() {
        if (products == null) {
            return 0;
        }
        return products.size();

    }


}