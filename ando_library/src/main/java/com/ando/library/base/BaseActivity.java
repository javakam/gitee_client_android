package com.ando.library.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ando.library.views.GrayFrameLayout;
import com.ando.toolkit.StringUtils;
import com.ando.toolkit.ext.ToastUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Title:BaseActivity
 * <p>
 * Description:
 * </p>
 *
 * @author javakam
 * @date 2019/3/17 13:17
 */
@SuppressLint("SourceLockedOrientationActivity")
public abstract class BaseActivity extends AppCompatActivity implements IBaseInterface {

    /**
     * 系统DecorView的根View
     */
    protected View mView;

    /**
     * 是否退出App
     */
    private boolean isExit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initActivityStyle();
        super.onCreate(savedInstanceState);

        int layoutId = getLayoutId();
        if (layoutId > 0) {
            setContentView(layoutId);
        } else {
            setContentView(getLayoutView());
        }
        mView = findViewById(android.R.id.content);

        initView(savedInstanceState);
        initListener();
        initData();
    }

    // 灰度处理 : WebView 有时会显示异常
    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if (BaseApplication.Companion.isGray()) {
            if ("FrameLayout".equals(name)) {
                int count = attrs.getAttributeCount();
                for (int i = 0; i < count; i++) {
                    String attributeName = attrs.getAttributeName(i);
                    String attributeValue = attrs.getAttributeValue(i);
                    if ("id".equals(attributeName)) {
                        int id = Integer.parseInt(attributeValue.substring(1));
                        String idVal = getResources().getResourceName(id);
                        if ("android:id/content".equals(idVal)) {
                            //因为这是API 23之后才能改变的，所以你的判断版本
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                //获取窗口区域
                                Window window = this.getWindow();
                                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                //设置状态栏颜色
                                window.setStatusBarColor(Color.parseColor("#858585"));
                                //设置显示为白色背景，黑色字体
//                            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                            }
                            // grayFrameLayout.setBackgroundDrawable(getWindow().getDecorView().getBackground());
                            return new GrayFrameLayout(context, attrs);
                        }
                    }
                }
            }
        }
        return super.onCreateView(name, context, attrs);
    }

    protected void initActivityStyle() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 重写 getResource 方法，防止系统字体影响
     * <p>
     * https://www.jianshu.com/p/5effff3db399
     */
    @Override
    public Resources getResources() {//禁止app字体大小跟随系统字体大小调节
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            final android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//            createConfigurationContext(configuration);
        }
        return resources;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mView = null;
    }

    /**
     * 获取String资源
     *
     * @param id
     * @return
     */
    protected List<String> getStringArray(@ArrayRes int id) {
        return Arrays.asList(getResources().getStringArray(id));
    }

    /**
     * 显示提示信息
     *
     * @param message
     */
    public void showMessage(String message) {
        if (mView != null) {
            Snackbar.make(mView, message, Snackbar.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 关闭输入法
     */
    public void hideKeyboard(View view) {
        if (view == null) {
            view = this.getCurrentFocus();
        }
        if (view != null) {
            ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 设置全屏
     */
    public void setFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置ToolBar和DrawerLayout
     */
    public void setupToolBar(int toolbarId, int toolBarTitleId, String title) {
        Toolbar toolbar = (Toolbar) findViewById(toolbarId);
        //设置标题必须在setSupportActionBar之前才有效
        TextView toolBarTitle = (TextView) toolbar.findViewById(toolBarTitleId);
        toolBarTitle.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        toolBarTitle.setText(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 连续点击两次退出App
     */
    @SuppressLint("CheckResult")
    protected void exitBy2Click(long delay, @StringRes int text) {
        if (!isExit) {
            isExit = true; // 准备退出
            shortToast(text);
            //showMessage(getString(text));

            // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            Flowable.timer(delay, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .subscribe(aLong -> isExit = false);
        } else {
            //真正退出
            BaseApplication.Companion.exit();

            //触发 Home 事件
//            Intent backHome = new Intent(Intent.ACTION_MAIN);
//            backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            backHome.addCategory(Intent.CATEGORY_HOME);
//            startActivity(backHome);
        }
    }

    public void shortToast(@StringRes int text) {
        ToastUtils.shortToast(StringUtils.noNull(getString(text)));
    }

    public void shortToast(String text) {
        ToastUtils.shortToast(StringUtils.noNull(text));
    }

    public void longToast(@StringRes int text) {
        ToastUtils.longToast(StringUtils.noNull(getString(text)));
    }

    public void longToast(String text) {
        ToastUtils.longToast(StringUtils.noNull(text));
    }

}