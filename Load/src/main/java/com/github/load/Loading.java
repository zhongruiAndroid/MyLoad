package com.github.load;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;


/**
 * 进入页面加载的Dialog
 */
public class Loading {
    private static Loading singleObj;

    private Dialog loadDialog;


    private LoadConfig config;
    private LoadConfig defaultLoadConfig;
    private Context mContext;
    private boolean isNeedFinishAct;

    private Loading() {
        defaultLoadConfig = LoadConfig.defaultConfig();
    }

    private static Loading get() {
        if (singleObj == null) {
            synchronized (Loading.class) {
                if (singleObj == null) {
                    singleObj = new Loading();
                }
            }
        }
        return singleObj;
    }

    private LoadConfig getCurrentConfig() {
        return config == null ? defaultLoadConfig : config;
    }


    private Dialog newDialog(Context context, boolean showNow) {
        this.mContext = context;

        View contentView = getCurrentConfig().getLoadView();
        if (contentView == null) {
            int viewLayoutId = getCurrentConfig().getLoadViewId();
            contentView = LayoutInflater.from(context).inflate(viewLayoutId, null);
        }
        setDefaultViewStyle(contentView);
        int styleId = getCurrentConfig().getLoadStyle();
        if (styleId > 0) {
            loadDialog = new Dialog(context, styleId);
        } else {
            loadDialog = new Dialog(context);
        }
        loadDialog.setCanceledOnTouchOutside(getCurrentConfig().isCanceledOnTouchOutside());
        loadDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (loadDialog != null && isNeedFinishAct && mContext != null) {
                    isNeedFinishAct = false;
                    if (mContext instanceof Activity) {
                        ((Activity) mContext).finish();
                    }
                }
                config = null;
                loadDialog = null;
                mContext = null;
            }
        });

        loadDialog.setContentView(contentView);
        setDialogWindow(loadDialog);


        if (showNow) {
            if(!LoadHelper.actIsFinish(context)){
                loadDialog.show();
            }
        }
        return loadDialog;
    }

    private void setDefaultViewStyle(View view) {
        ProgressBar pb = view.findViewById(R.id.pb);
        Drawable defaultDrawable = getCurrentConfig().getDefaultDrawable();
        if (defaultDrawable != null) {
            pb.setIndeterminateDrawable(defaultDrawable);
        }
        if (getCurrentConfig().getDefaultDrawableColor() != -1) {
            Drawable indeterminateDrawable = pb.getIndeterminateDrawable();
            indeterminateDrawable.mutate().setColorFilter(getCurrentConfig().getDefaultDrawableColor(), getCurrentConfig().getDefaultDrawableMode());
        }
    }

    private void setDialogWindow(Dialog loadDialog) {
        if (loadDialog == null) {
            return;
        }
        LoadConfig currentConfig = getCurrentConfig();
        Window window = loadDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = currentConfig.getViewWidth();// ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() * 3 / 4;
        params.height = currentConfig.getViewHeight();
        params.gravity = Gravity.CENTER;

        Drawable backgroundDrawable = currentConfig.getBackgroundDrawable();

        if (backgroundDrawable == null) {
            int color = Color.TRANSPARENT;
            if (currentConfig.getBackgroundColor() != -1) {
                color = currentConfig.getBackgroundColor();
            }
            window.setBackgroundDrawable(new ColorDrawable(color));
        } else {
            window.setBackgroundDrawable(backgroundDrawable);
        }
        window.setDimAmount(currentConfig.getBackgroundDimAmount());
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {//4.4 全透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setAttributes(params);
    }

    private Loading setDefConfig(LoadConfig loadConfig) {
        if (loadConfig == null) {
            return this;
        }
        copyConfigAttr(loadConfig, defaultLoadConfig);
        return this;
    }

    private Loading setLoadConfig(LoadConfig fromConfig) {
        if (fromConfig == null) {
            return this;
        }
        if (config == null) {
            config = new LoadConfig();
            copyConfigAttr(defaultLoadConfig, config);
        }
        copyConfigAttr(fromConfig, config);
        return this;
    }

    private void copyConfigAttr(LoadConfig fromConfig, LoadConfig toConfig) {
        if (fromConfig == null || toConfig == null) {
            return;
        }
        int viewWidth = fromConfig.getViewWidth();
        int viewHeight = fromConfig.getViewHeight();

        View loadView = fromConfig.getLoadView();
        int loadViewId = fromConfig.getLoadViewId();
        int loadStyle = fromConfig.getLoadStyle();
        boolean canceledOnTouchOutside = fromConfig.isCanceledOnTouchOutside();
        Drawable backgroundDrawable = fromConfig.getBackgroundDrawable();
        int windowBackground = fromConfig.getBackgroundColor();
        float backgroundDimAmount = fromConfig.getBackgroundDimAmount();
        Drawable defaultDrawable = fromConfig.getDefaultDrawable();
        int defaultDrawableColor = fromConfig.getDefaultDrawableColor();
        PorterDuff.Mode defaultDrawableMode = fromConfig.getDefaultDrawableMode();


        toConfig.setViewWidth(viewWidth);
        toConfig.setViewHeight(viewHeight);

        if (loadView != null) {
            toConfig.setLoadView(loadView);
        }
        if (loadViewId > 0) {
            toConfig.setLoadViewId(loadViewId);
        }
        if (loadStyle > 0) {
            toConfig.setLoadStyle(loadStyle);
        }

        toConfig.setCanceledOnTouchOutside(canceledOnTouchOutside);

        if (backgroundDrawable != null) {
            toConfig.setBackgroundDrawable(backgroundDrawable);
        }
        if (windowBackground != -1) {
            toConfig.setBackgroundColor(windowBackground);
        }
        if (backgroundDimAmount != -1) {
            toConfig.setBackgroundDimAmount(backgroundDimAmount);
        }
        if (defaultDrawable != null) {
            toConfig.setDefaultDrawable(defaultDrawable);
        }
        if (defaultDrawableColor != -1) {
            toConfig.setDefaultDrawableColor(defaultDrawableColor);
        }
        if (defaultDrawableMode != null) {
            toConfig.setDefaultDrawableMode(defaultDrawableMode);
        }
    }

    /*给默认的dialog设置属性*/
    public static Loading setDefaultConfig(LoadConfig loadConfig) {
        return get().setDefConfig(loadConfig);
    }

    public static Loading resetDefaultConfig() {
        return get().setDefConfig(LoadConfig.defaultConfig());
    }

    /*给当前show的dialog设置属性*/
    public static Loading setConfig(LoadConfig loadConfig) {
        return get().setLoadConfig(loadConfig);
    }

    public static void show(Activity activity) {
        showForExit(activity, null, 0, false);
    }

    public static void show(Activity activity, View view) {
        showForExit(activity, view, 0, false);
    }

    public static void show(Activity activity, @StyleRes int styleId) {
        showForExit(activity, null, styleId, false);
    }

    public static void show(Activity activity, View view, @StyleRes int styleId) {
        showForExit(activity, view, styleId, false);
    }

    public static void showForExit(Activity activity) {
        showForExit(activity, null, 0, true);
    }

    public static void showForExit(Activity activity, View view) {
        showForExit(activity, view, 0, true);
    }

    public static void showForExit(Activity activity, @StyleRes int styleId) {
        showForExit(activity, null, styleId, true);
    }

    public static void showForExit(Activity activity, View view, @StyleRes int styleId, boolean dismissNeedFinishActivity) {
        Loading.get().preShow(view, styleId, dismissNeedFinishActivity);
        Loading.get().newDialog(activity, true);
    }

    public static void dismiss() {
        Loading.get().dismissDialog();
    }

    private void dismissDialog() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        config = null;
        mContext = null;
    }

    public void showDialog(Activity activity) {
        showDialog(activity, false);
    }

    public void showDialog(Activity activity, boolean dismissNeedFinishActivity) {
        Loading.get().preShow(null, 0, dismissNeedFinishActivity);
        Dialog dialog = Loading.get().newDialog(activity, false);
        if(!LoadHelper.actIsFinish(activity)){
            dialog.show();
        }
    }

    private void preShow(View view, int styleId, boolean dismissNeedFinishActivity) {
        isNeedFinishAct = dismissNeedFinishActivity;
        if (config == null) {
            config = new LoadConfig();
            copyConfigAttr(defaultLoadConfig, config);
        }
        if (view != null) {
            config.setLoadView(view);
        }
        if (styleId > 0) {
            config.setLoadStyle(styleId);
        }
    }
}