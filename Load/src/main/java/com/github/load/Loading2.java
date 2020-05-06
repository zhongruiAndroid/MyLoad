package com.github.load;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.util.Log;
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
    private Dialog loadDialog;
    private boolean isExit;
    private int loadView =0;
    private int loadViewStyle =0;
    private final int useDefFlag = 0;

    /**********************************************************/
    private static Loading2 singleObj;

    private Loading2() {
    }

    public static Loading2 get() {
        if (singleObj == null) {
            synchronized (Loading2.class) {
                if (singleObj == null) {
                    singleObj = new Loading2();
                }
            }
        }
        return singleObj;
    }

    /**********************************************************/

    public void setLoadView(@LayoutRes int loadView) {
        this.loadView = loadView;
    }

    private void setLoading(final Context context, View contentView, @StyleRes int styleId) {
        if(styleId==useDefFlag){
            styleId=R.style.LoadStyle;
        }
        loadDialog = new Dialog(context, styleId);
        if(contentView==null){
            loadDialog.setContentView(R.layout.loading_default);
        }else{
            loadDialog.setContentView(contentView);
        }

        Window window = loadDialog.getWindow();
        loadDialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ((Activity) context).getWindowManager()
                .getDefaultDisplay().getWidth() * 3 / 4;
        params.gravity = Gravity.CENTER;
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        window.setAttributes(params);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        loadDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (isExit && context != null ) {
                    Activity activity = LoadHelper.findActivity(context);
                    if(activity!=null){
                        activity.finish();
                    }
                }
                isExit = false;
            }
        });
    }

    public void showForExit(Context ctx) {
        show(ctx);
        isExit=true;
    }
    public void showForExit(Context ctx, boolean exit) {
        show(ctx);
        isExit=exit;
    }
    public void show(Context context) {
        show(context,loadView,loadViewStyle);
    }
    public void show(Context context, @LayoutRes int layoutId, @StyleRes int styleId) {
        if(context==null){
            return;
        }
        if(layoutId==useDefFlag){
            layoutId=R.layout.loading_default;
        }
        if(styleId==useDefFlag){
            styleId=R.style.LoadStyle;
        }
        show(context, LayoutInflater.from(context).inflate(layoutId,null),styleId);
    }
    public void show(Context context, @StyleRes int styleId) {
        show(context,null,styleId);
    }
    public void show(Context context, View layout, @StyleRes int styleId) {
        if (context == null) {
            return;
        }
        if(LoadHelper.actIsFinish(context)){
            return;
        }

        if (loadDialog == null || !loadDialog.isShowing()) {
            setLoading(context,layout,styleId);
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
        if(LoadHelper.actIsFinish(context)){
            loadDialog=null;
            return;
        }
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
            loadDialog = null;
        }
    }
}