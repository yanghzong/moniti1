package com.example.yuekao_moni_36.mvp.model;

import android.os.Handler;

import com.example.yuekao_moni_36.utils.ICallBack;
import com.example.yuekao_moni_36.utils.OkHttpUtils;
import com.example.yuekao_moni_36.utils.UtilsUrl;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShowModel implements ShowModelInterface {
    private Handler handler=new Handler();
    @Override
    public void getLogin(String phone, String pwd, final ICallBack callBack, final Type type) {
        Map<String,String> map=new HashMap<>();
        map.put("phone",phone);
        map.put("pwd",pwd);
        OkHttpUtils.enqueuePost(UtilsUrl.loingUrl, map, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson=new Gson();
                final Object o = gson.fromJson(string, type);
                handler.post(new Runnable() {
                  @Override
                  public void run() {
                      callBack.successful(o);
                  }
              });
            }
        },type);
    }

    @Override
    public void getShopCar(final ICallBack callBack, final Type type) {
   OkHttpUtils.enqueuGet(UtilsUrl.shopCartUrl, new Callback() {
       @Override
       public void onFailure(Call call, final IOException e) {
           handler.post(new Runnable() {
               @Override
               public void run() {
                   callBack.failed(e);
               }
           });
       }

       @Override
       public void onResponse(Call call, Response response) throws IOException {
           String string = response.body().string();
           Gson gson=new Gson();
           final Object o = gson.fromJson(string, type);
           handler.post(new Runnable() {
               @Override
               public void run() {
                   callBack.successful(o);
               }
           });
       }
       },type);
    }

    @Override
    public void getShowProductModel(int commodityId, final ICallBack callBack, final Type type) {
        String  url=UtilsUrl.ShowProductUrl+"?commodityId="+commodityId;
        OkHttpUtils.enqueuGet(url, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson=new Gson();
                final Object o = gson.fromJson(string, type);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.successful(o);
                    }
                });
            }
        },type);

    }
}
