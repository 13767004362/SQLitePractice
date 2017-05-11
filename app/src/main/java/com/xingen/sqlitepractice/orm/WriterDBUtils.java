package com.xingen.sqlitepractice.orm;

import android.content.Context;

import com.xingen.sqlitepractice.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ${新根} on 2017/5/11 0011.
 * blog: http://blog.csdn.net/hexingen
 *
 * 拷贝外部的db文件到数据库中。
 */
public class WriterDBUtils {
    /**
     * 从raw文件下city.db复写到数据库中。
     * <p/>
     * 数据库文件会存放在/data/data/<package name>/databases/目录下。
     */
    public static final String CITYDB_NAME = "city.db";
    public static final String TABLE_PROVICE = "m_province";
    public static final String TABLE_CITY = "m_city";
    public static final String COLUMN_PID = "pid";
    public static  final String COLUMN_CNAME = "cname";
    public static final String COLUMN_PNAME = "pname";

    /**
     *
     * @param context
     */
    public static void copyDBFromRaw(Context context) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("/data/data/");
            stringBuffer.append(context.getPackageName());
            stringBuffer.append("/databases");
            File dir=new File(stringBuffer.toString());
            if(!dir.exists()){//防止databases文件夹不存在，不然，会报错
                dir.mkdirs();
            }
            stringBuffer.append("/");
            stringBuffer.append(CITYDB_NAME);
            File file = new File(stringBuffer.toString());
            if (file == null || !file.exists()) {//数据库不存在，则进行拷贝数据库的操作。
                inputStream = context.getResources().openRawResource(R.raw.city);
                outputStream = new FileOutputStream(file.getAbsolutePath());
                byte[] b = new byte[1024];
                int length;
                while ((length = inputStream.read(b)) > 0) {
                    outputStream.write(b, 0, length);
                }
                //写完后刷新
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {//关闭流，释放资源
                    inputStream.close();
                }
                if(outputStream!=null){
                    outputStream.close();
                }
            } catch (Exception e) {
                    e.printStackTrace();
            }

        }
    }
}
