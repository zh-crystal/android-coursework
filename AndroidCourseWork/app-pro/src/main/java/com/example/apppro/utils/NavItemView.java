package com.example.apppro.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apppro.R;

public class NavItemView extends RelativeLayout {
    TextView textView_title;
    TextView textView_line;
    ImageView imageView_Refresh;
    ImageView imageView_Message;
    private View mView;
    private boolean isShowRefreshImage =false;
    private NavItemRefreshListener navItemReflashListener;
    public NavItemView(Context context) {
        this(context,null);
    }
    public NavItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public void setShowRefreshImage(boolean showRefreshImage) {
        isShowRefreshImage = showRefreshImage;
    }

    public void setNavItemRefreshListener(NavItemRefreshListener navItemReflashListener) {
        this.navItemReflashListener = navItemReflashListener;
    }

    @SuppressLint("ResourceType")
    public NavItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mView = View.inflate(context, R.layout.view_navitem, this);
        textView_line=mView.findViewById(R.id.nav_item_tv_line);
        textView_title=mView.findViewById(R.id.nav_item_tv_title);
        imageView_Refresh=mView.findViewById(R.id.reflash);

//        获取自定义属性
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.NavItem);
        if(ta!=null){
            String navtext = ta.getString(R.styleable.NavItem_navtext);
            textView_title.setText(navtext);
        }
        initListener();
    }
    private void initListener() {
        final Animation rotateAnimation = new RotateAnimation(0,-360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(300);
        imageView_Refresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_Refresh.startAnimation(rotateAnimation);
                if(navItemReflashListener!=null){
                    navItemReflashListener.onRefresh(v);
                }
            }
        });

    }
    public void startActive(){
        if(isShowRefreshImage){
            textView_title.setVisibility(GONE);
            imageView_Refresh.setVisibility(VISIBLE);
        }else {
            textView_title.setTextColor(Color.WHITE);
            textView_title.setTextSize(18);
        }
        textView_line.animate().alpha(1).setDuration(200).start();
    }
    public void cancelActive(){
        if(isShowRefreshImage){
            textView_title.setVisibility(VISIBLE);
            imageView_Refresh.setVisibility(GONE);
            textView_line.setAlpha(0);
        }else {
            textView_title.setTextColor(Color.parseColor("#F1F1F1"));
            textView_title.setTextSize(16);
        }
        textView_line.animate().alpha(0).setDuration(200).start();
    }
}
