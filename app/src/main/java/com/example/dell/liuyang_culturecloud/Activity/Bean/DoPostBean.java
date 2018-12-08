package com.example.culturecloud.Bean;

/**
 * Created by DELL on 2018/12/4.
 */

public class DoPostBean {
    int page = 1;
    int rows = 8;
    int type = 0;

    public DoPostBean() {
    }

    public DoPostBean(int page, int rows, int type) {
        this.page = page;
        this.rows = rows;
        this.type = type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
