package com.example.yuekao_moni_36;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DindanFragment extends Fragment {
    @BindView(R.id.btn_map)
    Button btnMap;
    @BindView(R.id.three_login)
    Button threeLogin;
    Unbinder unbinder;
    @BindView(R.id.twocode)
    Button twocode;
    @BindView(R.id.ed_text)
    EditText edText;
    @BindView(R.id.shengchengtwocode)
    Button shengchengtwocode;
    @BindView(R.id.img_show)
    ImageView imgShow;
    private int REQUEST_CODE=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dindan, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_map, R.id.three_login,R.id.twocode, R.id.shengchengtwocode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_map:
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
                break;
            case R.id.three_login:
                break;
            case R.id.twocode:
                Intent intent1 = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent1, REQUEST_CODE);

                break;
            case R.id.shengchengtwocode:
                String textContent = edText.getText().toString();
                if (TextUtils.isEmpty(textContent)) {
                    Toast.makeText(getActivity(), "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                edText.setText("");
                Bitmap mBitmap = CodeUtils.createImage(textContent, 400, 400, null);
                imgShow.setImageBitmap(mBitmap);
                break;
        }
    }


}
