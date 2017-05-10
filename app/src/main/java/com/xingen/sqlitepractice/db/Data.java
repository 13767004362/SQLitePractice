package com.xingen.sqlitepractice.db;

import android.net.Uri;
import android.provider.BaseColumns;



/**
 * Created by ${新根} on 2017/5/7 0007.
 * blog: http://blog.csdn.net/hexingen
 */
public final class Data implements BaseColumns {
    /**
     * 数据库信息
     */
    public static final String SQLITE_NAME="sqlitePrcactice.db";
    public static final int SQLITE_VERSON=1;
    /**
     * 信息表，及其字段
     */
    public static final String TABLE_NAME="message";
    public static final String COLUMN_CONTENT="content";
    public static final String COLUMN_DATE="date";
    /**
     * 时间字段的格式
     */
    public static final String DATE_FORMAT="YYYY-MM-DD";
    /**
     * 时间字段的降序，采用date函数比较
     */
    public static final String ORDER_BY="date("+COLUMN_DATE+") desc";



    /**
     * ContentProvider的authorities
     */
    public static final  String AUTHORITY="com.xingen.sqlitepractice.db.DataContentProvider";
    /**
     * Scheme
     */
    public static final String SCHEME="content";
    /**
     *  ContentProvider的URI
     */
    public static final Uri CONTENT_URI=Uri.parse(SCHEME+"://"+AUTHORITY);
    /**
     * Message表的URI
     */
    public static final Uri MESSAGE_URI=Uri.withAppendedPath(CONTENT_URI,TABLE_NAME);
}
