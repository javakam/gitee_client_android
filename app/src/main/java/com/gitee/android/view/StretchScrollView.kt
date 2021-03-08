package com.gitee.android.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView

/**
 * A ScrollView which can scroll to (0,0) when pull down or up.
 *
 * @author markmjw
 * @date 2014-04-30
 */
class StretchScrollView : ScrollView {
    private var mChildRootView: View? = null
    private var mTouchY = 0f
    private var mTouchStop = false
    private var mScrollY = 0
    private var mScrollDy = 0

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (MSG_REST_POSITION == msg.what) {
                if (mScrollY != 0 && mTouchStop) {
                    mScrollY -= mScrollDy
                    if (mScrollDy < 0 && mScrollY > 0 || mScrollDy > 0 && mScrollY < 0) {
                        mScrollY = 0
                    }
                    mChildRootView!!.scrollTo(0, mScrollY)
                    // continue scroll after 20ms
                    sendEmptyMessageDelayed(MSG_REST_POSITION, 20)
                }
            }
        }
    }

    constructor(context: Context?) : this(context, null, 0)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        // set scroll mode
        overScrollMode = OVER_SCROLL_NEVER
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            // when finished inflating from layout xml, get the first child view
            mChildRootView = getChildAt(0)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            mTouchY = ev.y
        }
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (null != mChildRootView) {
            doTouchEvent(ev)
        }
        return super.onTouchEvent(ev)
    }

    private fun doTouchEvent(ev: MotionEvent) {
        when (ev.action) {
            MotionEvent.ACTION_UP -> {
                mScrollY = mChildRootView!!.scrollY
                if (mScrollY != 0) {
                    mTouchStop = true
                    mScrollDy = (mScrollY / 10.0f).toInt()
                    mHandler.sendEmptyMessage(MSG_REST_POSITION)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val nowY = ev.y
                val deltaY = (mTouchY - nowY).toInt()
                mTouchY = nowY
                if (isNeedMove) {
                    val offset = mChildRootView!!.scrollY
                    if (offset < MAX_SCROLL_HEIGHT && offset > -MAX_SCROLL_HEIGHT) {
                        mChildRootView!!.scrollBy(0, (deltaY * SCROLL_RATIO).toInt())
                        mTouchStop = false
                    }
                }
            }
            else -> {
            }
        }
    }

    private val isNeedMove: Boolean
        get() {
            val viewHeight = mChildRootView!!.measuredHeight
            val scrollHeight = height
            val offset = viewHeight - scrollHeight
            val scrollY = scrollY
            return scrollY == 0 || scrollY == offset
        }

    companion object {
        private const val MSG_REST_POSITION = 0x01

        /** The max scroll height.  */
        private const val MAX_SCROLL_HEIGHT = 400

        /** Damping, the smaller the greater the resistance  */
        private const val SCROLL_RATIO = 0.4f
    }
}