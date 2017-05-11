package com.xingen.sqlitepractice.orm;

import android.content.ContentValues;
import android.database.Cursor;

import com.xingen.sqlitepractice.db.Data;
import com.xingen.sqlitepractice.orm.city.City;
import com.xingen.sqlitepractice.orm.message.Message;
import com.xingen.sqlitepractice.orm.province.Province;

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

    /**
     * 从Cursor生成City对象
     * @param cursor
     * @return
     */
    public static City transformCity(Cursor cursor){
        City city=new City();
        city.setCname(cursor.getString(cursor.getColumnIndex(WriterDBUtils.COLUMN_CNAME)));
        return  city;
    }

    /**
     * 从Cursor生成Province对象
     * @param cursor
     * @return
     */
    public static Province transformProvince(Cursor cursor){
        Province province = new Province();
        province.setPid(cursor.getInt(cursor.getColumnIndex(WriterDBUtils.COLUMN_PID)));
        province.setPname(cursor.getString(cursor.getColumnIndex(WriterDBUtils.COLUMN_PNAME)));
        return  province;
    }
}
