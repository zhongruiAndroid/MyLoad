package com.github.load;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.WindowManager;

public class LoadConfig {
    private View loadView;
    private int viewWidth ;
    private int viewHeight;
    @LayoutRes
    private int loadViewId;
    @StyleRes
    private int loadStyle;

    private boolean canceledOnTouchOutside;

    /*window背景*/
    @ColorInt
    private int backgroundColor;
    private Drawable backgroundDrawable;

    /*dialog底部透明度 0.0f ~ 1f*/
    private float backgroundDimAmount;


    /*默认loading图片*/
    private Drawable defaultDrawable;
    /*默认loading图片颜色*/
    @ColorInt
    private int defaultDrawableColor;
    private PorterDuff.Mode defaultDrawableMode;


    public LoadConfig() {
        viewWidth= WindowManager.LayoutParams.WRAP_CONTENT;
        viewHeight= WindowManager.LayoutParams.WRAP_CONTENT;

        loadViewId=R.layout.loading_default;
        loadStyle=R.style.LoadStyle;
        canceledOnTouchOutside=false;

        defaultDrawableMode= PorterDuff.Mode.SRC_ATOP;


        backgroundColor =-1;

        backgroundDimAmount = -1f;
        defaultDrawableColor=-1;

    }
    public static LoadConfig defaultConfig(){
        LoadConfig loadConfig = new LoadConfig();
        loadConfig.backgroundColor =Color.TRANSPARENT;
        loadConfig.backgroundDimAmount=0.3f;
        return loadConfig;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }

    public View getLoadView() {
        return loadView;
    }

    public void setLoadView(View loadView) {
        this.loadView = loadView;
    }

    public int getLoadViewId() {
        return loadViewId;
    }

    public void setLoadViewId(@LayoutRes int loadViewId) {
        this.loadViewId = loadViewId;
    }

    public int getLoadStyle() {
        return loadStyle;
    }

    public void setLoadStyle(@StyleRes int loadStyle) {
        this.loadStyle = loadStyle;
    }

    public boolean isCanceledOnTouchOutside() {
        return canceledOnTouchOutside;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    public Drawable getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(Drawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public float getBackgroundDimAmount() {
        return backgroundDimAmount;
    }

    public void setBackgroundDimAmount(float backgroundDimAmount) {
        this.backgroundDimAmount = backgroundDimAmount;
    }

    public Drawable getDefaultDrawable() {
        return defaultDrawable;
    }

    public void setDefaultDrawable(Drawable defaultDrawable) {
        this.defaultDrawable = defaultDrawable;
    }

    public int getDefaultDrawableColor() {
        return defaultDrawableColor;
    }

    public void setDefaultDrawableColor(@ColorInt int defaultDrawableColor) {
        this.defaultDrawableColor = defaultDrawableColor;
    }

    public PorterDuff.Mode getDefaultDrawableMode() {
        return defaultDrawableMode;
    }

    public void setDefaultDrawableMode(PorterDuff.Mode defaultDrawableMode) {
        this.defaultDrawableMode = defaultDrawableMode;
    }

}
