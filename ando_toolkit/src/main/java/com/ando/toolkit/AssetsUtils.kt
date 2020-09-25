package com.ando.toolkit

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Title: AssetsUtils
 *
 *
 * Description: 读取 main -> assets 目录下的文件
 * <pre>
 * eg: AssetsUtils.getBeanByClass(Application.get(), "video.json", VideoBean.class);
 * </pre>
 *
 * @author javakam
 * @date 2019/11/7  10:12
 */
object AssetsUtils {

    @JvmStatic
    fun getJson(context: Context, fileName: String): String {
        //将json数据变成字符串
        val stringBuilder = StringBuilder()
        var bf: BufferedReader? = null
        try {
            //获取assets资源管理器
            val assetManager = context.assets
            //通过管理器打开文件并读取
            bf = BufferedReader(InputStreamReader(assetManager.open(fileName)))
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bf?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return stringBuilder.toString()
    }

    //    public static <T extends Object> T getBeanByClass(@NonNull Context context, @NonNull String fileName, @NonNull Class<T> clz) {
    //        return new Gson().fromJson(getJson(context, fileName), clz);
    //    }

    //    public static <T extends Object> List<T> getBeanByType(@NonNull Context context, @NonNull String fileName, @NonNull Type type) {
    //        return new Gson().fromJson(getJson(context, fileName), type);
    //    }
}