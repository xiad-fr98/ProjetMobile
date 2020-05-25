package com.example.td3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.td3.presentation.model.Digimon;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class Detail_screen extends AppCompatActivity {

    private TextView name_detail;
    private ImageView image_detail;
    private TextView level_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.page_detail );


        name_detail = findViewById( R.id.name_detail );
        level_detail = findViewById( R.id.level_detail );
        image_detail = findViewById( R.id.image_detail );
        Intent intent = getIntent();
        String digimonjson = intent.getStringExtra(Constants.DIGIMON_KEY);
        Digimon digimon = Singletons.getGson().fromJson( digimonjson, Digimon.class );
        Picasso.with( this ).load( digimon.getImg()).into( image_detail );
        showDetail(digimon);
    }

    private void showDetail(Digimon digimon){

        name_detail.setText( "Name: "+digimon.getName());
        level_detail.setText("Level: "+digimon.getLevel() );
    }

}
