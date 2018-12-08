package com.example.culturecloud.Adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.culturecloud.Bean.WenHuaShiChangBean;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/12/5.
 */

public class MarketRcyAdapter extends RecyclerView.Adapter<MarketRcyAdapter.ViewHolder>{
    public List<WenHuaShiChangBean.WHSC> WHSC_DataList =  new ArrayList<>();
    Context         context;
    Handler         handler;
    OnClickCallBack onClickCallBack;
    private int width;

    public MarketRcyAdapter(Context context, Handler handler, OnClickCallBack onClickCallBack, int width) {
        this.context = context;
        this.handler = handler;
        this.onClickCallBack = onClickCallBack;
        this.width = width;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView  mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.cul_person_item);
            mTextView = (TextView)itemView.findViewById(R.id.title_name);
            mTextView.setSelected(true);
        }
    }
    public void onBindViewHolder(MarketRcyAdapter.ViewHolder holder,final int position) {
        WenHuaShiChangBean.WHSC data = WHSC_DataList.get(position);

        Picasso.with(context)
                .load(NetworkInfo.NEW_IP_ADDRESS+"/media/"+data.getCover())
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onClickCallBack.OnClink(position);
            }
        });
        holder.mTextView.setText(data.getName());
        Log.d("Adapter", "onBindViewHolder: name="+data.getName());

    }
    @Override
    public MarketRcyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cul_person_item,
                parent,false);
        view.getLayoutParams().width =width/6;
        MarketRcyAdapter.ViewHolder holder = new MarketRcyAdapter.ViewHolder(view);
        return holder;
    }

    public int getItemCount(){
        return WHSC_DataList.size();
    }

    public void addCulDataList(List<WenHuaShiChangBean.WHSC> newsDataList) {
        this.WHSC_DataList.addAll(newsDataList);
    }

    public void setWHSC_DataList(List<WenHuaShiChangBean.WHSC> WHSC_DataList) {
        this.WHSC_DataList = WHSC_DataList;
    }

    public interface OnClickCallBack{
        public void OnClink(int id);
    }
    public void clear() {
        WHSC_DataList.clear();
        notifyDataSetChanged();
    }
}
