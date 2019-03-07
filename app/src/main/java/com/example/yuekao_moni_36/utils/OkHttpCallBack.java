package com.example.yuekao_moni_36.utils;

import android.os.Handler;
import android.os.Message;
import android.telecom.Call;


import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;


public class OkHttpCallBack implements Callback {

    private OkHttpDataListener okHttpDataListener;

    public OkHttpCallBack(OkHttpDataListener okHttpDataListener) {
        this.okHttpDataListener = okHttpDataListener;
    }




    @Override
    public void onFailure(okhttp3.Call call, IOException e) {

    }

    @Override
    public void onResponse(okhttp3.Call call, Response response) throws IOException {
        final String string = response.body().string();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Gson gson =new Gson();
                DataLogin dataLogin = gson.fromJson(string, DataLogin.class);
                okHttpDataListener.success(dataLogin.getOb());
            }
        });

    }
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

    };
}
