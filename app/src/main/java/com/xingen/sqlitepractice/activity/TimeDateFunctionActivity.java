package com.xingen.sqlitepractice.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.xingen.sqlitepractice.R;
import com.xingen.sqlitepractice.adapter.MessageAdapter;
import com.xingen.sqlitepractice.base.BaseApplication;
import com.xingen.sqlitepractice.db.Data;
import com.xingen.sqlitepractice.orm.message.Message;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by ${新根} on 2017/5/9 0009.
 * blog: http://blog.csdn.net/hexingen
 *
 * SQLite的日期和时间函数界面
 */

public class TimeDateFunctionActivity extends AppCompatActivity implements View.OnClickListener{
    private CompositeSubscription compositeSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timedatefunction);
        this.compositeSubscription=new CompositeSubscription();
        initView();
    }
    private ListView lv;
    private MessageAdapter adapter;
    private EditText content_edit,date_edit;
    /**
     * 初始化控件
     */
    private void initView() {

        this.lv=(ListView) this.findViewById(R.id.timedatefuction_listview);
        this.adapter=new MessageAdapter(this);
        this.lv.setAdapter(this.adapter);

        this.content_edit=(EditText) this.findViewById(R.id.timedatefuction_content_edit);
        this.date_edit=(EditText) this.findViewById(R.id.timedatefuction_date_edit);

        this.findViewById(R.id.timedatefuction_action_query_btn).setOnClickListener(this);
        this.findViewById(R.id.timedatefuction_all_query_btn).setOnClickListener(this);
        this.findViewById(R.id.timedatefuction_add_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.timedatefuction_all_query_btn://全部信息
                queryAll();

                break;
            case R.id.timedatefuction_action_query_btn://5月信息
                queryAction();
                break;

            case R.id.timedatefuction_add_btn: //新增信息
                if(TextUtils.isEmpty(content_edit.getText().toString())||TextUtils.isEmpty(date_edit.getText().toString())){
                    return ;
                }
                Message message=new Message();
                message.setContent(content_edit.getText().toString());
                message.setDate(date_edit.getText().toString());
                insert(message);
                break;
        }
    }

    /**
     * 获取全部信息
     */
    public void queryAll(){
        Observable<List<Message>> observable= Observable.create(new Observable.OnSubscribe<List<Message>>() {
            @Override
            public void call(Subscriber<? super List<Message>> subscriber) {
                List<Message> list= BaseApplication.getAppContext().getMessageDAO().queryAll();
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        });
        excuteObservable(observable);
    }

    /**
     * 指定条件的信息
     */
    public void queryAction(){
        Observable<List<Message>> observable=Observable.create(new Observable.OnSubscribe<List<Message>>() {
            @Override
            public void call(Subscriber<? super List<Message>> subscriber) {
                List<Message> list= BaseApplication.getAppContext().getMessageDAO().queryAction(createSelectAction(),new String[]{"05"});
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        });
        excuteObservable(observable);
    }

    /**
     * 创建查询条件
     * @return
     */
    public String createSelectAction(){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("strftime('%m',");
        stringBuffer.append(Data.COLUMN_DATE);
        stringBuffer.append(")=?");
        return  stringBuffer.toString();
    }

    /**
     * 插入信息
     * @param message
     */
    public void insert(final Message message){
      Observable<Boolean> observable=  Observable.create(new Observable.OnSubscribe<Boolean>() {
          @Override
          public void call(Subscriber<? super Boolean> subscriber) {
              BaseApplication.getAppContext().getMessageDAO().insert(message);
              subscriber.onNext(true);
              subscriber.onCompleted();
          }
      });

       Observable<List<Message>> observable1= observable.flatMap(new Func1<Boolean, Observable< List<Message> >>() {
           @Override
           public Observable< List<Message> > call(Boolean aBoolean) {
               List<Message> list= BaseApplication.getAppContext().getMessageDAO().queryAll();
               return      Observable.just(list);
           }
       });
        excuteObservable(observable1);
    }

    /**
     * 执行任务
     * @param observable
     */
    public void excuteObservable(Observable<List<Message>> observable){
        this.compositeSubscription.add(  observable.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Message>>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(List<Message> list) {
                adapter.addData(list);
            }
        }));
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.compositeSubscription.unsubscribe();
    }
}
