package com.github.load;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.view.View;

public class LoadConfig {
    private View loadView;
    @LayoutRes
    private int loadViewId;
    @StyleRes
    private int loadStyle;

    private boolean canceledOnTouchOutside;


    /*dialog背景*/
    @ColorInt
    private int backgroundColor;
    /*dialog背景，windowBackgroundDrawable优先级大于background*/
    private Drawable backgroundDrawable;

    /*window背景*/
    @ColorInt
    private int windowBackground;

    /*dialog底部透明度 0.0f ~ 1f*/
    private float backgroundDimAmount;


    /*默认loading图片*/
    private Drawable defaultDrawable;
    /*默认loading图片颜色*/
    @ColorInt
    private int defaultDrawableColor;
    private PorterDuff.Mode defaultDrawableMode;


    public LoadConfig() {
        loadViewId=R.layout.loading_default;
        loadStyle=R.style.Theme_dialog;
        canceledOnTouchOutside=false;

        defaultDrawableMode= PorterDuff.Mode.SRC_ATOP;


        backgroundColor = Color.TRANSPARENT;
        windowBackground = Color.TRANSPARENT;

        backgroundDimAmount = 0.3f;
        defaultDrawableColor=-1;
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

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Drawable getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(Drawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }

    public int getWindowBackground() {
        return windowBackground;
    }

    public void setWindowBackground(@ColorInt int windowBackground) {
        this.windowBackground = windowBackground;
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
