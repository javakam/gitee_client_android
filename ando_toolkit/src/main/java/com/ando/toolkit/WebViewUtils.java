package com.ando.toolkit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Patterns;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY;

/**
 * WebView简单配置
 *
 * @author majavakam
 * @date 2019-05-20 16:48:25
 */
public class WebViewUtils {

    public static void initWebView(Context context, WebView webview) {
        initWebView((Activity) context, webview);
    }

    public static void initWebView(Activity activity, WebView webview) {
        if (activity == null || webview == null) {
            return;
        }
        WebSettings webSettings = webview.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setTextZoom(100);//防止字体随系统设置的字体大小变化

        webSettings.setAllowUniversalAccessFromFileURLs(true);
        //webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        // url callback
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                Intent intent = new Intent();
                intent.setData(uri);
                // 分析协议 是否打开分享 or 登录
//                if (IntentUtil.diggingIntentData(intent, activity)) {
//                    return true;
//                }

                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
                //view.clearHistory();
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
            }

            @Override
            public void onReceivedTitle(WebView view, String title1) {
            }
        });
    }

    /**
     * WebView 嵌套在 ScrollView 中崩溃的问题
     * <p>
     * https://my.oschina.net/onlykc/blog/2050590
     */
    public static void fixAndroidLOLLIPOP(WebView webView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.setLayerType(View.LAYER_TYPE_NONE, null);
        }
    }

    /**
     * WebView 图片过宽
     * <p>
     * https://blog.csdn.net/minwenping/article/details/56878254
     */
    public static void autoFitImage(WebView webView) {

        final String fitRule = "javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                " img.style.maxWidth = '100%';img.style.height='auto';" +
                "}" +
                "})()";

        webView.evaluateJavascript(fitRule, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                L.w("autoFitImage success " + value);
            }
        });
    }

    public static void loadContent(WebView webView, String source) {
        loadContentWithBaseUrl(webView, null, source);
    }

    /**
     * <pre>
     *      webView.loadData(newSource, "text/html", "UTF-8");  //中文乱码问题
     *      val sss =
     *              "<p><script type='text/javascript' " +
     *              "  src='http://vd.zqrb.cn/admin/getvod/getvideo?key=b99dd7e5cfc1dadbb486326c68c8eb42&videoId=098ae9c0336f44f7b0990ea8433f45ff&isRePlay=1&isautoplay=1&id=358'> " +
     *              "</script></p><p>（策划 证券日报音视频中心）</p>"
     *      mWebView.loadUrl(sss)
     *
     *      mWebView.loadDataWithBaseURL("", sss, "text/html", "UTF-8", null)
     * </pre>
     */
    public static void loadContentWithBaseUrl(WebView webView, String baseUrl, String source) {
        if (webView != null) {
            final String newSource = StringUtils.noNull(source);
            String url = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                url = Html.fromHtml(newSource, FROM_HTML_MODE_LEGACY).toString();// for 24 api and more
            } else {
                url = Html.fromHtml(newSource).toString();// or for older api
            }

            //检查路径对否合法
            if (Patterns.WEB_URL.matcher(url).matches()) {
                webView.loadUrl(url);
            } else {
                //webView.loadData(newSource, "text/html", "UTF-8");  //中文乱码问题
                webView.loadDataWithBaseURL(baseUrl, newSource, "text/html", "UTF-8", null);

            }
        }
    }


}