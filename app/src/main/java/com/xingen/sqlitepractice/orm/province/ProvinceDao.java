package com.xingen.sqlitepractice.orm.province;

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
public class ProvinceDao implements DAO<Province> {
    private Context context;
    public ProvinceDao(Context context){
        this.context=context;
    }
    @Override
    public List<Province> queryAll() {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        List<Province> list = null;
        try {
            database = context.openOrCreateDatabase(WriterDBUtils.CITYDB_NAME, Context.MODE_PRIVATE, null);
            if (database != null) {
                cursor = database.query(WriterDBUtils.TABLE_PROVICE, new String[]{WriterDBUtils.COLUMN_PID,WriterDBUtils. COLUMN_PNAME}, null, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    list = new ArrayList<>();
                    do {
                        list.add(ValuesTransform.transformProvince(cursor));
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {//释放资源
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
    public List<Province> queryAction(String selection, String[] selectionArgs) {
        return null;
    }

    @Override
    public void delite() {

    }

    @Override
    public void insert(Province province) {

    }

    @Override
    public void update(Province province) {

    }
}
