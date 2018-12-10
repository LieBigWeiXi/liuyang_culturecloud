package com.example.dell.liuyang_culturecloud.Activity.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dell.liuyang_culturecloud.Activity.Bean.LibBean;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/12/6.
 */

public class TuShuRecAdapter extends RecyclerView.Adapter<TuShuRecAdapter.ViewHolder>  {
    List<LibBean.Lib> dataList =  new ArrayList<>();
    Context         context;
    OnClickCallBack onClickCallBack;
    int             width;

    public TuShuRecAdapter(Context context, TuShuRecAdapter.OnClickCallBack onClickCallBack, int width){
        this.context = context;
        Log.d("ok","ok");
        this.onClickCallBack = onClickCallBack;
        this.width = width;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.cul_person_item);
           /* mTextView = (TextView)itemView.findViewById(R.id.title_name);
            mTextView.setSelected(true);*/
        }
    }
    public void onBindViewHolder(ViewHolder holder, final int position) {
        LibBean.Lib lib = dataList.get(position);
        Picasso.with(context)
                .load(NetworkInfo.IP_ADDRESS+"/media/"+lib.getCover())
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onClickCallBack.OnClink(position);
            }
        });
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tushu_pic_item,
                parent,false);
        //view.getLayoutParams().width =width/5;
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public int getItemCount(){
        Log.d("unload", String.valueOf(dataList.size()));
        return dataList.size();
    }

    public void addDataList(List<LibBean.Lib> dataList) {
        this.dataList.addAll(dataList);
    }

    public void setDataList(List<LibBean.Lib> dataList) {
        this.dataList = dataList;
    }

    public interface OnClickCallBack{
        public void OnClink(int id);
    }
    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }
}
