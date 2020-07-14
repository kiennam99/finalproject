package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CityIni extends AppCompatActivity {
    Button cityDialog;
    Button cityNext;
    String[] listItems;
    boolean[] checkItems;
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Integer> SelectedItems = new ArrayList<>();
    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_ini);
        tx = (TextView) findViewById(R.id.label_city_ini_result);
        cityDialog = (Button) findViewById(R.id.button_city_select);
        listItems = getResources().getStringArray(R.array.CityList);
        checkItems = new boolean[listItems.length];
        cityDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CityIni.this);
                mBuilder.setTitle("City Choices");
                mBuilder.setMultiChoiceItems(listItems, checkItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(!SelectedItems.contains(position)){
                                SelectedItems.add(position);
                            }else{
                                SelectedItems.remove(position);
                            }
                        }
                        else{
                            SelectedItems.remove(position);
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i = 0;i<SelectedItems.size();i++){
                            result.add(listItems[SelectedItems.get(i)]);
                            item = item+listItems[SelectedItems.get(i)];
                            if(i!= SelectedItems.size()-1){
                                item +=",";
                            }
                        }
                        tx.setText(item);

                    }
                });
                mBuilder.setNegativeButton(R.string.dialog_clear_all, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i = 0; i < checkItems.length;i++){
                            checkItems[i] = false;
                            SelectedItems.clear();
                            result.clear();
                            tx.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        cityNext = (Button) findViewById(R.id.button_city_next);
        cityNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCityNext();
            }
            private void goCityNext(){
                Intent intent = new Intent(CityIni.this,CitySchool.class);
                intent.putExtra("result",result);
                startActivity(intent);
            }
        });
    }
}