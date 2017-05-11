package com.xingen.sqlitepractice.service;

import android.app.IntentService;
import android.content.Intent;

import com.xingen.sqlitepractice.base.BaseApplication;
import com.xingen.sqlitepractice.orm.WriterDBUtils;

/**
 * Created by ${新根} on 2017/5/11 0011.
 * blog: http://blog.csdn.net/hexingen
 */
public class WriteCityDBIntentService extends IntentService {
    public static final String TAG=WriteCityDBIntentService.class.getSimpleName();
    public WriteCityDBIntentService() {
        super(TAG);
    }
    /**
     *  异步执行，不在主线程执行，执行完后自动停止Service。
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        WriterDBUtils.copyDBFromRaw(BaseApplication.getAppContext());
    }
}
