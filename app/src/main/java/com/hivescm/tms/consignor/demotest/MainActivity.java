package com.hivescm.tms.consignor.demotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private TitleView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.tv_title);
        title.addItemView("贸易战");
        title.addItemView("特朗普");
        title.addItemView("X二狗");
        title.setSelect(0);
        title.setOnClickListener(v->{
            title.setSelect(0);
        });
    }
}
