package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class myAdapter extends BaseAdapter {
    private Context mContext;
    private  List<groupresultclass> grouping;

    public myAdapter(Context mContext,List<groupresultclass> grouping) {
        this.grouping = grouping;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return grouping.size();
    }

    @Override
    public Object getItem(int position) {
        return grouping.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext,R.layout.activity_group_result_view,null);
        TextView school = (TextView) v.findViewById(R.id.label_group_result_school);
        TextView course = (TextView) v.findViewById(R.id.label_group_result_course);
        TextView marks = (TextView) v.findViewById(R.id.label_group_result_marks);
        TextView website = (TextView) v.findViewById(R.id.label_group_result_website);
        TextView weight = (TextView) v.findViewById(R.id.label_group_result_weight);
        TextView nstudent = (TextView) v.findViewById(R.id.label_group_result_nstudent);
        school.setText(grouping.get(position).getSchool());
        course.setText("科系："+grouping.get(position).getCourse());
        marks.setText("分數："+grouping.get(position).getMarks());
        website.setText("網址："+grouping.get(position).getWebsite());
        weight.setText("權重："+grouping.get(position).getWeight());
        nstudent.setText("學生人數："+grouping.get(position).getNStudent());
        v.setTag(grouping.get(position).getCourse());
        return v;
    }


}
