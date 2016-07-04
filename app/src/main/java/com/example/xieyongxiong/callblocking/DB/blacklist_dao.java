package com.example.xieyongxiong.callblocking.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieyongxiong on 16-6-18.
 */
public class blacklist_dao {

    private Context mcontext;
    private blacklist_dbHelper dbHelper;
    private SQLiteDatabase db;
    public blacklist_dao(Context context) {
        mcontext=context;
        dbHelper=new blacklist_dbHelper(mcontext);
    }
    public List<String> query(){
        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query("blacklist",null,null,null,null,null,null);
        List<String> list = new ArrayList<String>();
        int numberIndex = cursor.getColumnIndex("call");

        while (cursor.moveToNext()){
            list.add(cursor.getString(numberIndex));
        }
        return list;
    }
    public void insert(String number){
        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("call",number);
        db.insert("blacklist",null,contentValues);
    }
    public void delete(String number){
//        String sql = "delete from blacklist where call = "+number;
//        db.execSQL(sql);

        db = dbHelper.getWritableDatabase();
        db.delete("blacklist","call=?",new String[]{number});
    }

    public void close(){
        if(db.isOpen())
            db.close();
    }
}
