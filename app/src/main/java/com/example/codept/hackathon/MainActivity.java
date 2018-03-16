package com.example.codept.hackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.example.codept.hackathon.R.styleable.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    public void Register(android.view.View view){

        Intent i=new Intent(MainActivity.this,Register.class);

        startActivity(i);


    }
}
