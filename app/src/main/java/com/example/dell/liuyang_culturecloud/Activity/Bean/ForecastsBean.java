package com.example.dell.liuyang_culturecloud.Activity.Bean;

import java.util.List;

/**
 * Created by DELL on 2018/7/6.
 */

public class ForecastsBean {
    private String city;//城市名
    private String adcode;//区域编码
    private String province;//省份名
    private String reporttime;//数据发布时间

    private List<CastsBean> casts;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public List<CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<CastsBean> casts) {
        this.casts = casts;
    }
}
