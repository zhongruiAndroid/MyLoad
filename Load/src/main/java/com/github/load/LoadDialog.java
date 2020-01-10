package com.github.load;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


/**
 * 进入页面加载的Dialog
 */
class LoadDialog extends Dialog {
    private KeyDownListener keyDownListener;
    private static final int TAG_SHOW = 1;
    private static final int TAG_DISMISS = 0;
    private static int showTag = TAG_DISMISS;

    private static LoadDialog loading;
    private static Context context;

    private static boolean isNeedFinishAct;

    private static int loadView = -1;
    private static final int noLoadView = -1;

    public static void setLoadView(@LayoutRes int loadView) {
        LoadDialog.loadView = loadView;
    }

    public LoadDialog(Context context, View view, int style) {
        super(context, style);
        setContentView(view);
        Window window = getWindow();
        this.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ((Activity) context).getWindowManager()
                .getDefaultDisplay().getWidth() * 3 / 4;
        params.gravity = Gravity.CENTER;
        window.setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(android.R.color.transparent)));
        window.setAttributes(params);

    }


    public KeyDownListener getKeyDownListener() {
        if(keyDownListener==null){
            keyDownListener=new KeyDownListener() {
                @Override
                public boolean onKeyDown(int keyCode, KeyEvent event) {
                    return false;
                }
            };
        }
        return keyDownListener;
    }

    public void setKeyDownListener(KeyDownListener keyDownListener) {
        this.keyDownListener = keyDownListener;
    }

    private static void setLoading(Context ctx) {
        if (ctx == null) {
            return;
        }
        context = ctx;
        if (loadView == noLoadView) {
//            loading = new LoadDialog(context, R.layout.loading_default, R.style.Theme_dialog);
        } else {
//            loading = new LoadDialog(context, loadView, R.style.Theme_dialog);
        }
        loading.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                loading.showTag = 0;
                isNeedFinishAct = false;
            }
        });
    }

    public static void showForExit(Context ctx) {
        if (ctx == null) {
            return;
        }
        showForExit(ctx, true);
    }

    public static void showForExit(Context ctx, boolean dismissAndFinishActivity) {
        if (ctx == null) {
            return;
        }
        if (loading == null || !loading.isShowing()) {
            isNeedFinishAct = dismissAndFinishActivity;
            setLoading(ctx);
        }
        if (LoadDialog.showTag == 0 && loading != null) {
            Activity activity = (Activity) ctx;
            if (activity != null && !activity.isFinishing()) {
                LoadDialog.showTag = 1;
                loading.show();
            } else {
                loading.showTag = 0;
            }
        }
    }

    public static void show(Context ctx) {
        if (ctx == null) {
            return;
        }
        if (loading == null || !loading.isShowing()) {
            setLoading(ctx);
        }
        if (LoadDialog.showTag == 0 && loading != null) {
            Activity activity = (Activity) ctx;
            if (activity != null && !activity.isFinishing()) {
                LoadDialog.showTag = 1;
                loading.show();
            } else {
                loading.showTag = 0;
            }
        }
    }

    public static void dismissLoading() {
        if (loading != null && loading.isShowing()) {
            loading.showTag = 0;
            loading.dismiss();
            loading = null;
            context = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean flag = getKeyDownListener().onKeyDown(keyCode, event);
        if(flag){
            return true;
        }
        if (isNeedFinishAct && context != null && loading.isShowing()) {
            isNeedFinishAct = false;
            loading.dismiss();
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}