package com.ando.toolkit;

import android.content.Context;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;

/**
 * Title: StringExpandUtils
 * <p>
 * Description:
 * StaticLayout 源码分析 https://jaeger.itscoder.com/android/2016/08/05/staticlayout-source-analyse.html
 * Android TextView实现查看全部和收起功能 https://www.jianshu.com/p/838d407e0df0
 *
 * </p>
 *
 * @author javakam
 * @date 2020/5/10  11:06
 */
public class StringExpandUtils {

    private static volatile StringExpandUtils instance;

    private StringExpandUtils() {
    }

    public static StringExpandUtils getInstance() {
        if (instance == null) {
            synchronized (StringExpandUtils.class) {
                if (instance == null) {
                    instance = new StringExpandUtils();
                }
            }
        }
        return instance;
    }

    private int maxLine = 3;

    private SpannableString elipseString;//收起的文字
    private SpannableString notElipseString;//展开的文字

    public SpannableString getElipseString() {
        return elipseString;
    }

    public SpannableString getNotElipseString() {
        return notElipseString;
    }

    //endText "...[详情]"   "...查看全部"
    public void getLastIndexForLimitPure(Context context, TextView tv, int maxLine,
                                         String content, String endText, @ColorInt int endTextColor,
                                         int endTextBoundWithColor,
                                         View.OnClickListener l) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(endText)) {
            return;
        }
        //获取TextView的画笔对象
        TextPaint paint = tv.getPaint();
        //每行文本的布局宽度
        int width = context.getResources().getDisplayMetrics().widthPixels - dip2px(context, 25);
        //实例化StaticLayout 传入相应参数
        StaticLayout staticLayout = new StaticLayout(content, paint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            StaticLayout.Builder.obtain(content,0,content.length(),paint,1);
//        }

        //判断content是行数是否超过最大限制行数3行
        if (staticLayout.getLineCount() > maxLine) {
            //获取到第三行最后一个文字的下标
            int index = staticLayout.getLineStart(maxLine) - 1;
            //定义收起后的文本内容
            //String substring = content.substring(0, index - 5) +  "...查看全部";
            String substring = content.substring(0, index - 5) + endText;
            SpannableString elipseString = new SpannableString(substring);
            //给查看全部设成蓝色
            elipseString.setSpan(new ForegroundColorSpan(endTextColor), substring.length() - endTextBoundWithColor, substring.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置收起后的文本内容
            tv.setText(elipseString);
            tv.setOnClickListener(l);
            //将 TextView 设成选中状态 true 用来表示文本未展示完全的状态,false 表示完全展示状态，用于点击时的判断
            tv.setSelected(true);
        }
    }

    public void getLastIndexForLimit(Context context, TextView tv, int maxLine, String content, @ColorInt int endTextColor, View.OnClickListener l) {
        //获取TextView的画笔对象
        TextPaint paint = tv.getPaint();
        //每行文本的布局宽度
        int width = context.getResources().getDisplayMetrics().widthPixels - dip2px(context, 40);
        //实例化StaticLayout 传入相应参数
        StaticLayout staticLayout = new StaticLayout(content, paint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        //StaticLayout.Builder builder = StaticLayout.Builder.obtain(content,);

        //判断content是行数是否超过最大限制行数3行
        if (staticLayout.getLineCount() > maxLine) {
            //定义展开后的文本内容
            String string1 = content + "    收起";
            notElipseString = new SpannableString(string1);
            //给收起两个字设成蓝色
            notElipseString.setSpan(new ForegroundColorSpan(endTextColor), string1.length() - 2, string1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //获取到第三行最后一个文字的下标
            int index = staticLayout.getLineStart(maxLine) - 1;
            //定义收起后的文本内容
            //String substring = content.substring(0, index - 5) + "..." + "查看全部";
            String substring = content.substring(0, index - 5) + "..." + "[详情]";
            elipseString = new SpannableString(substring);
            //给查看全部设成蓝色
            elipseString.setSpan(new ForegroundColorSpan(endTextColor), substring.length() - 4, substring.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置收起后的文本内容
            tv.setText(elipseString);
            tv.setOnClickListener(l);
            //将 TextView 设成选中状态 true用来表示文本未展示完全的状态,false表示完全展示状态，用于点击时的判断
            tv.setSelected(true);
        } else {
            //没有超过 直接设置文本
            tv.setText(content);
            tv.setOnClickListener(null);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context mContext, float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}