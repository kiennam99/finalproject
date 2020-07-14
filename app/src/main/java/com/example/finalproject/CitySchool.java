package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CitySchool extends AppCompatActivity {
    ArrayList<String> prevCity = new ArrayList<>();
    Button cityDialog;
    Button cityNext;
    String[] listItems;
    boolean[] checkItems;
    ArrayList<String> selectedSchool = new ArrayList<>();
    List<groupsample> constrainGrouping = new ArrayList<>();
    ArrayList<Integer> SelectedItems = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_school);
        cityDialog = findViewById(R.id.button_city_school_select);
        cityNext = findViewById(R.id.button_city_school_next);
        prevCity = getIntent().getStringArrayListExtra("result");
        tx = (TextView) findViewById(R.id.label_city_school_result);
        readCsv();
        String temp = "";
        for (int i = 0; i < prevCity.size(); i++) {
            for (int j = 0; j < grouping.size(); j++) {
                if (grouping.get(j).getCity().equals(prevCity.get(i))) {
                    constrainGrouping.add(grouping.get(j));
                    if(!selectedSchool.contains(grouping.get(j).getSchool())) {
                        temp += grouping.get(j).getSchool();
                        temp += ",";
                        selectedSchool.add(grouping.get(j).getSchool());
                    }
                }
            }
        }
        listItems = temp.split(",");
        checkItems = new boolean[listItems.length];
        cityDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CitySchool.this);
                mBuilder.setTitle("Select School");
                mBuilder.setMultiChoiceItems(listItems, checkItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!SelectedItems.contains(position)) {
                                SelectedItems.add(position);
                            } else {
                                SelectedItems.remove(position);
                            }
                        } else {
                            SelectedItems.remove(position);

                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i = 0; i < SelectedItems.size(); i++) {
                            item = item + listItems[SelectedItems.get(i)];
                            for(int j = 0; j < constrainGrouping.size();j++){
                                if(constrainGrouping.get(j).getSchool().equals(listItems[SelectedItems.get(i)])){
                                    ids.add(constrainGrouping.get(j).getId());
                                }
                            }
                            if(i != SelectedItems.size()-1){
                                item += ",";
                            }
                        }
                        tx.setText(item);
                    }
                });
                mBuilder.setNegativeButton(R.string.dialog_clear_all, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkItems.length; i++) {
                            checkItems[i] = false;
                            SelectedItems.clear();
                            ids.clear();
                            tx.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        cityNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCityNext();
            }

            private void goCityNext() {
                Intent intent = new Intent(CitySchool.this, CityCourse.class);
                intent.putExtra("result", ids);
                startActivity(intent);
            }
        });

    }

    private List<groupsample> grouping = new ArrayList<>();

    private void readCsv() {

        InputStream is = getResources().openRawResource(R.raw.alldata);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(",");
                groupsample sample = new groupsample();
                sample.setCity(token[0]);
                sample.setSchool(token[1]);
                sample.setCourse(token[2]);
                sample.setGroup(token[3]);
                sample.setId(token[4]);
                grouping.add(sample);
            }

        } catch (IOException e) {
            Log.wtf("Error reading csv: ", e);
            e.printStackTrace();
        }


    }
}