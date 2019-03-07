package com.example.yuekao_moni_36.mvp.presenter;


import com.example.yuekao_moni_36.entity.LoginBean;
import com.example.yuekao_moni_36.entity.ShopCarEntity;
import com.example.yuekao_moni_36.entity.XiangqingEntity;
import com.example.yuekao_moni_36.mvp.model.ShowModel;
import com.example.yuekao_moni_36.mvp.view.ShowView;
import com.example.yuekao_moni_36.utils.ICallBack;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ShowPresenter  implements ShowPresenterInterface {

    private ShowModel showModel;
    private ShowView showView;

    public  void  attach(ShowView showView){
        this.showView=showView;
        showModel=new ShowModel();
    }
    public  void  detach(){
        if (showModel!=null){
            showModel=null;
        }
        if (showView!=null){
            showView=null;
        }
    }


    @Override
    public void getLogin(String phone, String pwd) {
        Type type=new TypeToken<LoginBean>(){}.getType();
          showModel.getLogin(phone, pwd, new ICallBack() {
              @Override
              public void successful(Object o) {
                  LoginBean loginBean= (LoginBean) o;
                  if (loginBean!=null){
                      showView.shopSuccess(loginBean);
                  }
              }

              @Override
              public void failed(Exception e) {
              showView.shopFailed(e.getMessage());
              }
          },type);

    }

    @Override
    public void getShopCart() {
        Type type=new TypeToken<ShopCarEntity>(){}.getType();
        showModel.getShopCar(new ICallBack() {
            @Override
            public void successful(Object o) {
                ShopCarEntity shopCarEntity= (ShopCarEntity) o;
                if (shopCarEntity!=null){
                    showView.shopSuccess(shopCarEntity);
                }
            }

            @Override
            public void failed(Exception e) {
              showView.shopFailed(e.getMessage());
            }
        },type);

    }

    @Override
    public void getShowPrpduct(int commodityId) {
              Type type=new TypeToken<XiangqingEntity>(){}.getType();
              showModel.getShowProductModel(commodityId, new ICallBack() {
                  @Override
                  public void successful(Object o) {
                       XiangqingEntity xiangqingEntity= (XiangqingEntity) o;
                       if (xiangqingEntity!=null){
                           showView.shopSuccess(xiangqingEntity);
                       }
                  }

                  @Override
                  public void failed(Exception e) {
                          showView.shopFailed(e.getMessage());
                  }
              },type);
    }
}
