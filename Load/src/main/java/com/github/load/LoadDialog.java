package com.github.load;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;


/**
 * 进入页面加载的Dialog
 */
class LoadDialog extends Dialog {

    public LoadDialog(Context context, View view) {
        super(context);
    }
    public LoadDialog(Context context, View view, int style) {
        super(context, style);
    }

}