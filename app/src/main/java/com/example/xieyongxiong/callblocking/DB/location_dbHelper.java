package com.example.xieyongxiong.callblocking.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xieyongxiong on 16-6-25.
 */
public class location_dbHelper extends SQLiteOpenHelper {

    public location_dbHelper(Context context) {
        super(context, "location", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table location("
                +"_id integer primary key autoincrement,"
                +"loc varchar(20))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
