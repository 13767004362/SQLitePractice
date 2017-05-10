package com.xingen.sqlitepractice.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ${新根} on 2017/5/7 0007.
 * blog: http://blog.csdn.net/hexingen
 */
public class DataHelper extends SQLiteOpenHelper {
    public static final String CREATE_MESSAGE="create table "+
            Data.TABLE_NAME+"("+
            Data._ID+" integer primary key autoincrement,"+
            Data.COLUMN_CONTENT+" text,"+
            Data.COLUMN_DATE+" text"
            +")";

    public DataHelper(Context context) {
        super(context, Data.SQLITE_NAME, null, Data.SQLITE_VERSON);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
