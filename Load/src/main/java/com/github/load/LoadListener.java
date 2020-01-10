package com.github.load;

import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;

public interface LoadListener {
    void show(View dialogView);
    void dismiss(DialogInterface dialog);
}
