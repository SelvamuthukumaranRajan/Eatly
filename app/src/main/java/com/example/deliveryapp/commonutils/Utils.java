package com.example.deliveryapp.commonutils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.deliveryapp.Login;
import com.example.deliveryapp.location.CurrentAddress;

public class Utils {

    private static ProgressDialog mProgressDialog;

    public static void DisplayToast(Context currentactivityname, String messagetext)
    {
        Toast.makeText(currentactivityname, messagetext, Toast.LENGTH_SHORT).show();
    }

    public static void Navigation(Context currentactivityname, Class<?> nextactivityname)
    {
        Intent i = new Intent(currentactivityname,nextactivityname);
        currentactivityname.startActivity(i);
    }

    public static void hideActionBar(Activity activity)
    {
        // Call before calling setContentView();
        if (activity != null) {
            activity.getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            if (activity.getActionBar() != null) {
                activity.getActionBar().hide();
            }
        }
    }

    public static void setFullScreen(Activity activity)
    {
        // Call before calling setContentView();
        activity.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void showAlertDialog(Context context, String title, String body)
    {
        showAlertDialog(context, title, body, null);
    }

    public static void showAlertDialog(Context context, String title, String body, DialogInterface.OnClickListener okListener)
    {
        if (okListener == null)
        {
            okListener = new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            };
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(body).setPositiveButton("OK", okListener);
        if (!TextUtils.isEmpty(title))
        {
            builder.setTitle(title);
        }
        builder.show();
    }

    public static void showProgressDialog(Context ctx, String title, String body, boolean isCancellable)
    {
        showProgressDialog(ctx, title, body, null, isCancellable);
    }

    public static void showProgressDialog(Context ctx, String title, String body, Drawable icon, boolean isCancellable)
    {

        if (ctx instanceof Activity)
        {
            if (!((Activity) ctx).isFinishing())
            {
                mProgressDialog = ProgressDialog.show(ctx, title, body, true);
                mProgressDialog.setIcon(icon);
                mProgressDialog.setCancelable(isCancellable);
            }
        }
    }

    public static boolean isProgressDialogVisible()
    {
        return (mProgressDialog != null);
    }

    public static void dismissProgressDialog()
    {
        if (mProgressDialog != null)
        {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }

    public static void onLoadSplashScreenTimer(Context context)
    {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (SharedPrefsUtils.getBoolean(SharedPrefsUtils.PREF_KEY.shareLoginStatus))
                {
                    Intent intent=new Intent(context, Login.class);
                    context.startActivity(intent);
                }
                else{
                    Intent intent=new Intent(context, CurrentAddress.class);
                    context.startActivity(intent);
                }
            }
        },6500);
    }

    public static String capitalizeString(String string)
    {
        if (string == null)
        {
            return null;
        }
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i]))
            {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            }
            else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'')
            { // You
                // can add other
                // chars here
                found = false;
            }
        } // end for
        return String.valueOf(chars);
    }

    public static String capitalizeString(String string, int start, int offset)
    {
        if (TextUtils.isEmpty(string))
        {
            return null;
        }
        String formattedString = string.substring(start, offset).toUpperCase() + string.substring(offset, string.length());
        return formattedString;
    }

    public static Dialog DisplayCustomCenterDialog(Context context, int resource) {
        Dialog customDialog = new Dialog(context);
        View dialogView = LayoutInflater.from(context).inflate(resource, null, false);
        customDialog.setContentView(dialogView);
        customDialog.setCancelable(false);
        customDialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        customDialog.getWindow().setAttributes(lp);

        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return customDialog;
    }

    public static Dialog DisplayCustomBottomDialog(Context context, int resource) {
        Dialog customDialog = new Dialog(context);
        View dialogView = LayoutInflater.from(context).inflate(resource, null, false);
        customDialog.setContentView(dialogView);
        customDialog.setCancelable(false);
        customDialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        customDialog.getWindow().setAttributes(lp);

        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return customDialog;
    }


}
