package com.github.load;

import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.WindowManager;

public class LoadConfig {
    private View loadView;

    private @LayoutRes
    int loadViewId;

    private @StyleRes
    int loadStyle;


    private int defaultProgressColor;


    private WindowManager.LayoutParams params;
    private Drawable windowBackground;
    private boolean canceledOnTouchOutside;



}
