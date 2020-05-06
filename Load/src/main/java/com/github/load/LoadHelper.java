package com.github.load;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

class LoadHelper {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity.isDestroyed()) {
                return true;
            }
        }
        if (activity.isFinishing()) {
            return true;
        }
        return false;
    }
}
