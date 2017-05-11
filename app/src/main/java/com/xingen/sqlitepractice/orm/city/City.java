package com.xingen.sqlitepractice.orm.city;

/**
 * 作者：新根  on 2016/11/4.
 * <p/>
 * 博客链接：http://blog.csdn.net/hexingen
 * 用途：
 * 数据库中表对应的字段类，提供set与get方法
 */
public class City {
    private  int pid;
    private  String cname;
    private int cid;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
