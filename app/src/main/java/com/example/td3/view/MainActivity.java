package com.example.td3.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.td3.DigiAPI;
import com.example.td3.R;
import com.example.td3.presentation.model.Digimon;
import com.example.td3.presentation.model.RestDigimonResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://raw.githubusercontent.com";
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        sharedPreferences = getSharedPreferences("App_Digimon", Context.MODE_PRIVATE);
         gson = new GsonBuilder()
                .setLenient()
                .create();
         List< Digimon > digimonList = getDatafromCache();
         if (digimonList!=null){
             showList(digimonList);
         }else{
             makeAPIcall();
         }

    }

    private List<Digimon> getDatafromCache() {

        String jsonDigimon = sharedPreferences.getString("jsonDigimonList",null);
        if(jsonDigimon == null) {
            return null;
        }else{
            Type listType = new TypeToken<List<Digimon>>(){}.getType();
            return gson.fromJson(jsonDigimon,listType);
        }

    }

    private void showList(List<Digimon> digimonList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ListAdapter(digimonList,getApplicationContext());
        recyclerView.setAdapter(mAdapter);
    }

    private void makeAPIcall()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        DigiAPI digiAPI = retrofit.create(DigiAPI.class);

        Call< RestDigimonResponse > call = digiAPI.getDigimonResponse();
        call.enqueue(new Callback< RestDigimonResponse >() {
            @Override
            public void onResponse(Call< RestDigimonResponse > call, Response< RestDigimonResponse > response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Digimon> digimonList = response.body().getResults();
                    saveList(digimonList);
                    showList(digimonList);
                }else{
                    Toast.makeText(getApplicationContext(), "ici Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call< RestDigimonResponse > call, Throwable t) {
                showError();
            }
        });

    }

    private void saveList(List< Digimon> digimonList) {
        String jsonString = gson.toJson(digimonList);
        sharedPreferences
                .edit()
                .putString("jsonDigimonList", jsonString)
                .apply();
        Toast.makeText(getApplicationContext(), "List saved", Toast.LENGTH_SHORT).show();
    }

    public void showError(){
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

}
