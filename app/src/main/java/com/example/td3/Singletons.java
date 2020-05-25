package com.example.td3;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons{

    private static Gson GsonInstance;
    private static DigiAPI digiAPIinstance;
    private static SharedPreferences sharedPreferences;

    public static Gson getGson ()
    {
        if (GsonInstance == null){
            GsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return GsonInstance;
    }

    public static DigiAPI getDigiAPI () {
        if (digiAPIinstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory( GsonConverterFactory.create(getGson()))
                    .build();

            digiAPIinstance = retrofit.create(DigiAPI.class);
        }
        return digiAPIinstance;
    }

    public static SharedPreferences getSharedPreferences (Context context)
    {
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("app_digimon", Context.MODE_PRIVATE  );

        }
        return sharedPreferences;
    }



}
