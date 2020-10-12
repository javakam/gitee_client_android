package com.ando.library.base

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ando.library.base.BaseApplication.Companion.exit
import com.ando.library.base.BaseApplication.Companion.isGray
import com.ando.library.views.GrayFrameLayout
import com.ando.toolkit.StringUtils.noNull
import com.ando.toolkit.ext.ToastUtils
import com.ando.toolkit.ext.toastShort
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Title:BaseActivity
 *
 * Description:
 *
 * @author changbao
 * @date 2019/3/17 13:17
 */
abstract class BaseActivity : AppCompatActivity(), IBaseInterface {
    /**
     * 系统DecorView的根View
     */
    protected var mView: View? = null

    /**
     * 是否退出App
     */
    private var isExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        initActivityStyle()
        super.onCreate(savedInstanceState)
        val layoutId = layoutId
        if (layoutId > 0) {
            setContentView(layoutId)
        } else {
            setContentView(layoutView)
        }
        mView = findViewById(R.id.content)
        initView(savedInstanceState)
        initListener()
        initData()
    }

    // 灰度处理 : WebView 有时会显示异常
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        if (isGray) {
            if ("FrameLayout" == name) {
                val count = attrs.attributeCount
                for (i in 0 until count) {
                    val attributeName = attrs.getAttributeName(i)
                    val attributeValue = attrs.getAttributeValue(i)
                    if ("id" == attributeName) {
                        val id = attributeValue.substring(1).toInt()
                        val idVal = resources.getResourceName(id)
                        if ("android:id/content" == idVal) {
                            //因为这是API 23之后才能改变的，所以你的判断版本
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                //获取窗口区域
                                val window = this.window
                                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                                //设置状态栏颜色
                                window.statusBarColor = Color.parseColor("#858585")
                                //设置显示为白色背景，黑色字体
//                            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                            }
                            // grayFrameLayout.setBackgroundDrawable(getWindow().getDecorView().getBackground());
                            return GrayFrameLayout(context, attrs)
                        }
                    }
                }
            }
        }
        return super.onCreateView(name, context, attrs)
    }

    protected fun initActivityStyle() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * 重写 getResource 方法，防止系统字体影响
     *
     * https://www.jianshu.com/p/5effff3db399
     */
    override fun getResources(): Resources { //禁止app字体大小跟随系统字体大小调节
        val resources = super.getResources()
        if (resources != null && resources.configuration.fontScale != 1.0f) {
            val configuration = resources.configuration
            configuration.fontScale = 1.0f
            resources.updateConfiguration(configuration, resources.displayMetrics)
            // createConfigurationContext(configuration);
        }
        return resources
    }

    override fun onDestroy() {
        super.onDestroy()
        mView = null
    }

    fun getStringArray(@ArrayRes id: Int): List<String> = listOf(*resources.getStringArray(id))

    fun showMessage(message: String?) {
        if (mView != null) {
            Snackbar.make(mView!!, message!!, Snackbar.LENGTH_SHORT).show()
            return
        }
        toastShort(message)
    }

    /**
     * 设置ToolBar和DrawerLayout
     */
    fun setupToolBar(toolbarId: Int, toolBarTitleId: Int, title: String?) {
        val toolbar = findViewById<View>(toolbarId) as Toolbar
        //设置标题必须在setSupportActionBar之前才有效
        val toolBarTitle = toolbar.findViewById<View>(toolBarTitleId) as TextView
        toolBarTitle.gravity = Gravity.START or Gravity.CENTER_VERTICAL
        toolBarTitle.text = title
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true) //设置返回键可用
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * 连续点击两次退出App
     */
    @SuppressLint("CheckResult")
    protected fun exitBy2Click(delay: Long, @StringRes text: Int) {
        if (!isExit) {
            isExit = true // 准备退出
            toastShort(text)
            // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            Flowable.timer(delay, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe { isExit = false }
        } else {
            exit()
            //or 触发 Home 事件
//            Intent backHome = new Intent(Intent.ACTION_MAIN);
//            backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            backHome.addCategory(Intent.CATEGORY_HOME);
//            startActivity(backHome);
        }
    }
}