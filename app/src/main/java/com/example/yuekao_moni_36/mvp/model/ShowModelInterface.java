package com.example.yuekao_moni_36.mvp.model;

import com.example.yuekao_moni_36.utils.ICallBack;

import java.lang.reflect.Type;

public interface ShowModelInterface {
    void getLogin(String phone, String pwd, final ICallBack callBack, final Type type);
    void getShopCar(final ICallBack callBack, final Type type);

    void getShowProductModel(int commodityId, final ICallBack callBack, final Type type);
}
