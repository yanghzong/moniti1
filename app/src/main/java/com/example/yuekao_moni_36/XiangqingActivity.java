package com.example.yuekao_moni_36;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuekao_moni_36.entity.XiangqingEntity;
import com.example.yuekao_moni_36.mvp.presenter.ShowPresenter;
import com.example.yuekao_moni_36.mvp.view.ShowView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XiangqingActivity extends AppCompatActivity implements ShowView {

    @BindView(R.id.vp_banner)
    ViewPager vpBanner;
    @BindView(R.id.tv_xiangqingname)
    TextView tvXiangqingname;
    @BindView(R.id.wb_html)
    WebView wbHtml;
    private int cid;
    private ShowPresenter showPresenter;
    private List<String> bannerlist;
    List<XiangqingEntity.ResultBean> xiangqinglist=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangqing);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String s = intent.getStringExtra("cid");

        cid = Integer.valueOf(s);
        initPresenter();
        initPresenter();


    }

    private void initPresenter() {
        showPresenter = new ShowPresenter();
        showPresenter.attach(this);
        showPresenter.getShowPrpduct(cid);
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
        XiangqingEntity xiangqingEntity= (XiangqingEntity) obj;
        XiangqingEntity.ResultBean result = xiangqingEntity.getResult();
        String picture = result.getPicture();
        String[] split = picture.split("\\,");
        bannerlist = new ArrayList<>();
        bannerlist.add(split[0]);
        bannerlist.add(split[1]);
        bannerlist.add(split[2]);
        bannerlist.add(split[3]);
        bannerlist.add(split[4]);
        vpBanner.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return bannerlist.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view==o;
            }
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                SimpleDraweeView img = new SimpleDraweeView(XiangqingActivity.this);
                img.setScaleType(ImageView.ScaleType.FIT_XY);
                img.setImageURI(bannerlist.get(position));
                container.addView(img);
                return img;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });

        tvXiangqingname.setText(result.getCommodityName());
        WebSettings settings = wbHtml.getSettings();
        settings.setJavaScriptEnabled(true);//支持JS
        String js = "<script type=\"text/javascript\">" +
                "var imgs = document.getElementsByTagName('img');" + // 找到img标签
                "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
                "imgs[i].style.width = '100%';" +  // 宽度改为100%
                "imgs[i].style.height = 'auto';" +
                "}" +
                "</script>";
        wbHtml.loadData(result.getDetails() + js, "text/html; charset=UTF-8", null);

    }

    @Override
    public void shopFailed(String str) {

    }
}
