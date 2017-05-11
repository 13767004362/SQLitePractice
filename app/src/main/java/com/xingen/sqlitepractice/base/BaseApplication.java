package com.xingen.sqlitepractice.base;

import android.app.Application;

import com.xingen.sqlitepractice.orm.DAO;
import com.xingen.sqlitepractice.orm.message.Message;
import com.xingen.sqlitepractice.orm.message.MessageDAO;
import com.xingen.sqlitepractice.sqlbrite.SqlBriteProvider;

/**
 * Created by ${新根} on 2017/5/7 0007.
 * blog: http://blog.csdn.net/hexingen
 *
 * 客户端生命周期的共享信息存储
 */
public class BaseApplication extends Application {
    private static BaseApplication  appContext;
    private DAO<Message> messageDAO;
    private SqlBriteProvider sqlBriteProvider;
    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();

    }

    /**
     *初始化配置
     */
    private void initConfig() {
        appContext=this;
        this.messageDAO=new MessageDAO(this);
        this.sqlBriteProvider=new SqlBriteProvider(this);
    }

    public static BaseApplication getAppContext() {
        return appContext;
    }

    public DAO<Message> getMessageDAO() {
        return messageDAO;
    }

    public SqlBriteProvider getSqlBriteProvider() {
        return sqlBriteProvider;
    }
}
