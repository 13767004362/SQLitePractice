package com.xingen.sqlitepractice.orm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xingen.sqlitepractice.db.Data;
import com.xingen.sqlitepractice.db.DataHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${新根} on 2017/5/7 0007.
 * blog: http://blog.csdn.net/hexingen
 *
 * Dao的实现类，对Message表进行增删查改操作。
 */
public class MessageDAO implements DAO<Message> {
    private Context context;
    private DataHelper dataHelper;

    public MessageDAO(Context context) {
        this.context = context;
        this.dataHelper = new DataHelper(this.context);
    }

    @Override
    public List<Message> queryAll() {
        return queryAction(null,null);
    }

    @Override
    public List<Message> queryAction(String selection,
                                     String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<Message> list = null;
        try {
            sqLiteDatabase = this.dataHelper.getReadableDatabase();
            cursor = sqLiteDatabase.query(Data.TABLE_NAME, null, selection, selectionArgs, null, null, Data.ORDER_BY);
            list=new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(ValuesTransform.transformMessage(cursor));
                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return list;
    }

    @Override
    public void delite() {

    }

    @Override
    public void insert(Message message) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = this.dataHelper.getWritableDatabase();
            sqLiteDatabase.insert(Data.TABLE_NAME,null,ValuesTransform.transformContentValues(message));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }

    @Override
    public void update(Message message) {

    }
}
