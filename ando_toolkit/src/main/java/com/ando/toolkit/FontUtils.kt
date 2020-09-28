package com.ando.toolkit

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import java.util.*

object FontUtils {

    fun initFont(context: Context, fontAssetName: String?) {
        val regular = Typeface.createFromAsset(context.assets, fontAssetName)
        replaceFont("MONOSPACE", regular)
    }

    private fun replaceFont(staticTypefaceFieldName: String, newTypeface: Typeface) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val newMap: MutableMap<String?, Typeface?> = HashMap()
            newMap[staticTypefaceFieldName] = newTypeface
            try {
                val staticField = Typeface::class.java.getDeclaredField("sSystemFontMap")
                staticField.isAccessible = true
                staticField[null] = newMap
            } catch (var5: IllegalAccessException) {
                var5.printStackTrace()
            } catch (var5: NoSuchFieldException) {
                var5.printStackTrace()
            }
        } else {
            try {
                val staticField = Typeface::class.java.getDeclaredField(staticTypefaceFieldName)
                staticField.isAccessible = true
                staticField[null] = newTypeface
            } catch (var4: Exception) {
                var4.printStackTrace()
            }
        }
    }
}