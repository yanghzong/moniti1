package com.example.yuekao_moni_36;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuekao_moni_36.adapter.ShopCarAdapter;
import com.example.yuekao_moni_36.entity.ShopCarEntity;
import com.example.yuekao_moni_36.mvp.presenter.ShowPresenter;
import com.example.yuekao_moni_36.mvp.view.ShowView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShopcarFragment extends Fragment implements ShowView {
    @BindView(R.id.rv_shopcar)
    RecyclerView rvShopcar;
    Unbinder unbinder;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.tv_allprice)
    TextView tvAllprice;
    private List<ShopCarEntity.ResultBean> shopcarlist = new ArrayList<>();
    private ShopCarAdapter shopCarAdapter;
    private ShowPresenter showPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopcar, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPresenter();
        initAdapter();
    }

    private void initAdapter() {
        shopCarAdapter = new ShopCarAdapter(getActivity(), shopcarlist);
        rvShopcar.setAdapter(shopCarAdapter);
        rvShopcar.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        shopCarAdapter.setOnProductClickListener(new ShopCarAdapter.OnCommodityClickListener() {
            @Override
            public void onCommodityClick(int commodityId) {
                Toast.makeText(getActivity(), ""+commodityId, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),XiangqingActivity.class);
                String s = String.valueOf(commodityId);
                intent.putExtra("cid",s);
                startActivity(intent);
            }
        });
    }

    private void initPresenter() {
        showPresenter = new ShowPresenter();
        showPresenter.attach(this);
        showPresenter.getShopCart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getUserPassWord() {
        return null;
    }

    @Override
    public void shopSuccess(Object obj) {
        ShopCarEntity shopCarEntity = (ShopCarEntity) obj;
        List<ShopCarEntity.ResultBean> result = shopCarEntity.getResult();
        if (result != null) {
            shopcarlist.clear();
            shopcarlist.addAll(result);
        }
        shopCarAdapter.notifyDataSetChanged();

    }

    @Override
    public void shopFailed(String str) {
        Toast.makeText(getActivity(), "" + str, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.cb_all)
    public void onViewClicked() {
        boolean checked = cbAll.isChecked();
        for (ShopCarEntity.ResultBean resultBean : shopcarlist) {
            resultBean.setCheck(checked);
        }
        shopCarAdapter.notifyDataSetChanged();
        caclulatePrice();
    }

    //计算总价
    private void caclulatePrice() {
        float price=0;
        for (ShopCarEntity.ResultBean resultBean : shopcarlist) {
            if (resultBean.isCheck()) {
                price+=resultBean.getPrice()*resultBean.getCount();
            }
        }
        tvAllprice.setText("总价:"+price);
    }
}
