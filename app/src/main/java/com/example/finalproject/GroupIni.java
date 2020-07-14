package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GroupIni extends AppCompatActivity {
    Button groupDialog;
    Button groupNext;
    //  TextView tx;
    ArrayList<String> result = new ArrayList<>();
    String[] listItems;
    boolean[] checkItems;
    ArrayList<Integer> SelectedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);
        groupDialog = (Button) findViewById(R.id.selectG);
        // tx = (TextView) findViewById()
        listItems = getResources().getStringArray(R.array.GroupList);
        checkItems = new boolean[listItems.length];
        groupDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(GroupIni.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems, checkItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!SelectedItems.contains(listItems[position])) {
                                SelectedItems.add(position);
                            } else {
                                SelectedItems.remove(position);
                            }
                        }

                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        TextView tx = (TextView) findViewById(R.id.label_group_select_result);
                        tx.setText("");
                        for (int i = 0; i < SelectedItems.size(); i++) {
                            item = item + listItems[SelectedItems.get(i)];
                            if (!result.contains(listItems[SelectedItems.get(i)])) {
                                result.add(listItems[SelectedItems.get(i)]);
                            }
                            if (i != SelectedItems.size() - 1) {
                                item += " , ";
                            }
                        }
                        tx.append(item);
                    }
                });
                mBuilder.setNegativeButton(R.string.dialog_clear_all, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkItems.length; i++) {
                            checkItems[i] = false;
                            SelectedItems.clear();
                            result.clear();
                        }
                        TextView tx = (TextView) findViewById(R.id.label_group_select_result);
                        tx.setText("");
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        groupNext = (Button) findViewById(R.id.button_group_next);
        groupNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goGroupNext();
            }

            private void goGroupNext() {
                Intent intent = new Intent(GroupIni.this, groupCourse.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        });
    }


}