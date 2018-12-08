package com.example.culturecloud.Bean;

import com.example.culturecloud.StaticResources.NetworkInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/12/4.
 */

public class WenHuaYiChanBean {
    public int  total;

    public List<WHYC> getRows() {
        return rows;
    }
    public List<WHYC> rows = new ArrayList<>();
    public static class WHYC implements Serializable{
        private int heritage_type;//所属分类
        private String name;
        private String cover;
        private String absolute_url = NetworkInfo.ip_address+"/media/"+getCover();

        public String getAbsolute_url() {
            return absolute_url;
        }

        private String level;
        private String type;
        private String info;
        private String url;
        private String release_time;

        public int getHeritage_type() {
            return heritage_type;
        }

        public void setHeritage_type(int heritage_type) {
            this.heritage_type = heritage_type;
        }

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

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
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

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }
    }

}
