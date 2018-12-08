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


import com.example.dell.liuyang_culturecloud.Activity.Bean.PolicyBean;
import com.example.dell.liuyang_culturecloud.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by DELL on 2018/12/3.
 */

public class PolicyAdapter extends ArrayAdapter<PolicyBean.policy> {

    private int resourceId;

    public PolicyAdapter(@NonNull Context context, int resource, List<PolicyBean.policy> policyBeanList) {
        super(context, resource,policyBeanList);
        resourceId = resource;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PolicyBean.policy policyBean = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){//无缓存加载
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.policy_title);
            viewHolder.date = (TextView)view.findViewById(R.id.policy_date);
            view.setTag(viewHolder);//将viewHolder存储在view中
        }else{//调用缓存view
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.title.setText(policyBean.getName());
        SimpleDateFormat df = new SimpleDateFormat("yyy年MM月dd日");
        viewHolder.date.setText(df.format(policyBean.getDate()));
        return view;
    }
    public class ViewHolder{
        TextView title;
        TextView date;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
