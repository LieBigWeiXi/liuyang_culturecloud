package com.example.dell.liuyang_culturecloud.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.liuyang_culturecloud.Activity.Bean.CastsBean;
import com.example.dell.liuyang_culturecloud.Activity.Bean.ForecastsBean;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WeatherBean;
import com.example.dell.liuyang_culturecloud.Activity.HttpRequest.doRequest;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    doRequest http_request;
    List<CastsBean> castslist = new ArrayList<>();
    WeatherBean weatherBean;
    public static String city = "浏阳市";
    TextView location_text,today_weather_tv,today_temp_tv,tomorrow_weather_tv,tomorrow_temp_tv;
    ImageView today_weather_ic,tomorrow_weather_ic;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson gson = new Gson();
            switch (msg.what){
                case 201://解析天气信息
                    String weather_data = (String)msg.obj;
                    weatherBean = gson.fromJson(weather_data, WeatherBean.class);
                    if(weatherBean.getStatus()==1){//返回状态成功
                        if(weatherBean.getCount()>0){//返回的结果大于0
                            for(ForecastsBean forecastsBean : weatherBean.getForecasts()){
                                if(forecastsBean.getCity().equals(city)){
                                    castslist.addAll(forecastsBean.getCasts());
                                    break;
                                }
                            }
                            showSimpleWeather(castslist);
                        }else{
                            Toast.makeText(MainActivity.this,"获取天气数据异常！",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"获取天气数据失败！",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView date_txt = (TextView)findViewById(R.id.date_txt);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyy年MM月dd日");
        date_txt.setText(df.format(date));

        //findViewById(R.id.weather_simple_layout).setOnClickListener(this);
        findViewById(R.id.wen_ti_chang_guan_img).setOnClickListener(this);
        findViewById(R.id.wen_hua_yi_chan_img).setOnClickListener(this);
        findViewById(R.id.wen_ti_dong_tai_img).setOnClickListener(this);
        findViewById(R.id.wen_hua_shi_chang_img).setOnClickListener(this);
        findViewById(R.id.wen_hua_liu_yang_img).setOnClickListener(this);
        findViewById(R.id.lian_xi_wo_men_img).setOnClickListener(this);
        findViewById(R.id.shu_xiang_liu_yang_img).setOnClickListener(this);
        findViewById(R.id.zheng_wu_fu_wu_img).setOnClickListener(this);
        location_text = (TextView) findViewById(R.id.location_txt);
        location_text.setText(city);
        http_request = doRequest.getInstance(getApplicationContext());
        //"长沙市","adcode":"430100" "长沙县","adcode":"430121"
        String url = "https://restapi.amap.com/v3/weather/weatherInfo?key="+ NetworkInfo.AMAP_API_KEY+
                "&city="+city+"&extensions=all";
        http_request.doGet(url,201,handler);
    }

    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
           /* case R.id.weather_simple_layout://天气
                intent = new Intent(this,WeatherInfoActivity.class);
                Log.d("WeatherInfo", "castslist.size= "+castslist.size());
                intent.putExtra("weather_data", (Serializable) castslist);
                startActivity(intent);
                break;*/
            case R.id.wen_ti_chang_guan_img://文体场馆
                intent = new Intent(this,WenTiChangGuan.class);
                startActivity(intent);
                break;
            case R.id.wen_hua_yi_chan_img://文化遗产
                intent = new Intent(this,WenHuaYiChan.class);
                startActivity(intent);
                break;
            case R.id.wen_ti_dong_tai_img://文体动态
                intent = new Intent(this,WenTiDongTai.class);
                startActivity(intent);
                break;
            case R.id.wen_hua_shi_chang_img://文化市场
                intent = new Intent(this,WenHuaShiChang.class);
                startActivity(intent);
                break;
            case R.id.wen_hua_liu_yang_img://文化浏阳
                intent = new Intent(this,WenHuaLiuYang.class);
                startActivity(intent);
                break;
            case R.id.lian_xi_wo_men_img://联系我们
                intent = new Intent(this,ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.shu_xiang_liu_yang_img://书香浏阳
                intent = new Intent(this,ShuXiangLiuYang.class);
                startActivity(intent);
                break;
            case R.id.zheng_wu_fu_wu_img://政务服务
                intent = new Intent(this,ZhengWuFuWu.class);
                //http://www.liuyang.gov.cn/mlly/mobile/index.html#p=4美丽浏阳url
                intent.putExtra("url","http://www.liuyang.gov.cn/");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void showSimpleWeather(List<CastsBean> castsBeanList){
        today_weather_tv = (TextView)findViewById(R.id.today_info);
        today_temp_tv = (TextView)findViewById(R.id.today_temp);
        tomorrow_weather_tv = (TextView)findViewById(R.id.tomorrow_info);
        tomorrow_temp_tv = (TextView)findViewById(R.id.tomorrow_temp);
        String today_temp,tomorrow_temp;
        today_temp = castsBeanList.get(0).getDaytemp()+"/"+ castsBeanList.get(0).getNighttemp()+"℃";
        tomorrow_temp = castsBeanList.get(1).getDaytemp()+"/"+ castsBeanList.get(1).getNighttemp()+"℃";
        today_weather_tv.setText(castsBeanList.get(0).getDayweather());
        today_temp_tv.setText(today_temp);
        tomorrow_weather_tv.setText(castsBeanList.get(1).getDayweather());
        tomorrow_temp_tv.setText(tomorrow_temp);

        //利用天气描述匹配天气图标
        today_weather_ic = (ImageView)findViewById(R.id.today_weatherIcon);
        tomorrow_weather_ic = (ImageView)findViewById(R.id.tomorrow_weatherIcon);
        String today_weatherInfo = castsBeanList.get(0).getDayweather();
        String tomorrow_weatherInfo = castsBeanList.get(1).getDayweather();
        setWeatherIcon(today_weatherInfo,today_weather_ic);
        setWeatherIcon(tomorrow_weatherInfo,tomorrow_weather_ic);
    }
    public static void setWeatherIcon(String weatherInfo,ImageView imageView){
        if(weatherInfo.contains("晴")&&weatherInfo.contains("云")){
            imageView.setImageResource(R.drawable.sun_cloud);
        }else if(weatherInfo.contains("雨")&&weatherInfo.contains("雪")){
            imageView.setImageResource(R.drawable.rain_snow);
        }else if(weatherInfo.contains("阵雨")){
            imageView.setImageResource(R.drawable.rains);
        }else if(weatherInfo.contains("云")||weatherInfo.contains("阴")){
            imageView.setImageResource(R.drawable.overcast);
        }else if(weatherInfo.contains("尘")){
            imageView.setImageResource(R.drawable.storm);
        }else if(weatherInfo.contains("晴")) {
            imageView.setImageResource(R.drawable.sun);
        } else if(weatherInfo.contains("雨")) {
            imageView.setImageResource(R.drawable.rain);
        }else if(weatherInfo.contains("雾")) {
            imageView.setImageResource(R.drawable.mist);
        } else if(weatherInfo.contains("雪")) {
            imageView.setImageResource(R.drawable.snow);
        }
    }
}
