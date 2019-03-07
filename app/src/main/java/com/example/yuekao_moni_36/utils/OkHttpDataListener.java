package com.example.yuekao_moni_36.utils;

public interface OkHttpDataListener <T>{
    void success(T data);
    void fail(String msg);
}
