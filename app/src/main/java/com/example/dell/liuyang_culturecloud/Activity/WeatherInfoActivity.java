package com.example.dell.liuyang_culturecloud.Activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.dell.liuyang_culturecloud.Activity.Adapter.WeatherAdapter;
import com.example.dell.liuyang_culturecloud.Activity.Bean.CastsBean;
import com.example.dell.liuyang_culturecloud.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherInfoActivity extends BaseActivity {

    WeatherAdapter weatherAdapter;
    List<CastsBean> castlist = new ArrayList<>();
    TextView tv_temp,tv_location,tv_info,tv_wind,tv_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_weather);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager manager = new GridLayoutManager(this,4);

        tv_temp = (TextView)findViewById(R.id.tv_temp);
        tv_location = (TextView)findViewById(R.id.tv_location);
        tv_info = (TextView)findViewById(R.id.tv_info);
        tv_wind = (TextView)findViewById(R.id.tv_wind);
        tv_date = (TextView)findViewById(R.id.tv_weather_date);

        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        castlist = (List<CastsBean>) getIntent().getSerializableExtra("weather_data");
        //Log.d("WeatherInfo", "onCreate:date "+castlist.get(0).getDate());
        weatherAdapter = new WeatherAdapter(castlist);
        recyclerView.setAdapter(weatherAdapter);

        tv_temp.setText(castlist.get(0).getDaytemp());
        tv_location.setText(MainActivity.city);
        tv_info.setText(castlist.get(0).getDayweather());
        tv_wind.setText("风向："+castlist.get(0).getDaywind()+"    |    "+"风力："+castlist.get(0).getDaypower());
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyy 年 MM 月 dd 日   EE");
        tv_date.setText(df.format(date));
    }
}
