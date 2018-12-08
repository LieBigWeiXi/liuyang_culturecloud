package com.example.culturecloud.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/12/5.
 */

public class WenHuaShiChangBean {
    int        total;
    List<WHSC> rows = new ArrayList<>();
    public static class WHSC{
        String name;
        String cover;
        int type;
        String info;
        String url;
        Date release_time;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Date getRelease_time() {
            return release_time;
        }

        public void setRelease_time(Date release_time) {
            this.release_time = release_time;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<WHSC> getRows() {
        return rows;
    }

    public void setRows(List<WHSC> rows) {
        this.rows = rows;
    }
}
