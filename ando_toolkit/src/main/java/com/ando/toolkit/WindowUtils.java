package com.ando.toolkit;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * Title:WindowUtils
 * <p>
 * Description:Window窗体工具类
 * </p>
 *
 * @author javakam
 * Date 2018/9/23 18:54
 */
public class WindowUtils {

    /**
     * 设置状态栏背景，一个Acitivty中只能调用一次，仅支持5.0以上
     * v21
     * <item name="android:windowTranslucentStatus">false</item>
     * <item name="android:windowTranslucentNavigation">true</item>
     * <item name="android:statusBarColor">@android:color/transparent</item>
     *
     * @param activity
     * @param drawable
     */
    public static void setStatusBarDrawable(Activity activity, Drawable drawable) {
        //7.0以上移除状态栏阴影
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(activity.getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {
                Log.e(WindowUtils.class.getSimpleName(), e.getMessage());
            }
        }

        if (drawable == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            FrameLayout rootView = (FrameLayout) activity.findViewById(android.R.id.content);
            int count = rootView.getChildCount();
            if (count > 0) {
                View layout = rootView.getChildAt(0);
                int statusBarHeight = getStatusBarHeight(activity);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) layout.getLayoutParams();
                layoutParams.topMargin = statusBarHeight;

                ImageView statusBarView;
                if (count > 1) {
                    statusBarView = (ImageView) rootView.getChildAt(1);
                } else {
                    statusBarView = new ImageView(activity);
                    LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
                    statusBarView.setScaleType(ImageView.ScaleType.FIT_XY);
                    statusBarView.setLayoutParams(viewParams);
                    rootView.addView(statusBarView);
                }
                statusBarView.setImageDrawable(drawable);
            }
        }
    }

    /**
     * 利用反射获取顶部状态栏高度
     */
    public static int getStatusBarHeight(Activity activity) {
        int result = 0; //获取状态栏高度的资源id
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 隐藏底部导航栏
     */
    public static void hideStatusBarBottom(Window window) {
        final View decorView = window.getDecorView();
        final int option = View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(option);
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(option);
                }
            }
        });
    }

    /**
     * 全屏显示
     */
    public static void hideStatusBar(Window window) {
        //全屏显示
        View view = window.getDecorView();
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

}
