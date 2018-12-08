package com.example.culturecloud.Bean;

/**
 * Created by DELL on 2018/7/13.
 */

public class TypeBean {
    private String dt_name;
    private int id;
    private int dt_icon;
    public TypeBean(){

    }
    public TypeBean(int id, int dt_icon,String dt_name) {
        this.id = id;
        this.dt_icon = dt_icon;
        this.dt_name = dt_name;
    }

    public int getDt_icon() {
        return dt_icon;
    }

    public void setDt_icon(int dt_icon) {
        this.dt_icon = dt_icon;
    }

    public String getDt_name() {
        return dt_name;
    }

    public void setDt_name(String dt_name) {
        this.dt_name = dt_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
