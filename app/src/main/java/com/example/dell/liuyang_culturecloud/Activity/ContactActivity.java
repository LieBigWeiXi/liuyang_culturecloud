package com.example.dell.liuyang_culturecloud.Activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.dell.liuyang_culturecloud.Activity.Adapter.ContactAdapter;
import com.example.dell.liuyang_culturecloud.Activity.Bean.ContactBean;
import com.example.dell.liuyang_culturecloud.R;

import java.util.ArrayList;

public class ContactActivity extends BaseActivity {
    RecyclerView rv;
    private ArrayList<ContactBean> contactList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        findViewById(R.id.return_button).setBackgroundResource(R.mipmap.contact_return);
        initContactBeans();
        rv = (RecyclerView)findViewById(R.id.rv_contact_us);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        rv.setLayoutManager(layoutManager);
        ContactAdapter adapter = new ContactAdapter(contactList);
        rv.setAdapter(adapter);
    }
    public void initContactBeans(){
        String []names={"浏阳市文化体育广播电视局","浏阳市文化市场综合执法局","浏阳市文物管理局","浏阳市体育运动管理局","浏阳市文化馆","浏阳市图书馆",
                "浏阳市博物馆","浏阳市花鼓戏剧团", "浏阳市欧阳予倩大剧院"};
        String []address={"浏阳市行政中心附二栋","浏阳市广电大楼15楼","浏阳市指背冲甘家冲13号","浏阳市车站路131号",
                "浏阳市解放路98号","浏阳市解放路116号","浏阳市圭斋路72号",
                "浏阳市车站路11号","浏阳市予倩路9号"};
        String []phones={"0731-83612057","0731-83290076","0731-83611148","0731-83612845","0731-83611060",
                "0731-83611453","0731-83629445","0731-83612631","0731-83657378"};
        for(int i = 0;i < names.length;i++){
            ContactBean contact = new ContactBean();
            contact.setName(names[i]);
            contact.setPhone(phones[i]);
            contact.setAddress(address[i]);
            contactList.add(contact);
        }
    }
}
