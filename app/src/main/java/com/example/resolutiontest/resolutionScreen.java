package com.example.resolutiontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class resolutionScreen extends AppCompatActivity {
    EditText name;
    Spinner numTime;
    Spinner wordTime;




    public void next(View view){
        Intent intent = new Intent(getApplicationContext(), dailyGrind.class);
        int toki = 0;
        toki+=Integer.parseInt(numTime.getSelectedItem().toString());
        if(wordTime.getSelectedItem().toString().equals("months")){
            toki*=30;
        }
        else if(wordTime.getSelectedItem().toString().equals("years")){
            toki*=365;
        }
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("time", toki);
        Log.i("name", name.getText().toString());
        Log.i("time", Integer.toString(toki));
        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolution_screen);
        name = findViewById(R.id.name);
        numTime = findViewById(R.id.numTime);
        wordTime = findViewById(R.id.wordTime);
        ArrayList<String> nums = new ArrayList<String>();
        ArrayList<String> words = new ArrayList<String>();
        for(int i = 1; i < 31; i ++){
            nums.add(Integer.toString(i));
        }
        words.add("days");
        words.add("months");
        words.add("years");
        ArrayAdapter<String> numAdapter = new ArrayAdapter<>(resolutionScreen.this, android.R.layout.simple_spinner_dropdown_item, nums);
        numTime.setAdapter(numAdapter);
        ArrayAdapter<String> wordAdapter = new ArrayAdapter<>(resolutionScreen.this, android.R.layout.simple_spinner_dropdown_item, words);
        wordTime.setAdapter(wordAdapter);
    }
}
