package com.hrushie.takehomeassignment.controllers.http;

/**
 * Created by hrushie on 7/15/2017.
 */

public class ProductUrl {

    private volatile static ProductUrl uniqInstance;

    private final String url = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1/walmartproducts";
    private final String API_KEY = "c86bbded-3988-463f-94a5-6443ed7cec34";
    private final int pageSize = 20;


    private ProductUrl() {

    }

    public static ProductUrl getInstance(){

        if (uniqInstance == null){

            synchronized (ProductUrl.class){

                if (uniqInstance == null){

                    uniqInstance = new ProductUrl();
                }
            }
        }
    return uniqInstance;
    }

    public String getProductQuery(int pageNumber){

         return url + "/" + API_KEY + "/" + pageNumber + "/" + pageSize;
    }


}
