package com.traffic.wifiapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.traffic.wifiapp.SplashActivity;
import com.traffic.wifiapp.common.WifiApplication;

import static com.traffic.wifiapp.utils.StringUtil.isEmpty;

/**
 * Created by Ray on 2017/5/5.
 * email：1452011874@qq.com
 */

public class SystemUtil {

    //设置透明状态栏
    public static void virtualStatusBar(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static void smoothSwitchScreen(Activity activity) {
        // 5.0以上修复了此bug
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup rootView = ((ViewGroup) activity.findViewById(android.R.id.content));
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
            rootView.setPadding(0, statusBarHeight, 0, 0);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public static void openWifi(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);//系统设置界面
        context.startActivity(intent);
    }

    //设置view背景颜色
    public static void setViewColorBackGround(View view, String backGroundColor) {
        if (!isEmpty(backGroundColor)) {
            view.setBackgroundColor(Color.parseColor(backGroundColor));
        }
    }



    /***
     * 得到屏幕分辨率高
     */
    @SuppressWarnings("deprecation")
    public static int getDisplayHeight(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getHeight();

    }

    /***
     * 得到屏幕分辨率宽
     */
    @SuppressWarnings("deprecation")
    public static int getDisplayWidth(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getWidth();

    }

    public static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).
                getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).
                getDefaultDisplay().getHeight();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = 0.0f;
        scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = 0.0f;
        scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 获取设备ID
     * */
    public static String getDeviceId(){
        String szImei="";
        TelephonyManager TelephonyMgr = (TelephonyManager) WifiApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        szImei = TelephonyMgr.getDeviceId();
        return szImei;
    }

    //进入应用设置
    public static void goToAppSetting(Context context){
        if(!(context instanceof Activity))return;
        Uri packageURI = Uri.parse("package:" +context.getPackageName());
        Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,packageURI);
        ((Activity)context).startActivityForResult(intent,SplashActivity.CHECK_PEERISSION_CODE);
    }
}
