package com.github.load;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;



/**
 *  进入页面加载的Dialog
 */
public class Loading2 extends Dialog {
    public static int showTag = 0;
    private static Dialog dialog;
    private static Context context;
    private static boolean isExit;
    private static int loadView=-1;
    private static final int noLoadView=-1;

    public static void setLoadView(@LayoutRes int loadView) {
        Loading2.loadView = loadView;
    }
    public Loading2(Context context, int layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        this.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ((Activity) context).getWindowManager()
                .getDefaultDisplay().getWidth() * 3 / 4;
        params.gravity = Gravity.CENTER;
        window.setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(android.R.color.transparent)));
        window.setAttributes(params);
    }

    private static void setLoading(Context ctx) {
        if(ctx==null){
            return;
        }
        context = ctx;
        if(loadView==noLoadView){
            dialog = new Dialog(context,R.style.LoadStyle);
            dialog.setContentView(R.layout.loading_default);
        }else{
            dialog = new Dialog(context,R.style.LoadStyle);
            dialog.setContentView(loadView);
        }
        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Loading2.showTag = 0;
                isExit = false;
            }
        });
    }
    public  static void showForExit(Context ctx) {
        if(ctx==null){
            return;
        }
        showForExit(ctx,true);
    }
    public  static void showForExit(Context ctx,boolean exit) {
        if(ctx==null){
            return;
        }
        if(dialog==null||!dialog.isShowing()){
            isExit=exit;
            setLoading(ctx);
        }
        if (Loading2.showTag == 0 && dialog != null) {
            Activity activity = (Activity) ctx;
            if (activity != null && !activity.isFinishing()) {
                Loading2.showTag = 1;
                dialog.show();
            } else {
                Loading2.showTag = 0;
            }
        }
    }
    public  static void show(Context ctx) {
        if(ctx==null){
            return;
        }
        if(dialog==null||!dialog.isShowing()){
            setLoading(ctx);
        }
        if (Loading2.showTag == 0 && dialog != null) {
            Activity activity = (Activity) ctx;
            if (activity != null && !activity.isFinishing()) {
                Loading2.showTag = 1;
                dialog.show();
            } else {
                Loading2.showTag = 0;
            }
        }
    }
    public static void dismissLoading() {
        if (dialog != null &&dialog.isShowing()) {
            Loading2.showTag = 0;
            dialog.dismiss();
            dialog=null;
            context = null;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isExit&&context!=null&&dialog.isShowing()){
            isExit=false;
            dialog.dismiss();
            if(context instanceof Activity){
                ((Activity)context).finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}