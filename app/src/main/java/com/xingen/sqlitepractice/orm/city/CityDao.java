package com.xingen.sqlitepractice.orm.city;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xingen.sqlitepractice.orm.DAO;
import com.xingen.sqlitepractice.orm.ValuesTransform;
import com.xingen.sqlitepractice.orm.WriterDBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${新根} on 2017/5/11 0011.
 * blog: http://blog.csdn.net/hexingen
 */
public class CityDao implements DAO<City>{
    private  Context context;
    public CityDao(Context context){
        this.context=context;
    }
    @Override
    public List<City> queryAll() {
        return null;
    }
    @Override
    public List<City> queryAction(String selection, String[] selectionArgs) {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        List<City> list = null;
        try {
            database = context.openOrCreateDatabase(WriterDBUtils.CITYDB_NAME, Context.MODE_PRIVATE, null);
            if (database != null) {
                cursor = database.query(WriterDBUtils.TABLE_CITY, new String[]{WriterDBUtils.COLUMN_CNAME},selection, selectionArgs, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    list = new ArrayList<>();
                    do {
                        list.add(ValuesTransform.transformCity(cursor));
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
        return list;
    }

    @Override
    public void delite() {
    }
    @Override
    public void insert(City city) {
    }
    @Override
    public void update(City city) {
    }
}
