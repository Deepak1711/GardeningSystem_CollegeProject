package com.example.deepak.myfirstfirebaseproject;

import com.example.deepak.myfirstfirebaseproject.Model.ApiResponse;
import com.example.deepak.myfirstfirebaseproject.Model.ShowDataResponse;
import com.example.deepak.myfirstfirebaseproject.Model.TokenUpdateResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created on 2/4/17.
 */


interface APICallInterface {
    @GET("firebase/fcm_insert.php")
    Call<TokenUpdateResponse> processRequest(@QueryMap Map<String, String> options);

    @GET("smsRegistration/register.php")
    Call<ApiResponse> registerUser(@QueryMap Map<String, String> options);

    @GET("login.php")
    Call<ApiResponse> loginUser(@QueryMap Map<String, String> options);

    @GET("apiFunctions/fetchData.php")
    Call<ShowDataResponse> fetchParameterValues(@QueryMap Map<String, String> options);
}
