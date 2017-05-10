package com.xingen.sqlitepractice.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.xingen.sqlitepractice.R;
import com.xingen.sqlitepractice.adapter.MessageAdapter;
import com.xingen.sqlitepractice.db.Data;
import com.xingen.sqlitepractice.orm.Message;
import com.xingen.sqlitepractice.orm.ValuesTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${新根} on 2017/5/9 0009.
 * blog: http://blog.csdn.net/hexingen
 *
 * ContentProvider+SQLiteDatabase+CursorLoader实现自动观察数据源。
 */
public class AutomaticObservedActivity  extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,View.OnClickListener {
    /**
     * 加载器的标示
     */
    private  final int LOADER_ID=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automaticobserved);
        initView();
        //初始化loader
         getSupportLoaderManager().initLoader(LOADER_ID,null,this);
    }
    private ListView listview;
    private Button add_btn;
    private MessageAdapter adapter;
    /**
     * 初始化
     */
    private void initView() {
        this.listview=(ListView) this.findViewById(R.id.automaticobserved_message_lv);
        this.add_btn=(Button) this.findViewById(R.id.automaticobserved_add_btn);
        this.adapter=new MessageAdapter(this);
        this.listview.setAdapter(this.adapter);

        this.add_btn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        //销毁loader,释放资源
        getSupportLoaderManager().destroyLoader(LOADER_ID);
        super.onDestroy();
    }

    /**
     * 初始化加载器的回调
     * @param id
     * @param args
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id==LOADER_ID){
              return  new CursorLoader(this, Data.MESSAGE_URI,null,null,null,Data.ORDER_BY);
        }
        return null;
    }

    /**
     * 首次加载成功或者数据源发生改变时候进行回调
     * @param loader
     * @param cursor
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
      if(loader.getId()==LOADER_ID){
          if(cursor!=null&&cursor.moveToFirst()){//实时刷新数据到UI上
              List<Message> list=new ArrayList<>();
              do {
                  list.add(ValuesTransform.transformMessage(cursor));
              }while (cursor.moveToNext());
              this.adapter.addData(list);
          }
      }
    }

    /**
     * 加载器重置的回调
     * @param loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.automaticobserved_add_btn){
            EditText content_Edit= (EditText)this.findViewById(R.id.automaticobserved_content_edit);
            EditText date_Edit= (EditText)this.findViewById(R.id.automaticobserved_date_edit);
            Message message=new Message();
            message.setContent(content_Edit.getText().toString().trim());
            message.setDate(date_Edit.getText().toString().trim());
            getContentResolver().insert(Data.MESSAGE_URI,ValuesTransform.transformContentValues(message));
        }
    }
}
