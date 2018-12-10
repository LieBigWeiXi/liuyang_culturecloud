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
        String []names={"文化市场综合执法局","文物管理局","体育运动管理局","文化馆","图书馆",
                "博物馆","花鼓戏剧团", "欧阳予倩大剧院","体育科","非遗馆"};
        String []address={"浏阳市广电大楼15楼","浏阳市行政中心附二栋二楼","浏阳市车站路131号",
                "湖南省浏阳市解放路98号","湖南省浏阳市解放路116","湖南省浏阳县城关镇圭斋路72号",
                "解放路69号","浏阳市浏阳河广场予倩路9号","浏阳市行政中心附二栋二楼","湖南省浏阳市解放路98号"};
        String []phones={"13875923000","13874905046","15111308950","13755189498",
                "13755189498","15111144372","13469070421","15874953391","13873189400","13874953433"};
        for(int i = 0;i < 10;i++){
            ContactBean contact = new ContactBean();
            contact.setName(names[i]);
            contact.setPhone(phones[i]);
            contact.setAddress(address[i]);
            contactList.add(contact);
        }
    }
}
