package com.hrushie.takehomeassignment.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.hrushie.takehomeassignment.R;
import com.hrushie.takehomeassignment.controllers.activities.ProductActivity;
import com.hrushie.takehomeassignment.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrushie on 7/15/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {

    private static ArrayList<Product> products;
    private ArrayList<Product> mFilteredList;

    private Context context;

    public ProductAdapter(Context ctx, ArrayList<Product> productList) {
        context = ctx;
        products = productList;
        mFilteredList = productList;
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
            Product product = products.get(position);

            Intent myIntent = new Intent(context, ProductActivity.class);
            myIntent.putExtra("name", product.getProductiName());
            myIntent.putExtra("id", product.getProductiId());
            myIntent.putExtra("image", product.getProductImage());
            myIntent.putExtra("review", product.getReviewRating());
            myIntent.putExtra("reviewcount", product.getReviewCount());
            myIntent.putExtra("longdesc", product.getLongDescription());
            myIntent.putExtra("pagenumber",product.getPagenumber());

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
        if (product.isInStock() == true){
            holder.productStatus.setText("In Stock");
            holder.productStatus.setTextColor(context.getResources().getColor(R.color.colorAvailable));
        } else {
            holder.productStatus.setText("Out of Stock");
            holder.productStatus.setTextColor(context.getResources().getColor(R.color.colorNotAvailable));
        }
    }
    @Override
    public int getItemCount() {
        if (products == null) {
            return 0;
        }
        return products.size();
    }

@Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = products;
                } else {

                    ArrayList<Product> filteredList = new ArrayList<>();

                    for (Product androidVersion : products) {

                        String name = androidVersion.getProductiName();
                        String desc = androidVersion.getShortDescription();
                        if (name.toLowerCase().contains(charString) || desc.toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
