package com.github.load;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
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

    /*loading全局配置*/
    private static int _loadViewId = 0;
    private static View _loadView = null;
    private static int _loadViewStyle = 0;
    private static int _loadDrawableColor = Color.TRANSPARENT;
    private static float _dimAmount = 0.2f;
    private static float _alpha = 1.0f;
    private static int _animId = 0;

    public static void setGlobalLoadView(int loadViewId) {
        _loadViewId = loadViewId;
        _loadView=null;
        get().setLoadView(_loadViewId);
    }

    public static void setGlobalLoadView(View loadView) {
        _loadView = loadView;
        _loadViewId=0;
        get().setLoadView(_loadView);
    }

    public static void setGlobalLoadViewStyle(int loadViewStyle) {
        _loadViewStyle = loadViewStyle;
        get().setLoadViewStyle(_loadViewStyle);
    }

    public static void setGlobalLoadDrawableColor(int loadDrawableColor) {
        _loadDrawableColor = loadDrawableColor;
        get().setLoadDrawableColor(_loadDrawableColor);
    }

    public static void setGlobalDimAmount(float dimAmount) {
        if (dimAmount < 0 || dimAmount > 1) {
            dimAmount = 0.2f;
        }
        _dimAmount = dimAmount;
        get().setDimAmount(_dimAmount);
    }

    public static void setGlobalAlpha(float alpha) {
        if (alpha < 0 || alpha > 1) {
            alpha = 1;
        }
        _alpha = alpha;
        get().setAlpha(_alpha);
    }

    public static void setGlobalAnimId(int animId) {
        _animId = animId;
        get().setAnimId(_animId);
    }

    public static void resetGlobalAttr() {
        _loadViewId = 0;
        _loadView = null;
        _loadViewStyle = 0;
        _loadDrawableColor = Color.TRANSPARENT;
        _dimAmount = 0.2f;
        _alpha = 1.0f;
        _animId = 0;

        get().setLoadView(_loadViewId);
        get().setLoadView(_loadView);
        get().setLoadViewStyle(_loadViewStyle);
        get().setLoadDrawableColor(_loadDrawableColor);
        get().setDimAmount(_dimAmount);
        get().setAlpha(_alpha);
        get().setAnimId(_animId);
    }

    /********************************************************************************/
    private final int useDefFlag = 0;
    private Dialog loadDialog;
    private boolean isExit;
    private int loadViewId = 0;
    private View loadView = null;
    private int loadViewStyle = 0;
    private int loadDrawableColor = Color.TRANSPARENT;
    private float dimAmount = 0.2f;
    private float alpha = 1.0f;
    private int animId = 0;
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

    public void setLoadView(int loadViewId) {
        this.loadViewId = loadViewId;
        this.loadView = null;
    }

    public void setLoadView(View loadView) {
        this.loadView = loadView;
        this.loadViewId = 0;
    }

    public void setLoadViewStyle(int loadViewStyle) {
        this.loadViewStyle = loadViewStyle;
    }

    public void setDimAmount(float dimAmount) {
        if (dimAmount < 0 || dimAmount > 1) {
            dimAmount = 0.2f;
        }
        this.dimAmount = dimAmount;
    }

    public void setAlpha(float alpha) {
        if (alpha < 0 || alpha > 1) {
            alpha = 1;
        }
        this.alpha = alpha;
    }

    public void setAnimId(int alpha) {
        this.animId = alpha;
    }

    public void setLoadDrawableColor(int loadDrawableColor) {
        this.loadDrawableColor = loadDrawableColor;
    }

    private void setLoading(final Context context, View contentView, int styleId) {
        if (styleId == useDefFlag) {
            styleId = R.style.LoadStyle;
        }
        loadDialog = new Dialog(context, styleId);
        if (contentView == null) {
            contentView = LayoutInflater.from(context).inflate(R.layout.loading_default, null);
        }
        loadDialog.setContentView(contentView);
        setProgressBarColor(contentView);

        Window window = loadDialog.getWindow();
        loadDialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = window.getAttributes();
//        params.width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() * 3 / 4;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        params.alpha = this.alpha;
        params.dimAmount = this.dimAmount;
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        window.setAttributes(params);
        if (animId != useDefFlag) {
            window.setWindowAnimations(animId);
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        loadDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                resetAttr();
                if (isExit && context != null) {
                    Activity activity = findActivity(context);
                    if (activity != null) {
                        activity.finish();
                    }
                }
                isExit = false;
            }
        });
    }

    /*复原属性，防止某个loading修改属性影响后续showLoad*/
    private void resetAttr() {
        loadViewId = _loadViewId;
        loadView = _loadView;
        loadViewStyle = _loadViewStyle;
        loadDrawableColor = _loadDrawableColor;
        dimAmount = _dimAmount;
        alpha = _alpha;
        animId = _animId;
    }

    private void setProgressBarColor(View view) {
        if (loadDrawableColor == Color.TRANSPARENT) {
            return;
        }
        if (view == null) {
            return;
        }
        ProgressBar pb = view.findViewById(R.id.pb);
        if (pb == null) {
            return;
        }
        Drawable indeterminateDrawable = pb.getIndeterminateDrawable();
        if (indeterminateDrawable == null) {
            return;
        }
        indeterminateDrawable.mutate().setColorFilter(loadDrawableColor, PorterDuff.Mode.SRC_ATOP);
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
        if (loadViewId != useDefFlag) {
            showDialog(context, loadViewId, loadViewStyle);
        } else {
            showDialog(context, loadView, loadViewStyle);
        }
    }

    public void showDialog(Context context, int layoutId, int styleId) {
        if (context == null) {
            return;
        }
        if (layoutId == useDefFlag) {
            layoutId = R.layout.loading_default;
        }
        if (styleId == useDefFlag) {
            styleId = R.style.LoadStyle;
        }
        showDialog(context, LayoutInflater.from(context).inflate(layoutId, null), styleId);
    }

    public void showDialog(Context context, int styleId) {
        showDialog(context, null, styleId);
    }

    public void showDialog(Context context, View layout, int styleId) {
        if (context == null) {
            return;
        }
        if (actIsFinish(context)) {
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
        if (actIsFinish(context)) {
            loadDialog = null;
            return;
        }
        if (loadDialog.isShowing()) {
            Window window = loadDialog.getWindow();
            if (window == null) {
                loadDialog = null;
                isExit = false;
                return;
            }
            if (window.getWindowManager() == null) {
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

    public static void show(Context context, int layoutId, int styleId) {
        get().showDialog(context, layoutId, styleId);
    }

    public static void show(Context context, int styleId) {
        get().showDialog(context, styleId);
    }

    public static void show(Context context, View layout, int styleId) {
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

    public static Activity findActivity(Context context) {
        if (context == null) {
            return null;
        } else {
            if (context instanceof Activity) {
                return (Activity) context;
            } else if (context instanceof ContextWrapper) {
                ContextWrapper wrapper = (ContextWrapper) context;
                return findActivity(wrapper.getBaseContext());
            } else {
                return null;
            }
        }
    }

    public static boolean actIsFinish(Context context) {
        Activity activity = findActivity(context);
        if (activity == null) {
            return true;
        }
        if (activity.isFinishing()) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity.isDestroyed()) {
                return true;
            }
        }
        return false;
    }
}