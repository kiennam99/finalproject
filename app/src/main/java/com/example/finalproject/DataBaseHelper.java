package com.example.finalproject;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String Table = "MY_TABLE";
    public static final String Column_School = "School";
    public static final String Column_course = "Course";
    public static final String Column_NStudent = "NStudent";
    public static final String Column_marks = "Marks";
    public static final String Column_Website = "Website";
    public static final String Column_Weight = "Weight";



    public DataBaseHelper(@Nullable Context context){
        super(context,"my.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE MY_TABLE(" + Column_School + " Text ," + Column_course + " Text, " + Column_marks + " Text, " + Column_NStudent + " Text, " + Column_Website + " Text, "+ Column_Weight +  " Text) ";
        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(groupresultclass grouping){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_School,grouping.getSchool());
        cv.put(Column_course,grouping.getCourse());
        cv.put(Column_marks,grouping.getMarks());
        cv.put(Column_NStudent,grouping.getNStudent());
        cv.put(Column_Website,grouping.getWebsite());
        cv.put(Column_Weight,grouping.getWeight());

        long insert = db.insert(Table, null,cv);
        if(insert == -1) return false;
        else return true;

    }
    public List<groupresultclass> getAll(List<groupresultclass> finalResult){
        List<groupresultclass> returnlist = new ArrayList<>();
        for(int i = 0; i < finalResult.size();i++){
        String queryString = "SELECT * FROM " + Table + " where "+ Column_School+"='" + finalResult.get(i).getSchool() + "' and " +Column_course +"='" + finalResult.get(i).getCourse()+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()){
            //loop through the cursor result
            do{
                String School = cursor.getString(0);
                String course = cursor.getString(1);
                String marks = cursor.getString(2);
                String NStudent = cursor.getString(3);
                String Website = cursor.getString(4);
                String Weight = cursor.getString(5);
                groupresultclass newresult = new groupresultclass(School,course,marks,NStudent,Website,Weight);
                returnlist.add(newresult);
            }while(cursor.moveToNext());

        }
        else {

        }
        cursor.close();
        db.close();
        }
        return returnlist;
    }
}
