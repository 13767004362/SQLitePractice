package com.xingen.sqlitepractice.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.xingen.sqlitepractice.R;
import com.xingen.sqlitepractice.adapter.SelectCityAdapter;
import com.xingen.sqlitepractice.adapter.SelectProvinceAdapter;
import com.xingen.sqlitepractice.base.BaseApplication;
import com.xingen.sqlitepractice.orm.DAO;
import com.xingen.sqlitepractice.orm.WriterDBUtils;
import com.xingen.sqlitepractice.orm.city.City;
import com.xingen.sqlitepractice.orm.city.CityDao;
import com.xingen.sqlitepractice.orm.province.Province;
import com.xingen.sqlitepractice.orm.province.ProvinceDao;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ${新根} on 2017/5/11 0011.
 * blog: http://blog.csdn.net/hexingen
 */
public class CitySelectActivity extends AppCompatActivity implements SelectProvinceAdapter.Callbck {
    private ListView provicesListView, cityListView;
    private DAO<Province> provinceDAO;
    private  DAO<City> cityDAO;
    private CompositeSubscription compositeSubscription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcity);

        initConfig();
        initView();
        queryProvince();
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        this.compositeSubscription=new CompositeSubscription();
        this.provinceDAO=new ProvinceDao(BaseApplication.getAppContext());
        this.cityDAO=new CityDao(BaseApplication.getAppContext());
    }

    private SelectProvinceAdapter provinceAdapter;
    private SelectCityAdapter cityAdapter;

    /**
     * 初始化控件
     */
    private void initView() {
        provicesListView = (ListView)this.findViewById(R.id.selectcity_provices_listview);
        cityListView = (ListView) this.findViewById(R.id.selectcity_citys_listview);
        this.provinceAdapter=new SelectProvinceAdapter(this,this);
        this.cityAdapter=new SelectCityAdapter(this);
        this.provicesListView.setAdapter(this.provinceAdapter);
        this.cityListView.setAdapter(this.cityAdapter);

        this.provicesListView.setOnItemClickListener(provinceAdapter);
    }

    /**
     * 根据省份的pid查寻到对应的城市
     * @param pid
     */
    @Override
    public void queryCitys(final int pid) {
           Subscription subscription=Observable.create(new Observable.OnSubscribe<List<City>>(){
               @Override
               public void call(Subscriber<? super List<City>> subscriber) {
                      List<City> list=cityDAO.queryAction(WriterDBUtils.COLUMN_PID+"=?",new String[]{String.valueOf(pid)});
                      subscriber.onNext(list);
               }
           }).subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Action1<List<City>>() {
               @Override
               public void call(List<City> cities) {
                    cityAdapter.addData(cities);
               }
           });
        this.compositeSubscription.add(subscription);
    }

    /**
     * 查询省份信息
     */
    public void queryProvince(){
       Subscription subscription= Observable.create(new Observable.OnSubscribe<List<Province>>() {
            @Override
            public void call(Subscriber<? super List<Province>> subscriber) {
                //执行查询操作
                List<Province> list= provinceDAO.queryAll();
                subscriber.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//订阅者执行的线程,UI线程
                .subscribe(new Action1<List<Province>>() {
            @Override
            public void call(List<Province> provinces) {
                //更新UI
                provinceAdapter.addData(provinces);
            }
        });
        this.compositeSubscription.add(subscription);
    }

    @Override
    protected void onDestroy() {
        this.compositeSubscription.clear();
        super.onDestroy();
    }
}
