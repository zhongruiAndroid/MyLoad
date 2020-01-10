package com.github.load;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


/**
 * 进入页面加载的Dialog
 */
public class Loading2 {
    private static Loading2 singleObj;

    private LoadDialog loadDialog;

    private LoadListener loadListener;

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


    private void newDialog(Context context) {
        this.mContext = context;

        View contentView = getCurrentConfig().getLoadView();
        if (contentView == null) {
            int viewLayoutId = getCurrentConfig().getLoadViewId();
            contentView = LayoutInflater.from(context).inflate(viewLayoutId, null);
        }
        int styleId = getCurrentConfig().getLoadStyle();

        loadDialog = new LoadDialog(context, contentView, styleId);
        loadDialog.setCanceledOnTouchOutside(getCurrentConfig().isCanceledOnTouchOutside());
        loadDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                config = null;
                getLoadListener().dismiss(dialog);
                loadDialog = null;
                mContext = null;
            }
        });
        loadDialog.setKeyDownListener(new KeyDownListener() {
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (loadDialog != null && isNeedFinishAct && mContext != null && loadDialog.isShowing()) {
                    isNeedFinishAct = false;
                    loadDialog.dismiss();
                    if (mContext instanceof Activity) {
                        ((Activity) mContext).finish();
                    }
                    return true;
                }
                return false;
            }
        });
        setDialogWindow(context, loadDialog);


        Loading2.get().getLoadListener().show(contentView);

        loadDialog.show();
    }

    private void setDialogWindow(Context context, LoadDialog loadDialog) {
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
            window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(currentConfig.getBackgroundColor())));
        } else {
            window.setBackgroundDrawable(currentConfig.getBackgroundDrawable());
        }

        window.setDimAmount(currentConfig.getBackgroundDimAmount());

        window.setAttributes(params);
    }

    private Loading2 setDefConfig(LoadConfig loadConfig) {
        copyConfigAttr(loadConfig, defaultLoadConfig);
        return this;
    }

    private Loading2 setLoadConfig(LoadConfig fromConfig) {
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
        int defaultAnimDuration = fromConfig.getDefaultAnimDuration();


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

        if (backgroundColor > 0) {
            toConfig.setBackgroundColor(backgroundColor);
        }
        if (backgroundDrawable != null) {
            toConfig.setBackgroundDrawable(backgroundDrawable);
        }
        if (windowBackground > 0) {
            toConfig.setWindowBackground(windowBackground);
        }
        if (backgroundDimAmount > 0) {
            toConfig.setBackgroundDimAmount(backgroundDimAmount);
        }
        if (defaultDrawable != null) {
            toConfig.setDefaultDrawable(defaultDrawable);
        }
        if (defaultDrawableColor > 0) {
            toConfig.setDefaultDrawableColor(defaultDrawableColor);
        }
        if (defaultDrawableMode != null) {
            toConfig.setDefaultDrawableMode(defaultDrawableMode);
        }
        if (defaultAnimDuration > 0) {
            toConfig.setDefaultAnimDuration(defaultAnimDuration);
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

    public static void show(Activity activity, int styleId) {
        showForExit(activity, null, styleId, false);
    }

    public static void show(Activity activity, View view, int styleId) {
        showForExit(activity, view, styleId, false);
    }

    public static void showForExit(Activity activity, View view, int styleId, boolean dismissNeedFinishActivity) {
        Loading2.get().preShow(view, styleId, dismissNeedFinishActivity);
        Loading2.get().newDialog(activity);
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

    public LoadListener getLoadListener() {
        if (loadListener == null) {
            loadListener = new LoadListener() {
                @Override
                public void show(View dialogView) {
                }

                @Override
                public void dismiss(DialogInterface dialog) {
                }
            };
        }
        return loadListener;
    }

    public void setLoadListener(LoadListener loadListener) {
        this.loadListener = loadListener;
    }
}