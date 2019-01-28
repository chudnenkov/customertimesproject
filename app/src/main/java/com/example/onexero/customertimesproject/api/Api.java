package com.example.onexero.customertimesproject.api;

import com.example.onexero.customertimesproject.model.DescribeModel;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface Api {

    @GET("describe")
    Observable<DescribeModel> getDescribe();

    @GET("query")
    Observable<ResponseBody> getQuery();

}
