package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Group_School extends AppCompatActivity {
    Button group_school_select;
    Button group_school_next;
    String[] listItems;
    ArrayList<String> prevId = new ArrayList<>();
    ArrayList<String> schoolList = new ArrayList<>();
    ArrayList<groupsample> ids = new ArrayList<>();
    ArrayList<String> finalId = new ArrayList<>();
    boolean[] checkItems;
    ArrayList<Integer> SelectedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group__school);
        group_school_select = (Button) findViewById(R.id.button_group_school_select);
        group_school_next = (Button) findViewById(R.id.button_group_school_next);
        final TextView tx = (TextView) findViewById(R.id.label_group_school_result);
        readCsv();
        prevId = getIntent().getStringArrayListExtra("result");
        String temp = "";
        assert prevId != null;
        if (prevId.size() > 0 && grouping.size() > 0) {
            for (int i = 0; i < prevId.size(); i++) {
                for (int j = 0; j < grouping.size(); j++)
                    if (grouping.get(j).getId().equals(prevId.get(i))) {

                        ids.add(grouping.get(j));
                        if (!schoolList.contains(grouping.get(j).getSchool())) {

                            schoolList.add(grouping.get(j).getSchool());
                            temp += grouping.get(j).getSchool();
                            temp += ",";
                        }
                    }
            }

        }
       // Toast.makeText(Group_School.this, "a"+ids.get(0).getId(),Toast.LENGTH_SHORT).show();
        listItems = temp.split(",");
        checkItems = new boolean[listItems.length];
        group_school_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Group_School.this);
                mBuilder.setTitle("Select School");
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
                            for (int j = 0; j < ids.size(); j++) {
                                if (ids.get(j).getSchool().equals(listItems[SelectedItems.get(i)])) {
                                    finalId.add(ids.get(j).getId());
                                }
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
                            finalId.clear();
                            tx.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        group_school_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResult();
            }

            private void getResult() {
                Intent intent = new Intent(Group_School.this, GroupResult.class);
                intent.putExtra("result", finalId);
                Toast.makeText(Group_School.this,finalId.get(0),Toast.LENGTH_SHORT).show();
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