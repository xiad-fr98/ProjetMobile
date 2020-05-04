package com.example.td3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showList();
        makeAPIcall();
    }

    private void showList() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }

        mAdapter = new ListAdapter(input);
        recyclerView.setAdapter(mAdapter);
    }

    private void makeAPIcall()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        DigiAPI digiAPI = retrofit.create(DigiAPI.class);

        Call<RestDigimonResponse> call = digiAPI.getDigimonResponse();
        call.enqueue(new Callback< RestDigimonResponse >() {
            @Override
            public void onResponse(Call< RestDigimonResponse > call, Response< RestDigimonResponse > response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Digimon> digimonList = response.body().getResults();
                    Toast.makeText(getApplicationContext(), "API Successfull", Toast.LENGTH_SHORT).show();

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

    public void showError(){
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}
