package com.example.xieyongxiong.callblocking.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xieyongxiong on 16-6-18.
 */
public class blacklist_dbHelper extends SQLiteOpenHelper {

    public blacklist_dbHelper(Context context) {
        super(context, "blacklist", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table blacklist("
                    +"_id integer primary key autoincrement,"
                    +"call varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
