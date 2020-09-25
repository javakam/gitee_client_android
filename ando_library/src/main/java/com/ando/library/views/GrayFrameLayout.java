package com.ando.library.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * https://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650830296&idx=1&sn=a9f44ac6cd9ec53149e59a380403d04d&chksm=80b7a146b7c02850a96c5300e967f01fb4d58c1c9d320889fae043fd7b12cf42fde741722d52&scene=126&sessionid=1586088807&key=d8f7014a0480479388044a24ea66c7eddad0652250cd0ed1c25b01ad0e08f5171352f18457a060475e0d84e674982e1ced5c57763dac1ff52b817a5a0fdb5d22773d2b68a63568d73aa7246cc9c7b3ee&ascene=1&uin=MTc2MzYzMzc2MQ%3D%3D&devicetype=Windows+10&version=62080079&lang=zh_CN&exportkey=AUTJTMThNssgS87OFX3kMdA%3D&pass_ticket=TfZROikPmeKFmDh1qldS7qmv3lYejVybp0z9n3SjnamhHS5LCrgXaA617EMrRnK4
 *
 * <pre>
 *     WebView 部分情况下会显示异常 , 关闭硬件加速即可 :  mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
 * </pre>
 */
public class GrayFrameLayout extends FrameLayout {

    private Paint mPaint = new Paint();

    public GrayFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        mPaint.setColorFilter(new ColorMatrixColorFilter(cm));

        // java.lang.ClassCastException: android.view.ContextThemeWrapper cannot be cast to android.app.Activity
        if (context instanceof Activity) {
            ((Activity) context).getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        canvas.restore();
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
    }

}