package com.xingen.sqlitepractice.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by ${新根} on 2017/5/9 0009.
 * blog: http://blog.csdn.net/hexingen
 *
 * 数据库发生改变时如何自动更新UI
 *  过程：数据库---contentprovider/contentresolver
 *             ---cursor的观察者-数据源发生改变
 *             ---cursorloader重新加载数据
 *             ---cursoradapter的changercursor提换数据
 *  做法：
 *  1. 对cursor数据设置监听的url，即在contentprovider中query()调用
 （或者loader的loadeingbackground()）调用cursor.setNotificationUri()
 *  2.ContentProvider的insert()、update()、delete()等方法中调用ContentResolver的notifyChange()方法
 */

public class DataContentProvider extends ContentProvider {
    private static final int TABLE_DIR=1;

    private static UriMatcher uriMatcher;
    private DataHelper dataHelper;
    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Data.AUTHORITY,Data.TABLE_NAME,TABLE_DIR);
    }
    @Override
    public boolean onCreate() {
        dataHelper=new DataHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri)){
            case TABLE_DIR:
                cursor = db.query(Data.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            default:
                break;
        }
        if(cursor!=null){
            //添加通知对象
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case TABLE_DIR:
             return     "vnd.android.cursor.dir/vnd." +Data.AUTHORITY
                     + Data.TABLE_NAME;
            default:
                    break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteDatabase=dataHelper.getWritableDatabase();
        Uri returnUri=null;
        switch (uriMatcher.match(uri)){
            case TABLE_DIR:
               long rowId= sqLiteDatabase.insert(Data.TABLE_NAME,null,values);
                returnUri=Uri.parse("content://" + Data.AUTHORITY + "/"
                        + Data.TABLE_NAME + "/" + rowId);
                break;
            default:
                break;
        }
        //通知，数据源发生改变
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase=this.dataHelper.getWritableDatabase();
        int deliteRow=0;
        switch (uriMatcher.match(uri)){
            case TABLE_DIR:
                deliteRow=sqLiteDatabase.delete(Data.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                break;
        }
        //通知，数据源发生改变
        getContext().getContentResolver().notifyChange(uri,null);
        return deliteRow;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase=dataHelper.getWritableDatabase();
        int updateRow=0;
        switch (uriMatcher.match(uri)){
            case TABLE_DIR:
                updateRow=sqLiteDatabase.update(Data.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                break;
        }
        if(updateRow>0){     //通知，数据源发生改变
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return 0;
    }
}
