package com.example.td3;

import com.example.td3.presentation.model.RestDigimonResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DigiAPI {
    @GET("/xiad-fr98/td3/master/data.json")
    Call< RestDigimonResponse > getDigimonResponse();
}
