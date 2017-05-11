package com.xingen.sqlitepractice.orm.province;

/**
 * 作者：新根  on 2016/11/4.
 * <p/>
 * 博客链接：http://blog.csdn.net/hexingen
 *
 * 数据库中表对应的字段类，提供set与get方法
 *
 */
public class Province {
    private int pid;
    private String pname;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
