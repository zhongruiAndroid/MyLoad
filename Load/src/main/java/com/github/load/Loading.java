package com.github.load;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


/**
 * 进入页面加载的Dialog
 */
public class Loading {
    private final int useDefFlag = 0;
    private Dialog loadDialog;
    private boolean isExit;
    private int loadView = 0;
    private int loadViewStyle = 0;
    private int loadViewColor = Color.TRANSPARENT;

    /**********************************************************/
    private static Loading singleObj;

    public Loading() {
    }
    public static Loading get() {
        if (singleObj == null) {
            synchronized (Loading.class) {
                if (singleObj == null) {
                    singleObj = new Loading();
                }
            }
        }
        return singleObj;
    }

    /**********************************************************/

    public void setLoadView(@LayoutRes int loadView) {
        this.loadView = loadView;
    }

    public void setLoadViewStyle(@StyleRes int loadViewStyle) {
        this.loadViewStyle = loadViewStyle;
    }

    public void setLoadViewColor(int loadViewColor) {
        this.loadViewColor = loadViewColor;
    }

    private void setLoading(final Context context, View contentView, @StyleRes int styleId) {
        if (styleId == useDefFlag) {
//            styleId = R.style.LoadStyle;
        }
        loadDialog = new Dialog(context, styleId);
        if (contentView == null) {
            contentView = new View(context);
        }
        loadDialog.setContentView(contentView);

        Window window = loadDialog.getWindow();
        loadDialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ((Activity) context).getWindowManager()
                .getDefaultDisplay().getWidth() * 3 / 4;
        params.gravity = Gravity.CENTER;
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        window.setAttributes(params);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        loadDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (isExit && context != null) {
                    Activity activity = LoadHelper.findActivity(context);
                    if (activity != null) {
                        activity.finish();
                    }
                }
                isExit = false;
            }
        });
    }



    public void showDialogForExit(Context ctx) {
        showDialog(ctx);
        isExit = true;
    }

    public void showDialogForExit(Context ctx, boolean exit) {
        showDialog(ctx);
        isExit = exit;
    }

    public void showDialog(Context context) {
        showDialog(context, loadView, loadViewStyle);
    }

    public void showDialog(Context context, @LayoutRes int layoutId, @StyleRes int styleId) {
        if (context == null) {
            return;
        }
        View view;
        if (layoutId == useDefFlag) {
            view = new View(context);
        }else{
            view = LayoutInflater.from(context).inflate(layoutId, null);
        }

        showDialog(context,view , styleId);
    }

    public void showDialog(Context context, @StyleRes int styleId) {
        showDialog(context, null, styleId);
    }

    public void showDialog(Context context, View layout, @StyleRes int styleId) {
        if (context == null) {
            return;
        }
        if (LoadHelper.actIsFinish(context)) {
            return;
        }

        if (loadDialog == null || !loadDialog.isShowing()) {
            setLoading(context, layout, styleId);
        }
        if (loadDialog != null) {
            isExit = false;
            loadDialog.show();
        }
    }

    public void dismissLoading() {
        if (loadDialog == null) {
            return;
        }
        Context context = loadDialog.getContext();
        if (LoadHelper.actIsFinish(context)) {
            loadDialog = null;
            return;
        }
        if (loadDialog.isShowing()) {
            Window window = loadDialog.getWindow();
            if(window==null){
                loadDialog = null;
                isExit = false;
                return;
            }
            if(window.getWindowManager()==null){
                loadDialog = null;
                isExit = false;
                return;
            }
            loadDialog.dismiss();
            loadDialog = null;
        }
    }

    /************************************************************/
    public static void show(Context context) {
        get().showDialog(context);
    }

    public static void show(Context context, @LayoutRes int layoutId, @StyleRes int styleId) {
        get().showDialog(context, layoutId, styleId);
    }

    public static void show(Context context, @StyleRes int styleId) {
        get().showDialog(context, styleId);
    }

    public static void show(Context context, View layout, @StyleRes int styleId) {
        get().showDialog(context, layout, styleId);
    }

    public static void showForExit(Context ctx) {
        get().showDialogForExit(ctx);
    }

    public static void showForExit(Context ctx, boolean exit) {
        get().showDialogForExit(ctx, exit);
    }

    public static void dismissLoad() {
        get().dismissLoading();
    }
}