package com.example.resolutiontest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class dailyGrind extends AppCompatActivity {

    String name;
    int time;
    EditText dailyGrind;


    public void saveAndFinish(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        String namePlusTime  = name + " - " + time + " days left";
        MainActivity.daysLeft.add(time);

//        if(MainActivity.resolutions.get(0).equals("No resolutions...") ){
//            MainActivity.resolutions.set(0, namePlusTime);
//        }
//        else {
            MainActivity.resolutions.add(namePlusTime);
            Log.i("TIME AND NAME: ", namePlusTime);

        //}

        MainActivity.veryCool.notifyDataSetChanged();
        String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
//        if(MainActivity.resDates.get(0).equals("Placeholder") ){
//            MainActivity.resDates.set(0, currentDate);
//        }
//        else {
            MainActivity.resDates.add(currentDate);


        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.resolutiontest", Context.MODE_PRIVATE);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("com.example.resolutiontest", Context.MODE_PRIVATE);
        HashSet<String> set = new HashSet<>(MainActivity.resolutions);
        HashSet<String> dateSet = new HashSet<>(MainActivity.resDates);
        sharedPreferences.edit().putStringSet("notes", set).apply();
        preferences.edit().putStringSet("dates", dateSet).apply();



        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_grind);
        name = getIntent().getStringExtra("name");
        time = getIntent().getIntExtra("time", 0);
        dailyGrind = findViewById(R.id.dailyGrind);
    }
}
