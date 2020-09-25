package com.ando.library.views;

import android.view.View;

import com.ando.toolkit.L;

/**
 * Title: AbstractDoubleClickListener
 * <p>
 * Description: 双击监听 & 双击防抖
 * </p>
 *
 * @author javakam
 * @date 2020-04-15
 */
public abstract class AbstractDoubleClickListener implements View.OnClickListener {

    //默认双击事件响应间隔 300 毫秒
    private static final long DOUBLE_RESPONSE_TIME = 300;
    //双击防抖 1500 毫秒
    private static final long SHAKE_DURATION = 1500;

    private long lastClickTime;
    private long doubleRespDuration;
    private long shakeDuration;

    public AbstractDoubleClickListener() {
        this(DOUBLE_RESPONSE_TIME, SHAKE_DURATION);
    }

    public AbstractDoubleClickListener(long doubleRespDuration, long shakeDuration) {
        this.doubleRespDuration = doubleRespDuration;
        this.shakeDuration = shakeDuration;
    }

    @Override
    public void onClick(View v) {
        long currentTimeMillis = System.currentTimeMillis();
        long diffTimeMillis = currentTimeMillis - lastClickTime;

        if (diffTimeMillis < doubleRespDuration) {
            L.w("time","time 222");
            onDoubleClick(v);
        }

        lastClickTime = currentTimeMillis;

        // > shakeDuration 防抖: 需要大于这个时间差才可以再次响应时间
//        if (diffTimeMillis > shakeDuration) {
//            L.w("time","time 1111");
//        }


    }

    public abstract void onDoubleClick(View v);

}