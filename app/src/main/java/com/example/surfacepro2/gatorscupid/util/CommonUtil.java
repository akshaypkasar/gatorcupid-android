package com.example.surfacepro2.gatorscupid.util;

import android.content.Context;
import android.widget.Toast;

public class CommonUtil {

    public static void showToast(Context context, int duration, String message) {
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
