package com.example.dell.liuyang_culturecloud.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.dell.liuyang_culturecloud.Activity.Bean.ContactBean;
import com.example.dell.liuyang_culturecloud.R;

import java.util.ArrayList;

/**
 * Created by DELL on 2018/11/30.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{
    private ArrayList<ContactBean> contactList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_phone,text_address,text_name;
        public ViewHolder(View view){
            super(view);
            text_phone = (TextView)view.findViewById(R.id.phone_text);
            text_address = (TextView)view.findViewById(R.id.address_text);
            text_name = (TextView)view.findViewById(R.id.name_text);
        }
    }
    public ContactAdapter(ArrayList<ContactBean> contactList){
        this.contactList = contactList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContactBean contactBean = contactList.get(position);
        holder.text_address.setText(contactBean.getAddress());
        holder.text_phone.setText(contactBean.getPhone());
        holder.text_name.setText(contactBean.getName());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_us_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}
