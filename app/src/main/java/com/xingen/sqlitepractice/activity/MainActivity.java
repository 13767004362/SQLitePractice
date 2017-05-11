package com.xingen.sqlitepractice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xingen.sqlitepractice.R;
import com.xingen.sqlitepractice.service.WriteCityDBIntentService;

/**
 * Created by ${新根} on 2017/5/9 0009.
 * blog: http://blog.csdn.net/hexingen
 */
public class MainActivity  extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        startWriteDB();
    }

    /**
     *  开启后台服务，拷贝city.db
     */
    private void startWriteDB() {
        Intent intent=new Intent(this, WriteCityDBIntentService.class);
        startService(intent);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        this.findViewById(R.id.main_cp_btn).setOnClickListener(this);
        this.findViewById(R.id.main_rxjava_btn).setOnClickListener(this);
        this.findViewById(R.id.main_timefuction_btn).setOnClickListener(this);
        this.findViewById(R.id.main_writedb_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       Class<?> mclass=null;
        switch (v.getId()){
            case R.id.main_cp_btn:
                  mclass=AutomaticObservedActivity.class;
                break;
            case R.id.main_rxjava_btn:
                mclass=RxJavaSQLBriteActivity.class;
                break;
            case R.id.main_timefuction_btn:
                mclass=TimeDateFunctionActivity.class;
                break;
            case R.id.main_writedb_btn:
                mclass=CitySelectActivity.class;
                break;
        }
        Intent intent=new Intent(this,mclass);
        startActivity(intent);
    }
}
