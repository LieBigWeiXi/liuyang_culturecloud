package com.example.dell.liuyang_culturecloud.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.liuyang_culturecloud.Activity.Bean.MemoryBean;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Atlas on 2017/12/3.
 */
//图片适配器
public class MemoryAdapter extends BaseAdapter {
    private List<MemoryBean.Pictures> arrayList;
    private LayoutInflater            layoutInflater;
    private Context                   context;

    public MemoryAdapter(Context context) {
        this.context=context;
        layoutInflater = LayoutInflater.from(context);
        arrayList = new ArrayList<MemoryBean.Pictures>();//初始化集合
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = layoutInflater.inflate(R.layout.picture_memory_item, parent, false);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.textView=(TextView)convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        }else {
            holder = ( Holder) convertView.getTag();
        }
        MemoryBean.Pictures picturesBean = arrayList.get(position);
        Picasso.with(context)
                .load(NetworkInfo.IP_ADDRESS+"/media/"+picturesBean.getOld_picture())
                .into(holder.image);
        holder.textView.setText(picturesBean.getName());
        return  convertView;
    }

    class Holder{
        ImageView image;
        TextView  textView;
    }

    public void addArrayList(List<MemoryBean.Pictures> arrayList) {
        this.arrayList.addAll(arrayList);
    }

    public void setArrayList(List<MemoryBean.Pictures> arrayList) {
        this.arrayList = arrayList;
    }

    public void clear() {
        arrayList.clear();
        notifyDataSetChanged();
    }
}
