package com.example.pl.testhello.test.scan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pl.testhello.R;
import com.example.pl.testhello.base.BaseActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class FourActivity extends BaseActivity {

    public EditText editText = null;
    public Button button = null;
    public Button button1 = null;
    public ImageView imageView = null;

    public Bitmap mBitmap = null;

    @Override
    protected int getLayout() {
        return R.layout.activity_four;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {

        editText = findViewById(R.id.edit_content);
        button = findViewById(R.id.button_content);
        button1 = findViewById(R.id.button1_content);
        imageView = findViewById(R.id.image_content);

        /**
         * 生成二维码图片
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textContent = editText.getText().toString();
                if (TextUtils.isEmpty(textContent)) {
                    Toast.makeText(FourActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                editText.setText("");
                mBitmap = CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
                imageView.setImageBitmap(mBitmap);
            }
        });

        /**
         * 生成不带logo的二维码图片
         */
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textContent = editText.getText().toString();
                if (TextUtils.isEmpty(textContent)) {
                    Toast.makeText(FourActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                editText.setText("");
                mBitmap = CodeUtils.createImage(textContent, 400, 400, null);
                imageView.setImageBitmap(mBitmap);
            }
        });
    }

}
