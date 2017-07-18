package com.hrushie.takehomeassignment.models;

/**
 * Created by hrushie on 7/17/2017.
 */

public class DataObject {
    private int imageId;
    private String imageName;

    public DataObject(int imageId, String imageName) {
        this.imageId = imageId;
        this.imageName = imageName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
