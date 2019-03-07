package com.example.yuekao_moni_36;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yuekao_moni_36.entity.LoginBean;
import com.example.yuekao_moni_36.mvp.presenter.ShowPresenter;
import com.example.yuekao_moni_36.mvp.view.ShowView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ShowView {

    @BindView(R.id.ed_loginphone)
    EditText edLoginphone;
    @BindView(R.id.ed_loginpwd)
    EditText edLoginpwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private ShowPresenter showPresenter;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sp=getSharedPreferences("user", MODE_PRIVATE);
        initPresenter();
    }

    private void initPresenter() {
        showPresenter = new ShowPresenter();
        showPresenter.attach(this);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String userphone = getUserName();
        String userPassWord = getUserPassWord();
        showPresenter.getLogin(userphone,userPassWord);
    }

    @Override
    public String getUserName() {
        return edLoginphone.getText().toString().trim();
    }

    @Override
    public String getUserPassWord() {
        return edLoginpwd.getText().toString().trim();
    }

    @Override
    public void shopSuccess(Object obj) {
        LoginBean loginBean = (LoginBean) obj;
        Toast.makeText(this, "" + loginBean.getMessage(), Toast.LENGTH_SHORT).show();
        if ("0000".equals(loginBean.getStatus()) || "登录成功".equals(loginBean.getMessage())) {
            sp.edit().putString("sid", loginBean.getResult().getSessionId())
                    .putInt("uid", loginBean.getResult().getUserId())
                    .putString("name", loginBean.getResult().getNickName())
                    .putString("pwd", getUserPassWord())
                    .putString("timg", loginBean.getResult().getHeadPic())
                    .putBoolean("isFirst", false)
                    .commit();
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void shopFailed(String str) {
        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
    }
}
