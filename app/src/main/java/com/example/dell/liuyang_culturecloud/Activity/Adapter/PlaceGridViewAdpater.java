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

import com.example.dell.liuyang_culturecloud.Activity.Bean.WenHuaYiChanBean;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/12/4.
 */

public class GridViewAdpater extends BaseAdapter {

    private Context        mContext;
    private LayoutInflater layoutInflater;
    private List<WenHuaYiChanBean.Data> mWHYCList = new ArrayList<>();

    public GridViewAdpater(Context context) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        mWHYCList = new ArrayList<WenHuaYiChanBean.Data>();//初始化集合
    }

    @Override
    public int getCount() {
        return mWHYCList.size();
    }

    @Override
    public Object getItem(int position) {
        return mWHYCList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        WenHuaYiChanBean.Data wenHuaYiChan = mWHYCList.get(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){//无缓存加载
            convertView = layoutInflater.inflate(R.layout.hesitate_picture_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView_name = (TextView) convertView.findViewById(R.id.history_title);
            viewHolder.mImageView_cover = (ImageView)convertView.findViewById(R.id.history_img);
            convertView.setTag(viewHolder);//将viewHolder存储在view中
        }else{//调用缓存view
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Log.d("Adapter", "getView: "+ String.valueOf(position)+wenHuaYiChan.getName()+wenHuaYiChan.getCover());
        WenHuaYiChanBean.Data whycBean = mWHYCList.get(position);
        if(whycBean.getType()==2){
            viewHolder.mImageView_cover.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        Picasso.with(mContext)
                .load(NetworkInfo.IP_ADDRESS+"/media/"+whycBean.getCover())
                .into(viewHolder.mImageView_cover);
        viewHolder.mTextView_name.setText(wenHuaYiChan.getName());
        return convertView;
    }
    public class ViewHolder{
        TextView  mTextView_name;
        ImageView mImageView_cover;
    }


    public void setWHYCList(List<WenHuaYiChanBean.Data> WHYCList) {
        this.mWHYCList = WHYCList;
    }

    public void clear() {
        mWHYCList.clear();
        notifyDataSetChanged();
    }
}
