package com.example.dell.liuyang_culturecloud.Activity.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenTiDongTaiBean;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class DynamicAdapter extends ArrayAdapter<WenTiDongTaiBean.Data> {

    private int resourceId;
    private Context context;

    public DynamicAdapter(@NonNull Context context, int resource, List<WenTiDongTaiBean.Data> dataList) {
        super(context, resource,dataList);
        resourceId = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WenTiDongTaiBean.Data data = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){//无缓存加载
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.policy_title);
            viewHolder.date = (TextView)view.findViewById(R.id.policy_date);
            viewHolder.iamge = (ImageView)view.findViewById(R.id.work_dynamic_listViewimage);
            view.setTag(viewHolder);//将viewHolder存储在view中
        }else{//调用缓存view
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.title.setText(data.getName());
        SimpleDateFormat df = new SimpleDateFormat("yyy年MM月dd日");
        viewHolder.date.setText(df.format(data.getRelease_time()));
        Picasso.with(context)
                .load(NetworkInfo.IP_ADDRESS+"/media/"+data.getCover())
                .into(viewHolder.iamge);
        return view;
    }
    public class ViewHolder{
        TextView title;
        TextView date;
        ImageView iamge;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
