package com.xingen.sqlitepractice.activity;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.QueryObservable;
import com.squareup.sqlbrite.SqlBrite;
import com.xingen.sqlitepractice.R;
import com.xingen.sqlitepractice.adapter.MessageAdapter;
import com.xingen.sqlitepractice.base.BaseApplication;
import com.xingen.sqlitepractice.db.Data;
import com.xingen.sqlitepractice.orm.message.Message;
import com.xingen.sqlitepractice.orm.ValuesTransform;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ${新根} on 2017/5/9 0009.
 * blog: http://blog.csdn.net/hexingen
 * <p/>
 * RxJava + SQLBrite方式实现数据库的自动观察
 */
public class RxJavaSQLBriteActivity extends AppCompatActivity implements View.OnClickListener {
    private BriteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjavasqlbrite);
        this.db = BaseApplication.getAppContext().getSqlBriteProvider().getBriteDatabase();
        initView();
    }

    @Override
    protected void onResume() {
        excuteQuery();
        super.onResume();
    }

    private ListView listView;
    private MessageAdapter adapter;

    /**
     * 初始化控件
     */
    private void initView() {
        this.findViewById(R.id.rxjavasqlbrite_add_btn).setOnClickListener(this);
        this.listView = (ListView) this.findViewById(R.id.rxjavasqlbrite_listview);
        this.adapter = new MessageAdapter(this);
        this.listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rxjavasqlbrite_add_btn) {
            EditText content_editext = (EditText) this.findViewById(R.id.rxjavasqlbrite_content_edit);
            EditText date_editext = (EditText) this.findViewById(R.id.rxjavasqlbrite_date_edit);
            Message message = new Message();
            message.setContent(content_editext.getText().toString());
            message.setDate(date_editext.getText().toString());
            this.db.insert(Data.TABLE_NAME, ValuesTransform.transformContentValues(message));
        }
    }

    private Subscription suscription;

    /**
     * 长时间订阅查询
     */
    private void excuteQuery() {
        //Observable<SqlBrite.Query> observable
        QueryObservable observable = this.db.createQuery(Data.TABLE_NAME, "select * from message ORDER BY "+Data.ORDER_BY, null);
        suscription = observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<SqlBrite.Query>() {
            @Override
            public void call(SqlBrite.Query query) {
                Cursor cursor = query.run();
                loadDataToUI(cursor);
            }
        });
    }

    /**
     * 从Cursor获取数据加载到UI上
     *
     * @param cursor
     */
    public void loadDataToUI(Cursor cursor) {
        try {
            if (cursor != null && cursor.moveToFirst()) {
                List<Message> list = new ArrayList<>();
                do {
                    list.add(ValuesTransform.transformMessage(cursor));
                } while (cursor.moveToNext());
                this.adapter.addData(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onPause() {
        suscription.unsubscribe();
        super.onPause();
    }
}
