package com.example.td3;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DigiAPI {
    @GET("/api/digimon")
    static Call< RestDigimonResponse > getdigimonResponse() {
        return null;
    }
}
