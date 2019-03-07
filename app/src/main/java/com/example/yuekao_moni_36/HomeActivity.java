package com.example.yuekao_moni_36;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.vp_home)
    ViewPager vpHome;
    @BindView(R.id.tv_shopcar)
    TextView tvShopcar;
    @BindView(R.id.tv_dindan)
    TextView tvDindan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        final List<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(new ShopcarFragment());
        fragmentList.add(new DindanFragment());
        vpHome.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
          ChangeBackGroup(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void ChangeBackGroup(int i) {
        switch (i){
            case 0:
                tvShopcar.setTextColor(Color.RED);
                tvDindan.setTextColor(Color.BLACK);
                break;
            case 1:
                tvDindan.setTextColor(Color.RED);
                tvShopcar.setTextColor(Color.BLACK);
        }
    }

    @OnClick({R.id.tv_shopcar, R.id.tv_dindan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcar:
                vpHome.setCurrentItem(0);
                ChangeBackGroup(0);
                break;
            case R.id.tv_dindan:
                vpHome.setCurrentItem(1);
                ChangeBackGroup(1);
                break;
        }
    }
}
