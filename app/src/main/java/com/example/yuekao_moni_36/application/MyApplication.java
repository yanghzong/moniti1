package com.example.yuekao_moni_36.application;

import android.app.Application;
import android.content.Context;

import com.example.yuekao_moni_36.utils.OkHttpUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class MyApplication extends Application {
    public static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
        Fresco.initialize(this);

        sContext=this;
        initHttpHeader();

    }

    private void initHttpHeader() {
        OkHttpUtils.init();
    }
}
