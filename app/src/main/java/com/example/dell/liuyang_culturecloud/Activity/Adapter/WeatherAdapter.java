package com.example.dell.liuyang_culturecloud.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dell.liuyang_culturecloud.Activity.Bean.CastsBean;
import com.example.dell.liuyang_culturecloud.Activity.MainActivity;
import com.example.dell.liuyang_culturecloud.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/7/9.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>{

    private List<CastsBean> casts;

    public WeatherAdapter(List<CastsBean> casts){
        this.casts = casts;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView weather_week,weather_date,weather_temp,weather_info;
        ImageView weather_icon;



        public ViewHolder(View view){
            super(view);
            weather_week = (TextView)view.findViewById(R.id.weather_week);
            weather_date = (TextView)view.findViewById(R.id.weather_date);
            weather_icon = (ImageView)view.findViewById(R.id.weather_icon);
            weather_temp = (TextView)view.findViewById(R.id.weather_temp);
            weather_info = (TextView)view.findViewById(R.id.weather_info);

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder holder, int position) {
        CastsBean castsBean = casts.get(position);
        switch (casts.get(position).getWeek()){
            case "1":holder.weather_week.setText("星期一");break;
            case "2":holder.weather_week.setText("星期二");break;
            case "3":holder.weather_week.setText("星期三");break;
            case "4":holder.weather_week.setText("星期四");break;
            case "5":holder.weather_week.setText("星期五");break;
            case "6":holder.weather_week.setText("星期六");break;
            case "7":holder.weather_week.setText("星期日");break;
        }
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd");
        if(df.format(date).equals(castsBean.getDate())){
            castsBean.setDate("今日");
            holder.weather_date.setText("今日");
        }
        holder.weather_date.setText(castsBean.getDate());
        MainActivity.setWeatherIcon(castsBean.getDayweather(),holder.weather_icon);
        String weatherInfo = "白天: "+ castsBean.getDayweather()+"夜晚: "+ castsBean.getNightweather();
        holder.weather_info.setText(castsBean.getDayweather());
        String temp = castsBean.getDaytemp()+" / "+castsBean.getNighttemp()+" ℃";
        holder.weather_temp.setText(temp);
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }
}
