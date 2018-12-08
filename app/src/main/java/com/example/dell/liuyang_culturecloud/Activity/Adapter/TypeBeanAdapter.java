package com.example.dell.liuyang_culturecloud.Activity.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.dell.liuyang_culturecloud.Activity.Bean.TypeBean;
import com.example.dell.liuyang_culturecloud.R;

import java.util.List;

public class TypeBeanAdapter extends ArrayAdapter<TypeBean> {

    private int resourceId;
    private int selected_id = -1;
    public TypeBeanAdapter(@NonNull Context context, int resource, List<TypeBean> typeBeanList) {
        super(context, resource,typeBeanList);
        resourceId = resource;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TypeBean typeBean = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){//无缓存加载
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.type_name = (TextView) view.findViewById(R.id.type_name);
            viewHolder.type_icon = (ImageView)view.findViewById(R.id.type_icon);
            viewHolder.bkg_view = view.findViewById(R.id.left_type_item);
            view.setTag(viewHolder);//将viewHolder存储在view中
        }else{//调用缓存view
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.type_name.setText(typeBean.getDt_name());
        viewHolder.type_icon.setImageResource(typeBean.getDt_icon());
        if(typeBean.getId()==selected_id){
            viewHolder.bkg_view.setBackgroundResource(R.drawable.chg_bkg);
        }else {
            viewHolder.bkg_view.setBackgroundResource(0);
        }
        return view;
    }
    public class ViewHolder{
        TextView  type_name;
        ImageView type_icon;
        View      bkg_view;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }

    public void setSelected_id(int selected_id) {
        this.selected_id = selected_id;
    }
}
