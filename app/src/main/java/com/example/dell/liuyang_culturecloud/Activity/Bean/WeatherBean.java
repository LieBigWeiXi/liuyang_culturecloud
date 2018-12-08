package com.example.culturecloud.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2018/7/6.
 */

public class WeatherBean implements Serializable{
    private int status;//返回状态 0：失败，1：成功
    private int count;//返回结果总数目
    private String info;//返回的状态信息
    private String infocode;//返回的状态说明，10000表正确
    private List<ForecastsBean> forecasts;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public List<ForecastsBean> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastsBean> forecasts) {
        this.forecasts = forecasts;
    }
}




