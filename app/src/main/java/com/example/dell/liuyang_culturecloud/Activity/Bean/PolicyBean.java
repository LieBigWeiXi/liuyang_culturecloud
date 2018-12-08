package com.example.culturecloud.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/12/3.
 */

public class PolicyBean {
    public int  total;
    public List<policy> rows = new ArrayList<>();
    public static class policy{
        private String name;
        @SerializedName("info")
        private String content;
        private String url;
        @SerializedName("release_time")
        private Date   mDate;

        public policy(String name, Date date) {
            this.name = name;
            mDate = date;

        }

        public policy(String name, String content, String url) {
            this.name = name;
            this.content = content;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public Date getDate() {
            return mDate;
        }

        public void setDate(Date date) {
            mDate = date;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
