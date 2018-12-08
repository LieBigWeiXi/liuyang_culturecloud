package com.example.culturecloud.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/12/5.
 */

public class MemoryBean {
    int            total;
    List<Pictures> rows = new ArrayList<>();
    Pictures       mPictures;

    public List<Pictures> getRows() {
        return rows;
    }

    public void setRows(List<Pictures> rows) {
        this.rows = rows;
    }

    public Pictures getPictures() {
        return mPictures;
    }

    public void setPictures(Pictures pictures) {
        mPictures = pictures;
    }

    public static class Pictures implements Serializable{
       String id;
       String name;
       String new_picture;
       String old_picture;
       String info;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNew_picture() {
            return new_picture;
        }

        public void setNew_picture(String new_picture) {
            this.new_picture = new_picture;
        }

        public String getOld_picture() {
            return old_picture;
        }

        public void setOld_picture(String old_picture) {
            this.old_picture = old_picture;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


}
