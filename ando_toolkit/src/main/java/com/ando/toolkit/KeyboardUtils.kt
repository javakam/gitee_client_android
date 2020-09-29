package com.ando.toolkit

import android.R
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.view.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.ando.toolkit.ToolKit.getContext
import java.util.*

/**
 * 软键盘工具
 *
 * @author javakam
 * @date 2019/1/14 下午10:04
 */
class KeyboardUtils : OnGlobalLayoutListener {
    private var mCallback: SoftKeyboardToggleListener?
    private var mRootView: View
    private var prevValue: Boolean? = null
    private var mScreenDensity = 1f

    //
    private val MAGIC_NUMBER = 200
    private val sListenerMap = HashMap<SoftKeyboardToggleListener, KeyboardUtils>()

    interface SoftKeyboardToggleListener {
        fun onToggleSoftKeyboard(isVisible: Boolean)
    }

    override fun onGlobalLayout() {
        val r = Rect()
        mRootView.getWindowVisibleDisplayFrame(r)
        val heightDiff = mRootView.rootView.height - (r.bottom - r.top)
        val dp = heightDiff / mScreenDensity
        val isVisible = dp > MAGIC_NUMBER
        if (mCallback != null && (prevValue == null || isVisible != prevValue)) {
            prevValue = isVisible
            mCallback!!.onToggleSoftKeyboard(isVisible)
        }
    }

    private fun removeListener() {
        mCallback = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mRootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
        } else {
            mRootView.viewTreeObserver.removeGlobalOnLayoutListener(this)
        }
    }

    private constructor(act: Activity, listener: SoftKeyboardToggleListener) {
        mCallback = listener
        mRootView = (act.findViewById<View>(R.id.content) as ViewGroup).getChildAt(0)
        mRootView.viewTreeObserver.addOnGlobalLayoutListener(this)
        mScreenDensity = act.resources.displayMetrics.density
    }

    private constructor(viewGroup: ViewGroup, listener: SoftKeyboardToggleListener) {
        mCallback = listener
        mRootView = viewGroup
        mRootView.viewTreeObserver.addOnGlobalLayoutListener(this)
        mScreenDensity = viewGroup.resources.displayMetrics.density
    }

    /**
     * Add a new keyboard listener
     *
     * @param act      calling activity
     * @param listener callback
     */
    fun addKeyboardToggleListener(act: Activity, listener: SoftKeyboardToggleListener) {
        removeKeyboardToggleListener(listener)
        sListenerMap[listener] = KeyboardUtils(act, listener)
    }

    /**
     * Add a new keyboard listener
     *
     * @param act      calling activity
     * @param listener callback
     */
    fun addKeyboardToggleListener(act: ViewGroup, listener: SoftKeyboardToggleListener) {
        removeKeyboardToggleListener(listener)
        sListenerMap[listener] = KeyboardUtils(act, listener)
    }

    /**
     * Remove a registered listener
     *
     * @param listener [SoftKeyboardToggleListener]
     */
    fun removeKeyboardToggleListener(listener: SoftKeyboardToggleListener) {
        if (sListenerMap.containsKey(listener)) {
            val k = sListenerMap[listener]
            k!!.removeListener()
            sListenerMap.remove(listener)
        }
    }

    /**
     * Remove all registered keyboard listeners
     */
    fun removeAllKeyboardToggleListeners() {
        for (l in sListenerMap.keys) {
            sListenerMap[l]!!.removeListener()
        }
        sListenerMap.clear()
    }

    /**
     * Manually toggle soft keyboard visibility
     *
     * @param context calling context
     */
    fun toggleKeyboardVisibility(context: Context) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    /**
     * Force closes the soft keyboard
     *
     * @param activeView the view with the keyboard focus
     */
    fun forceCloseKeyboard(activeView: View?) {
        if (activeView != null) {
            val manager =
                activeView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(
                activeView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    /**
     * 软键盘以覆盖当前界面的形式出现
     *
     * @param activity
     */
    fun setSoftInputAdjustNothing(activity: Activity) {
        activity.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
                    or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        )
    }

    /**
     * 软键盘以顶起当前界面的形式出现, 注意这种方式会使得当前布局的高度发生变化，触发当前布局onSizeChanged方法回调，这里前后高度差就是软键盘的高度了
     *
     * @param activity
     */
    fun setSoftInputAdjustResize(activity: Activity) {
        activity.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                    or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        )
    }

    /**
     * 软键盘以上推当前界面的形式出现, 注意这种方式不会改变布局的高度
     *
     * @param activity
     */
    fun setSoftInputAdjustPan(activity: Activity) {
        activity.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                    or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        )
    }

    /**
     * 禁用物理返回键
     *
     *
     * 使用方法：
     *
     * 需重写 onKeyDown
     *
     * @param keyCode
     * @return
     * @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
     * return KeyboardUtils.onDisableBackKeyDown(keyCode) && super.onKeyDown(keyCode, event) ;
     * }
     */
    fun onDisableBackKeyDown(keyCode: Int): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> return false
            KeyEvent.KEYCODE_HOME -> return false
            else -> {
            }
        }
        return true
    }

    /**
     * 点击屏幕空白区域隐藏软键盘
     *
     * 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     *
     * 需重写 dispatchTouchEvent
     *
     * @param ev
     * @param activity 窗口
     * @return
     */
    fun dispatchTouchEvent(ev: MotionEvent, activity: Activity) {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = activity.currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                closeSoftInput(v)
            }
        }
    }

    /**
     * 点击屏幕空白区域隐藏软键盘
     *
     * 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     *
     * 需重写 dispatchTouchEvent
     *
     * @param ev
     * @param dialog 窗口
     * @return
     */
    fun dispatchTouchEvent(ev: MotionEvent, dialog: Dialog) {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = dialog.currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                closeSoftInput(v)
            }
        }
    }

    /**
     * 点击屏幕空白区域隐藏软键盘
     *
     * 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     *
     * 需重写 dispatchTouchEvent
     *
     * @param ev
     * @param focusView 聚焦的view
     * @return
     */
    fun dispatchTouchEvent(ev: MotionEvent, focusView: View?) {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            if (isShouldHideKeyboard(focusView, ev)) {
                closeSoftInput(focusView)
            }
        }
    }

    /**
     * 点击屏幕空白区域隐藏软键盘
     *
     * 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     *
     * 需重写 dispatchTouchEvent
     *
     * @param ev
     * @param window 窗口
     * @return
     */
    fun dispatchTouchEvent(ev: MotionEvent, window: Window) {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = window.currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                closeSoftInput(v)
            }
        }
    }

    /**
     * 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     *
     * @param v
     * @param event
     * @return
     */
    fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }

    /**
     * 切换软键盘显示与否状态
     */
    fun toggleSoftInput() {
        val imm = getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    /**
     * 动态隐藏软键盘
     *
     * @param view 视图
     */
    fun closeSoftInput(view: View?) = closeSoftInput(getContext(), view)

    /**
     * 关闭已经显示的输入法窗口
     *
     * @param activity 上下文对象
     */
    fun closeSoftInput(activity: Activity) = closeSoftInput(activity, activity.window.decorView)

    /**
     * 关闭已经显示的输入法窗口
     *
     * @param context      上下文对象
     * @param focusingView 输入法所在焦点的View
     */
    fun closeSoftInput(context: Context, focusingView: View?) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(
            focusingView!!.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN
        )
    }

    /**
     * 修复软键盘内存泄漏
     *
     * 在[Activity#onDestroy()][.]中使用
     *
     * @param context context
     */
    fun fixSoftInputLeaks(context: Context?) {
        if (context == null) return
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            ?: return
        val strArr = arrayOf("mCurRootView", "mServedView", "mNextServedView")
        for (i in strArr.indices) {
            try {
                val declaredField = imm.javaClass.getDeclaredField(strArr[i]) ?: continue
                if (!declaredField.isAccessible) {
                    declaredField.isAccessible = true
                }
                val obj = declaredField[imm]
                if (obj == null || obj !is View) {
                    continue
                }
                if (obj.context === context) {
                    declaredField[imm] = null
                } else {
                    return
                }
            } catch (th: Throwable) {
                th.printStackTrace()
            }
        }
    }
}