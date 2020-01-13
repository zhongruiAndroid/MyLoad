package com.github.load;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;


/**
 * 进入页面加载的Dialog
 */
public class Loading2 {
    private static Loading2 singleObj;

    private LoadDialog2 loadDialog;


    private LoadConfig config;
    private LoadConfig defaultLoadConfig;
    private Context mContext;
    private boolean isNeedFinishAct;

    private Loading2() {
        defaultLoadConfig = new LoadConfig();
    }

    private static Loading2 get() {
        if (singleObj == null) {
            synchronized (Loading2.class) {
                if (singleObj == null) {
                    singleObj = new Loading2();
                }
            }
        }
        return singleObj;
    }

    private LoadConfig getCurrentConfig() {
        return config == null ? defaultLoadConfig : config;
    }


    private LoadDialog2 newDialog(Context context,boolean showNow) {
        this.mContext = context;

        View contentView = getCurrentConfig().getLoadView();
        if (contentView == null) {
            int viewLayoutId = getCurrentConfig().getLoadViewId();
            contentView = LayoutInflater.from(context).inflate(viewLayoutId, null);
        }
        setDefaultViewStyle(contentView);
        int styleId = getCurrentConfig().getLoadStyle();
        if(styleId>0){
            loadDialog = new LoadDialog2(context, contentView, styleId);
        }else{
            loadDialog = new LoadDialog2(context, contentView);
        }
        loadDialog.setCanceledOnTouchOutside(getCurrentConfig().isCanceledOnTouchOutside());
        loadDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                config = null;
                loadDialog = null;
                mContext = null;
            }
        });
        loadDialog.setKeyDownListener(new KeyDownListener() {
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (loadDialog != null && isNeedFinishAct && mContext != null && loadDialog.isShowing()) {
                    isNeedFinishAct = false;
                    if (mContext instanceof Activity) {
                        ((Activity) mContext).finish();
                    }
                    loadDialog.dismiss();
                    return true;
                }
                return false;
            }
        });

        loadDialog.setContentView(contentView);
        setDialogWindow(context, loadDialog);


        if(showNow){
            loadDialog.show();
        }

        return loadDialog;
    }

    private void setDefaultViewStyle(View view) {
        ProgressBar pb=view.findViewById(R.id.pb);
        Drawable defaultDrawable = getCurrentConfig().getDefaultDrawable();
        if(defaultDrawable!=null){
            pb.setIndeterminateDrawable(defaultDrawable);
        }
        if(getCurrentConfig().getDefaultDrawableColor()!=-1){
            Drawable indeterminateDrawable = pb.getIndeterminateDrawable();
            indeterminateDrawable.mutate().setColorFilter(getCurrentConfig().getDefaultDrawableColor(),getCurrentConfig().getDefaultDrawableMode());
        }
    }

    private void setDialogWindow(Context context, LoadDialog2 loadDialog) {
        if (loadDialog == null) {
            return;
        }
        Window window = loadDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() * 3 / 4;
        params.gravity = Gravity.CENTER;

        LoadConfig currentConfig = getCurrentConfig();
        Drawable backgroundDrawable = currentConfig.getBackgroundDrawable();

        if (backgroundDrawable == null) {
            window.setBackgroundDrawable(new ColorDrawable(currentConfig.getBackgroundColor()));
        } else {
            window.setBackgroundDrawable(currentConfig.getBackgroundDrawable());
        }

        window.setDimAmount(currentConfig.getBackgroundDimAmount());

        window.setAttributes(params);
    }

    private Loading2 setDefConfig(LoadConfig loadConfig) {
        if(loadConfig==null){
            return this;
        }
        defaultLoadConfig=loadConfig;
//        copyConfigAttr(loadConfig, defaultLoadConfig);
        return this;
    }

    private Loading2 setLoadConfig(LoadConfig fromConfig) {
        if(fromConfig==null){
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
        View loadView = fromConfig.getLoadView();
        int loadViewId = fromConfig.getLoadViewId();
        int loadStyle = fromConfig.getLoadStyle();
        boolean canceledOnTouchOutside = fromConfig.isCanceledOnTouchOutside();
        int backgroundColor = fromConfig.getBackgroundColor();
        Drawable backgroundDrawable = fromConfig.getBackgroundDrawable();
        int windowBackground = fromConfig.getWindowBackground();
        float backgroundDimAmount = fromConfig.getBackgroundDimAmount();
        Drawable defaultDrawable = fromConfig.getDefaultDrawable();
        int defaultDrawableColor = fromConfig.getDefaultDrawableColor();
        PorterDuff.Mode defaultDrawableMode = fromConfig.getDefaultDrawableMode();


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

        if (backgroundColor !=-1) {
            toConfig.setBackgroundColor(backgroundColor);
        }
        if (backgroundDrawable != null) {
            toConfig.setBackgroundDrawable(backgroundDrawable);
        }
        if (windowBackground !=-1) {
            toConfig.setWindowBackground(windowBackground);
        }
        if (backgroundDimAmount >= 0) {
            toConfig.setBackgroundDimAmount(backgroundDimAmount);
        }
        if (defaultDrawable != null) {
            toConfig.setDefaultDrawable(defaultDrawable);
        }
        if (defaultDrawableColor !=-1) {
            toConfig.setDefaultDrawableColor(defaultDrawableColor);
        }
        if (defaultDrawableMode != null) {
            toConfig.setDefaultDrawableMode(defaultDrawableMode);
        }
    }

    /*给默认的dialog设置属性*/
    public static Loading2 setDefaultConfig(LoadConfig loadConfig) {
        return get().setDefConfig(loadConfig);
    }

    /*给当前show的dialog设置属性*/
    public static Loading2 setConfig(LoadConfig loadConfig) {
        return get().setLoadConfig(loadConfig);
    }

    public static void show(Activity activity) {
        showForExit(activity, null, 0, false);
    }

    public static void show(Activity activity, View view) {
        showForExit(activity, view, 0, false);
    }

    public static void show(Activity activity,@StyleRes int styleId) {
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
        Loading2.get().preShow(view, styleId, dismissNeedFinishActivity);
        Loading2.get().newDialog(activity,true);
    }
    public static void dismiss(){
        Loading2.get().dismissDialog();
    }

    private void dismissDialog() {
        if(loadDialog!=null&&loadDialog.isShowing()){
            loadDialog.dismiss();
        }
        config=null;
        mContext=null;
    }

    public void showDialog(Activity activity){
        showDialog(activity,false);
    }
    public void showDialog(Activity activity,boolean dismissNeedFinishActivity) {
        Loading2.get().preShow(null, 0, dismissNeedFinishActivity);
        Loading2.get().newDialog(activity, false).show();
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