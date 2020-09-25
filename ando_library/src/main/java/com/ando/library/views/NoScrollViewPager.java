package com.ando.library.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Title: NoScrollViewPager
 * <p>
 * Description:不能左右划的ViewPager
 * <p>
 * 主要原理是利用Android系统的事件分发机制。
 * 1. 首先在Action_Down事件ViewGroup不能够拦截掉事件，也就是ViewPager不处理滑动事件
 * 2. 如果子View没有消费本次事件，那么事件通过冒泡方式传递到ViewPager的时候也不消费该事件；
 * </p>
 * Author javakam
 * Date 2018/10/25  11:24
 */
public class NoScrollViewPager extends ViewPager {

    private boolean enableScroll;

    public NoScrollViewPager(Context context) {
        this(context, null);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 表示事件是否拦截, 返回false表示不拦截, 可以让嵌套在内部的recyclerview、viewpager相应左右滑动的事件不受干扰
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isEnableScroll()) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    /**
     * 重写onTouchEvent事件,什么都不用做
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isEnableScroll()) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    public boolean isEnableScroll() {
        return enableScroll;
    }

    public void setEnableScroll(boolean enableScroll) {
        this.enableScroll = enableScroll;
    }

}