package com.example.td3.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.td3.R;
import com.example.td3.Singletons;
import com.example.td3.controller.MainController;
import com.example.td3.presentation.model.Digimon;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private MainController controller;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        controller = new MainController(
                this,
                Singletons.getGson(),
                Singletons.getSharedPreferences( getApplicationContext())
        );

        controller.onStart();
    }

    public void showList(List<Digimon> digimonList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ListAdapter( digimonList, new ListAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Digimon item){
                controller.onItemClick( item );
            }
        }, getApplicationContext() );
        recyclerView.setAdapter(mAdapter);
    }
    public void showError(){
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

    public void navigateToDetails(Digimon digimon){
        Toast.makeText(getApplicationContext(), "Screen Change", Toast.LENGTH_SHORT).show();
    }
}
