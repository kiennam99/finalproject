package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GroupResult extends AppCompatActivity {
    ListView ls;
    ArrayList<String> prevId = new ArrayList<>();
    List<groupsample> tempSample = new ArrayList<>();
    List<groupresultclass> finalResult = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_result);
        readCCsv();
        readCsv();
        ls = findViewById(R.id.listView_group);
        int con = 0;
        prevId = getIntent().getStringArrayListExtra("result");

        assert prevId != null;
        for(int i = 0; i < prevId.size(); i++){
            for(int j = 0; j < finalSample.size();j++){
                if(finalSample.get(j).getId().equals(prevId.get(i))){
                    tempSample.add(finalSample.get(j));
                    con++;
                }
            }
        }

        int counter = 0;
        for(int i = 0 ;i < tempSample.size();i++){

            for(int j = 0;j < grouping.size();j++){

                if(grouping.get(j).getSchool().equals(tempSample.get(i).getSchool()) && grouping.get(j).getCourse().equals(tempSample.get(i).getCourse())){
                    finalResult.add(grouping.get(j));
                    counter++;
                }
            }
        }

       // Toast.makeText(GroupResult.this, tempSample.get(0).getSchool()+tempSample.get(0).getId()+grouping.get(191).getSchool()+grouping.get(191).getCourse(),Toast.LENGTH_SHORT).show();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(GroupResult.this);

        /*for(int i = 0 ; i < grouping.size();i++){
            dataBaseHelper.addOne(grouping.get(i));
        }*/
        //Toast.makeText(GroupResult.this,finalResult.size(), Toast.LENGTH_SHORT).show();
        List<groupresultclass> every = dataBaseHelper.getAll(finalResult);
        myAdapter adapter = new myAdapter(GroupResult.this,every);
        ls.setAdapter(adapter);

    }
    private List<groupresultclass> grouping = new ArrayList<>();

    private void readCsv() {

        InputStream is = getResources().openRawResource(R.raw.a);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(",");
                groupresultclass sample = new groupresultclass();
                sample.setCourse(token[1]);
                sample.setSchool(token[0]);
                sample.setWebsite(token[6]);
                sample.setNStudent(token[5]);
                sample.setWeight(token[4]);
                sample.setMarks(token[3]);
                grouping.add(sample);
            }

        } catch (IOException e) {
            Log.wtf("Error reading csv: ", e);
            e.printStackTrace();
        }


    }

    private List<groupsample> finalSample = new ArrayList<>();
    private void readCCsv(){
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
                finalSample.add(sample);
            }

        } catch (IOException e) {
            Log.wtf("Error reading csv: ", e);
            e.printStackTrace();
        }
    }
}