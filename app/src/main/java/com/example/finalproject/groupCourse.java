package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class groupCourse extends AppCompatActivity {
    Button group_course_select;
    Button group_course_next;
    String[] listItems;
    boolean[] checkItems;
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> nextIds = new ArrayList<>();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Integer> SelectedItems = new ArrayList<>();
    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_course);
        readCsv();
        result = getIntent().getStringArrayListExtra("result");
        group_course_select = (Button) findViewById(R.id.button_group_course_select);
        tx = (TextView) findViewById(R.id.label_group_course_result);
        tx.setText("");
        String temp = "";
        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < grouping.size(); j++){
                if (grouping.get(j).getGroup().equals(result.get(i))) {
                    ids.add(grouping.get(j).getId());
                    if(!result.contains(grouping.get(j).getCourse())){
                    temp += grouping.get(j).getCourse();
                    temp += ",";
                    result.add(grouping.get(j).getCourse());
                    }
                }
            }
        }


        listItems = temp.split(",");

        for(int i = 0 ; i < listItems.length;i++){
            if(listItems[i].equals("")){
                listItems[i] = "NULL";
            }
        }
       checkItems = new boolean[listItems.length];
        group_course_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(groupCourse.this);
                mBuilder.setTitle("Select Courses");
                mBuilder.setMultiChoiceItems(listItems, checkItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            if (!SelectedItems.contains(which)) {
                                SelectedItems.add(which);

                            } else {
                                SelectedItems.remove(which);

                            }
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        tx.setText("");

                        for (int i = 0; i < SelectedItems.size(); i++) {
                            item = item + listItems[SelectedItems.get(i)];
                            for(int j = 0; j < grouping.size();j++) {
                                if(grouping.get(j).getCourse().equals(listItems[SelectedItems.get(i)]))
                                    nextIds.add(grouping.get(j).getId());
                            }
                            if (i != SelectedItems.size() - 1) {
                                item += ",";
                            }
                        }
                        tx.append(item);
                    }
                });
                mBuilder.setNegativeButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkItems.length; i++) {
                            checkItems[i] = false;
                            SelectedItems.clear();
                            nextIds.clear();
                            tx.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }

        });

        group_course_next = (Button) findViewById(R.id.button_group_course_next);
        group_course_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goGroupNext();
            }

            private void goGroupNext() {
                Intent intent = new Intent(groupCourse.this, Group_School.class);
                Toast.makeText(groupCourse.this,nextIds.get(0),Toast.LENGTH_SHORT).show();
                intent.putExtra("result", nextIds);

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