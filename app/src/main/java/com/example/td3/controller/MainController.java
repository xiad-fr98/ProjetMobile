package com.example.td3.controller;

import android.content.SharedPreferences;

import com.example.td3.Singletons;
import com.example.td3.presentation.model.Digimon;
import com.example.td3.presentation.model.RestDigimonResponse;
import com.example.td3.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.td3.Constants;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController{

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController (MainActivity mainActivity,Gson gson, SharedPreferences sharedPreferences)
    {
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }
    public void onStart(){
        List< Digimon > digimonList = getDatafromCache();
        if (digimonList!=null){
            view.showList(digimonList);
        }else{
            makeAPIcall();
        }

    }
    private void makeAPIcall()
    {


        Call< RestDigimonResponse > call = Singletons.getDigiAPI().getDigimonResponse();
        call.enqueue(new Callback< RestDigimonResponse >() {
            @Override
            public void onResponse(Call< RestDigimonResponse > call, Response< RestDigimonResponse > response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Digimon> digimonList = response.body().getResults();
                    saveList(digimonList);
                    view.showList(digimonList);
                }else{

                }
            }

            @Override
            public void onFailure(Call< RestDigimonResponse > call, Throwable t) {
                view.showError();
            }
        });

    }

    private void saveList(List< Digimon> digimonList) {
        String jsonString = gson.toJson(digimonList);
        sharedPreferences
                .edit()
                .putString("jsonDigimonList", jsonString)
                .apply();
    }

    private List<Digimon> getDatafromCache() {

        String jsonDigimon = sharedPreferences.getString(Constants.KEY_DIGIMON_LIST, null);
        if(jsonDigimon == null) {
            return null;
        }else{
            Type listType = new TypeToken<List<Digimon>>(){}.getType();
            return gson.fromJson(jsonDigimon,listType);
        }

    }


    public void onItemClick (Digimon digimon){
        view.navigateToDetails(digimon);
    }
    public void onButtonAClick(){

    }
    public void onButtonBClick(){

    }
}
