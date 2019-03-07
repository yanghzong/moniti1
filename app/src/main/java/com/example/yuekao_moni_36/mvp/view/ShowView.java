package com.example.yuekao_moni_36.mvp.view;

import com.example.yuekao_moni_36.entity.LoginBean;

public interface ShowView {

    String getUserName();
    String getUserPassWord();
    void  shopSuccess(Object obj);
    void  shopFailed(String str);

}
