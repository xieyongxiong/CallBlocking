package com.example.xieyongxiong.callblocking.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieyongxiong on 16-6-25.
 */
public class location_dao {

    private Context mcontext;
    private location_dbHelper dbHelper;
    private SQLiteDatabase db;

    public location_dao(Context context) {
        mcontext = context;
        dbHelper = new location_dbHelper(mcontext);
        db = dbHelper.getReadableDatabase();
    }

    public List<String> query(){

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("location",null,null,null,null,null,null);
        int locIndex = cursor.getColumnIndex("loc");
        List<String> result = new ArrayList<>();
        while(cursor.moveToNext()){
            result.add(cursor.getString(locIndex));
        }
        return result;
    }

    public void add(String loc){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("loc",loc);
        db.insert("location",null,values);
    }

    public void delete(String loc){
        db = dbHelper.getWritableDatabase();
        db.delete("location","loc=?",new String[]{loc});
    }

    public void close(){
        if(db.isOpen())
            db.close();
    }


}
