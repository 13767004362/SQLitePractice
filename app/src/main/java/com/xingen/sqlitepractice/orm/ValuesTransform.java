package com.xingen.sqlitepractice.orm;

import android.content.ContentValues;
import android.database.Cursor;

import com.xingen.sqlitepractice.db.Data;

/**
 * Created by ${新根} on 2017/5/7 0007.
 * blog: http://blog.csdn.net/hexingen
 *
 * 用途：
 * 各种对象转换的工具类
 */
public class ValuesTransform {
    /**
     * 从Cursor生成Message对象
     * @param cursor
     * @return
     */
    public static Message transformMessage(Cursor cursor){
        Message message=new Message();
        message.setId(cursor.getInt(cursor.getColumnIndex(Data._ID)));
        message.setContent(cursor.getString(cursor.getColumnIndex(Data.COLUMN_CONTENT)));
        message.setDate(cursor.getString(cursor.getColumnIndex(Data.COLUMN_DATE)));
        return  message;
    }

    /**
     * 从Message生成ContentValues
     * @param message
     * @return
     */
    public static ContentValues transformContentValues(Message message){
        ContentValues contentValues=new ContentValues();
        contentValues.put(Data.COLUMN_CONTENT,message.getContent());
        contentValues.put(Data.COLUMN_DATE,message.getDate());
        return  contentValues;
    }
}
