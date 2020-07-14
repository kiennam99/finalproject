package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button GroupB;
    Button CityB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GroupB = (Button) findViewById(R.id.GroupButton);
        CityB = (Button) findViewById(R.id.CityButton);
        GroupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGroup();
            }
        });
        CityB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCity();
            }
        });
    }
    private void startGroup(){
        Intent intent = new Intent(MainActivity.this,GroupIni.class);
        startActivity(intent);
    }
    private void startCity(){
        Intent intent = new Intent(MainActivity.this,CityIni.class);
        startActivity(intent);
    }
}