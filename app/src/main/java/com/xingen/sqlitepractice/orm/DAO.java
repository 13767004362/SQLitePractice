package com.xingen.sqlitepractice.orm;

import java.util.List;

/**
 * Created by ${新根} on 2017/5/7 0007.
 * blog: http://blog.csdn.net/hexingen
 */
public interface DAO<T> {
    List<T> queryAll();
    List<T>  queryAction(String selection,
                    String[] selectionArgs);
    void delite();
    void insert(T t);
    void update(T t);
}
