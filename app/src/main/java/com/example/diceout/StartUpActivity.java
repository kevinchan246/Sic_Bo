package com.example.diceout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartUpActivity extends AppCompatActivity {


    //Field to hold a button
    Button startPlayingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        startPlayingButton = (Button) findViewById(R.id.startPlaying);
        startPlayingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(StartUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }





}
