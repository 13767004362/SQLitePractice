package com.xingen.sqlitepractice.sqlbrite;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.xingen.sqlitepractice.db.DataHelper;

import rx.schedulers.Schedulers;

/**
 * Created by ${新根} on 2017/5/10 0010.
 * blog: http://blog.csdn.net/hexingen
 *
 * SQLBrite的操作类。
 */
public class SqlBriteProvider {
    private SqlBrite sqlBrite;
    private BriteDatabase briteDatabase;
    public SqlBriteProvider(Context context){
        this.sqlBrite=providerSqlBrite();
        this.briteDatabase=createDatabase(this.sqlBrite,providerOpenHelper(context));
    }

    /**
     * 创建SQLiteOpenHelper对象
     * @param context
     * @return
     */
    private SQLiteOpenHelper providerOpenHelper(Context context){
        return  new DataHelper(context);
    }

    /**
     *
     * 创建SqlBrite对象
     *
     * @return
     */
    private SqlBrite providerSqlBrite(){
        return  new SqlBrite.Builder().build();
    }
    /**
     * 通过SQLBrite对象和SQLiteOpenHel对象
     * @param sqlBrite
     * @param sqLiteOpenHelper
     * @return
     */
    public BriteDatabase createDatabase(SqlBrite sqlBrite,SQLiteOpenHelper sqLiteOpenHelper){
        BriteDatabase db=sqlBrite.wrapDatabaseHelper(sqLiteOpenHelper, Schedulers.io());
        return db;
    }
    /**
     * 获取到项目默认的数据库
     * @return
     */
    public BriteDatabase getBriteDatabase() {
        return briteDatabase;
    }
}
