package com.example.yuekao_moni_36.utils;


import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OkLogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        long l = System.nanoTime();
        //拿到Response对象
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        //得出请求网络,到得到结果,中间消耗了多长时间
        String method=request.method();
        HttpUrl url=request.url();
        Headers headers=request.headers();
        Set<String> names=headers.names();
        Iterator<String> iterator=names.iterator();
        while (iterator.hasNext()){
            String next=iterator.next();
            String value=headers.get(next);
        }
        return chain.proceed(request);
    }
}
