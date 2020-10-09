package com.example.resolutiontest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    static ArrayList<String> resolutions = new ArrayList<String>();
    static ArrayAdapter<String> veryCool;
    SharedPreferences sharedPreferences;
    SharedPreferences preferences;
    static ArrayList<String> resDates = new ArrayList<String>();
    static ArrayList<Integer> daysLeft = new ArrayList<>();





    public void addNew (View view){
        Intent intent = new Intent(getApplicationContext(), resolutionScreen.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.resolutiontest", Context.MODE_PRIVATE);
        preferences = getApplicationContext().getSharedPreferences("com.example.resolutiontest", Context.MODE_PRIVATE);

        resolutions.clear();
        resDates.clear();

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);
        HashSet<String> dateSet = (HashSet<String>) preferences.getStringSet("dates", null);

        if (set == null && dateSet == null) {
            resolutions.add("No resolutions...");
            resDates.add("Placeholder");
        } else{
            resolutions = new ArrayList<>(set);
            resDates = new ArrayList<>(dateSet);
        }
//        resDates.remove(1);
        Log.i("RES DATES", resDates.toString());
        String todaysDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
        String dateDifference = "";
        //I HAVE TO MAKE SURE DATES ARE BEING PUT INTO THE DATE ARRAY FOR THIS TO WORK


        for(String date : resDates){
            try {
                Date today = dates.parse(todaysDate);
                Date resDate = dates.parse(date);
                long difference = Math.abs(today.getTime() - resDate.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);

                for(int i = 0; i < resolutions.size(); i++){
                    long dateDiff = daysLeft.get(i)-differenceDates;
                    dateDifference = Long.toString(differenceDates);
                    String temp = resolutions.get(i);
                    int index = resolutions.indexOf("-");
                    String newDateRes = temp.substring(0, index);
                    Log.i("@@@@Date Difference:", dateDifference);
                    newDateRes+= "- " +dateDifference + " days left";
                    resolutions.set(i, newDateRes);
                    veryCool.notifyDataSetChanged();
                    set = new HashSet<>(MainActivity.resolutions);
                    sharedPreferences.edit().putStringSet("notes", set).apply();


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


        final ListView resolutionsList = findViewById(R.id.resolutionsList);
        veryCool = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resolutions);

        resolutionsList.setAdapter(veryCool);


        resolutionsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                final int itemToDelete = position;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this resolution?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                resolutions.remove(itemToDelete);
                                resDates.remove(itemToDelete);
                                veryCool.notifyDataSetChanged();
                                if(resolutions.size() == 0){
                                    resolutions.add("No new resolutions...");
                                }

                                HashSet<String> set = new HashSet<>(MainActivity.resolutions);
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                                HashSet<String> dateSet = new HashSet<>(MainActivity.resDates);
                                preferences.edit().putStringSet("dates", dateSet).apply();


                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });



    }
}
