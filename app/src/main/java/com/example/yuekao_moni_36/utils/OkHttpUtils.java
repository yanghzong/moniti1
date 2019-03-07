package com.example.yuekao_moni_36.utils;

import android.os.Handler;
import android.util.Log;


import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class OkHttpUtils {
    private static Gson gson=new Gson();
    private static final String MEDIA_TYPE="application/json; charset=utf_8";
    private static final String METHOD_GET="GET";
    private static final String METHOD_POST="POST";
    private static final String METHOD_PUT="PUT";
    private static final String METHOD_DELETE="DELETE";
    private static OkHttpClient okHttpClient;
    private Handler handler=new Handler();
    private OkHttpUtils(){

    }

    public static void init(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.readTimeout(3000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(3000,TimeUnit.MILLISECONDS);
        builder.connectTimeout(3000,TimeUnit.MILLISECONDS);
        builder.addInterceptor(new OKHeaderInterceptor());
        //builder.addInterceptor(new OkLogInterceptor());
        builder.addInterceptor(interceptor);
        okHttpClient=builder.build();
    }
    private static Request createRequest(String url, String method, BaseRequest baseRequest){
        RequestBody requestBody=null;
        if (baseRequest!=null){
            String bodyJson=gson.toJson(baseRequest);
            MediaType JSON=MediaType.parse(MEDIA_TYPE);
            requestBody = RequestBody.create(JSON,bodyJson);
        }
        Request.Builder builder=new Request.Builder().url(url);
        Request request=null;
        switch (method){
            case METHOD_GET:
                request=builder.get().build();
                break;
            case METHOD_POST:
                request=builder.post(requestBody).build();
                break;
            case METHOD_PUT:
                if (requestBody!=null){
                    request=builder.put(requestBody).build();
                }
                break;
            case METHOD_DELETE:
                if (requestBody!=null){
                    request=builder.delete(requestBody).build();
                }else {
                    request=builder.delete().build();
                }
                break;
        }
        return request;
    }
    public static Request createRequestPost(String url, String method, Map<String,String> map){
        FormBody.Builder form=new FormBody.Builder();

        Set set=map.keySet();
        Iterator iterator = set.iterator();
        String logString="";
        while (iterator.hasNext()){
            String next= (String) iterator.next();
            form.add(next,map.get(next));
            logString+=next+":"+map.get(next)+"/n";
        }


        FormBody formBody = form.build();
        Request.Builder builder=new Request.Builder().url(url);
        Request request=null;
        switch (method){
            case METHOD_GET:
                request=builder.get().build();
                break;
            case METHOD_POST:
                request=builder.post(formBody).build();
                break;
            case METHOD_PUT:
                if (formBody!=null){
                    request=builder.put(formBody).build();
                }
                break;
            case METHOD_DELETE:
                if (formBody!=null){
                    request=builder.delete(formBody).build();
                }else {
                    request=builder.delete().build();
                }
                break;
        }
        return request;
    }
    //异步post方法
    public static void enqueuePost(String url, Map map, Callback callback, Type type){
        Request request=createRequestPost(url,METHOD_POST,map);
        Call call=okHttpClient.newCall(request);
        call.enqueue(callback);
    }
    //异步put方法
    public static void enqueuPut(String url, Map map, Callback callback, Type type){
        Request request=createRequestPost(url,METHOD_PUT,map);
        Call call=okHttpClient.newCall(request);
        call.enqueue(callback);
    }
    //同步post方法
    public static Response executePost(String url, BaseRequest baseRequest) throws IOException {
        Request request=createRequest(url,METHOD_POST,baseRequest);
        Call call=okHttpClient.newCall(request);
        Response response=call.execute();
        return response;
    }
    //异步get方法
    public static void enqueuGet(String url, Callback callback, Type type){
        Request request=createRequest(url,METHOD_GET,null);
        Call call=okHttpClient.newCall(request);
        call.enqueue(callback);
    }
    //异步delete方法
    public static void enqueuDelete(String url, Callback callback, Type type){
        Request request=createRequest(url,METHOD_DELETE,null);
        Call call=okHttpClient.newCall(request);
        call.enqueue(callback);
    }
    //同步get方法
    public static Response executeGet(String url) throws IOException{
        Request request=createRequest(url,METHOD_GET,null);
        Call call=okHttpClient.newCall(request);
        Response response=call.execute();
        return response;
    }

    public static void getDataPut(String url, int isuserid, String sessionId, Map<String, String> map, final ICallBack callBack, final Type type){
        //1 创建FormBody的对象，把表单添加到formBody中
        FormBody.Builder builder = new FormBody.Builder();
        //2 判断集合对象不为空
        if(map!=null){
            //遍历map中的key值
            for(String key : map.keySet()){
                builder.add(key,map.get(key));
            }
        }
        //3 得到一个formbody体
        FormBody formBody = builder.build();

        Log.i("utils", "getDataPut: "+sessionId);
        Log.i("utils", "getDataPut: "+isuserid);
        //4 在单例模式中 初始化一个OK对象
        //5 始化一个request对象  并设置相关属性
        Request request = new Request.Builder()
                .addHeader("userId", isuserid+"")
                .addHeader("sessionId",sessionId)//添加请求头参数  这个id是随时变更的
                .put(formBody)//使用Put请求方式  值需要改这里即可
                .url(url)
                .build();
        //6 通过OK对象  和  request对象  得到一个call
        Call call = okHttpClient.newCall(request);
        //7 通过call对象  调用同步或异步方法进行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if(callBack!=null){
                    //通过Handler发送到主线程

                    callBack.failed(e);

                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功  通过Handler发送到主线程
                if(response !=null && response.isSuccessful()){
                    //通过response得到数据类型Object
                    String result = response.body().string();
                    //使用gson解析
                    Gson gson = new Gson();
                    final Object o = gson.fromJson(result, type);

                    if(callBack!=null){
                        callBack.successful(o);
                    }
                }
            }
        });
    }
}
