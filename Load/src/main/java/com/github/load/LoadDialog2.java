package com.github.load;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;


/**
 * 进入页面加载的Dialog
 */
class LoadDialog2 extends Dialog {
    private KeyDownListener keyDownListener;




    public LoadDialog2(Context context, View view) {
        super(context);
    }
    public LoadDialog2(Context context, View view, int style) {
        super(context, style);
    }


    public KeyDownListener getKeyDownListener() {
        if (keyDownListener == null) {
            keyDownListener = new KeyDownListener() {
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean flag = getKeyDownListener().onKeyDown(keyCode, event);
        if (flag) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}