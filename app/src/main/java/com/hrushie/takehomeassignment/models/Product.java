package com.hrushie.takehomeassignment.models;

/**
 * Created by hrushie on 7/15/2017.
 * Model Class for the Products
 */

public class Product {

    private String productiId;
    private String productiName;
    private String shortDescription;
    private String longDescription;
    private String productImage;
    private int reviewCount;
    private int reviewRating;
    private String price;
    private boolean inStock;
    private int pagenumber;

    public int getPagenumber() {
        return pagenumber;
    }

    public void setPagenumber(int pagenumber) {
        this.pagenumber = pagenumber;
    }

    public String getProductiId() {
        return productiId;
    }

    public void setProductiId(String productiId) {
        this.productiId = productiId;
    }

    public String getProductiName() {
        return productiName;
    }

    public void setProductiName(String productiName) {
        this.productiName = productiName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}
