package com.example.basehello.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.basehello.R;


/**
 * desc:
 * created by pl at 2018/10/16
 */
public class TitlebarView extends RelativeLayout {
    RelativeLayout rlContainer;
    ImageView ivLeft;
    TextView tvLeft;
    ImageView ivRight;
    TextView tvRight;
    TextView tvTitle;

    private OnTitlebarLeftListener leftListener;

    private OnTitlebarRightListener rightListener;

    public TitlebarView(Context context) {
        this(context, null);
    }

    public TitlebarView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TitlebarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.msg_layout_titlebar, null);
        ivLeft = view.findViewById(R.id.iv_titlebar_left);
        ivRight = view.findViewById(R.id.iv_titlebar_right);
        tvTitle = view.findViewById(R.id.tv_titlebar_title);
        tvLeft = view.findViewById(R.id.tv_titlebar_left);
        tvRight = view.findViewById(R.id.tv_titlebar_right);
        rlContainer = view.findViewById(R.id.rl_titlebar_container);
        ivLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftListener != null) {
                    leftListener.onLeftClick();
                }
            }
        });

        tvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftListener != null) {
                    leftListener.onLeftClick();
                }
            }
        });

        ivRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightListener != null) {
                    rightListener.onRightClick();
                }
            }
        });

        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightListener != null) {
                    rightListener.onRightClick();
                }
            }
        });
        addView(view);
    }

    public void setContainerShow(boolean isShow) {
        if (isShow) {
            rlContainer.setVisibility(VISIBLE);
        } else {
            rlContainer.setVisibility(GONE);
        }
    }

    public void setContainerBackground(int resource) {
        rlContainer.setBackgroundResource(resource);
    }

    public void setAllTextViewColor(int color) {
        tvTitle.setTextColor(color);
        tvLeft.setTextColor(color);
        tvRight.setTextColor(color);
    }

    public void setTitleContent(String content) {
        tvTitle.setText(content);
    }

    public void setLeftImgResource(int resourceId) {
        ivLeft.setVisibility(VISIBLE);
        tvLeft.setVisibility(GONE);
        ivLeft.setImageResource(resourceId);
    }

    public void setLeftTvContent(String content) {
        ivLeft.setVisibility(GONE);
        tvLeft.setVisibility(VISIBLE);
        tvLeft.setText(content);
    }

    public void setRightImgResource(int resourceId) {
        ivRight.setVisibility(VISIBLE);
        tvRight.setVisibility(GONE);
        ivRight.setImageResource(resourceId);
    }

    public void setRightTvContent(String content) {
        tvRight.setVisibility(VISIBLE);
        ivRight.setVisibility(GONE);
        tvRight.setText(content);
    }

    public void setRightTvColor(@ColorInt int color) {
        tvRight.setTextColor(color);
    }


    public void setLeftOnClick(OnTitlebarLeftListener leftListener) {
        this.leftListener = leftListener;
    }

    public void setRightOnClick(OnTitlebarRightListener rightListener) {
        this.rightListener = rightListener;
    }

    public interface OnTitlebarLeftListener {
        void onLeftClick();
    }

    public interface OnTitlebarRightListener {
        void onRightClick();
    }
}
