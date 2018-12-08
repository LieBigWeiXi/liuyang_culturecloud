package com.example.dell.liuyang_culturecloud.Activity.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.liuyang_culturecloud.Activity.Bean.WenHuaPlaceBean;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenHuaYiChanBean;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/12/4.
 */

public class PlaceGridViewAdpater extends BaseAdapter {

    private Context        mContext;
    private LayoutInflater layoutInflater;
    private List<WenHuaPlaceBean.Data> mdataList = new ArrayList<>();

    public PlaceGridViewAdpater(Context context) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        mdataList = new ArrayList<WenHuaPlaceBean.Data>();//初始化集合
    }

    @Override
    public int getCount() {
        return mdataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mdataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        WenHuaPlaceBean.Data wenHuaPlace = mdataList.get(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){//无缓存加载
            convertView = layoutInflater.inflate(R.layout.place_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView_name = (TextView) convertView.findViewById(R.id.title);
            viewHolder.mImageView_cover = (ImageView)convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);//将viewHolder存储在view中
        }else{//调用缓存view
            viewHolder = (ViewHolder)convertView.getTag();
        }
//        Log.d("Adapter", "getView: "+ String.valueOf(position)+wenHuaPlace.getName()+wenHuaPlace.getCover());
        WenHuaPlaceBean.Data whycBean = mdataList.get(position);

        Picasso.with(mContext)
                .load(NetworkInfo.IP_ADDRESS+"/media/"+whycBean.getCover())
                .into(viewHolder.mImageView_cover);
        viewHolder.mTextView_name.setText(whycBean.getName());
        return convertView;
    }
    public class ViewHolder{
        TextView  mTextView_name;
        ImageView mImageView_cover;
    }


    public void setList(List<WenHuaPlaceBean.Data> WHYCList) {
        this.mdataList = WHYCList;
    }

    public void clear() {
        mdataList.clear();
        notifyDataSetChanged();
    }
}
