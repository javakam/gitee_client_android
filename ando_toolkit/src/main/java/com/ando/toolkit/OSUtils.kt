package com.ando.toolkit

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.AppOpsManager
import android.content.Context
import android.content.res.Configuration
import android.os.Binder
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import com.ando.toolkit.AppUtils.getContext
import com.ando.toolkit.config.ScreenType
import java.io.File
import java.io.FileInputStream
import java.lang.reflect.Method
import java.util.*
import java.util.regex.Pattern

/**
 * 设备类型工具类
 *
 * @author changbao
 * @date 2019/3/22 10:56
 */
@SuppressLint("PrivateApi")
object OSUtils {

    private val PATTERN_FLYME_VERSION = Pattern.compile("(\\d+\\.){2}\\d")

    //
    const val ROM_MIUI = "miui"
    const val ROM_EMUI = "emui"
    const val ROM_FLYME = "flyme"
    const val ROM_BBK = "bbk"
    const val ROM_VIVO = "vivo"
    const val ROM_OPPO = "oppo"
    const val ROM_HUAWEI = "huawei"
    const val ROM_HONOR = "honor"
    const val ROM_SMARTISAN = "smartisan"
    const val ROM_QIKU = "qiku"
    const val ROM_360 = "360"
    const val ROM_ZTEC2016 = "zte c2016"
    const val ROM_ZUKZ1 = "zuk z1"
    private const val KEY_VERSION_MIUI = "ro.miui.ui.version.name"
    private const val KEY_VERSION_FLYME = "ro.build.display.id"
    private const val KEY_VERSION_EMUI = "ro.build.version.emui"
    private const val KEY_VERSION_OPPO = "ro.build.version.opporom"
    private const val KEY_VERSION_SMARTISAN = "ro.smartisan.version"
    private const val KEY_VERSION_VIVO = "ro.vivo.os.version"
    private const val ESSENTIAL = "essential"
    private val BOARD_MEIZU = arrayOf("m9", "M9", "mx", "MX")
    private var sMiuiVersionName: String? = null
    private var sFlymeVersionName: String? = null
    private var sEmuiVersionName: String? = null
    private var sOppoVersionName: String? = null
    private var sSmartisanVersionName: String? = null
    private var sVivoVersionName: String? = null
    private var sScreenType = 0
    private var sIsTabletChecked = false
    private const val sIsTabletValue = false
    private val BRAND = Build.BRAND.toLowerCase(Locale.ROOT)

    /**
     * 检验设备屏幕的尺寸
     */
    private fun checkScreenSize(context: Context): Int {
        val screenSize =
            context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        return if (screenSize >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            //证明是平板
            if (screenSize >= Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                ScreenType.BIG_TABLET
            } else {
                ScreenType.SMALL_TABLET
            }
        } else {
            ScreenType.PHONE
        }
    }

    /**
     * 判断是否平板设备
     *
     * @return true:平板,false:手机
     */
    val screenType: Int
        get() {
            if (sIsTabletChecked) {
                return sScreenType
            }
            sScreenType = checkScreenSize(getContext())
            sIsTabletChecked = true
            return sScreenType
        }

    /**
     * 是否是平板
     */
    val isTablet: Boolean
        get() = screenType == ScreenType.SMALL_TABLET || screenType == ScreenType.BIG_TABLET

    /**
     * 判断是否是MIUI系统
     */
    val isMIUI: Boolean
        get() = !TextUtils.isEmpty(sMiuiVersionName)
    val isMIUIV5: Boolean
        get() = "v5".equals(sMiuiVersionName, ignoreCase = true)
    val isMIUIV6: Boolean
        get() = "v6".equals(sMiuiVersionName, ignoreCase = true)
    val isMIUIV7: Boolean
        get() = "v7".equals(sMiuiVersionName, ignoreCase = true)
    val isMIUIV8: Boolean
        get() = "v8".equals(sMiuiVersionName, ignoreCase = true)
    val isMIUIV9: Boolean
        get() = "v9".equals(sMiuiVersionName, ignoreCase = true)
    val isMIUIV10: Boolean
        get() = "v10".equals(sMiuiVersionName, ignoreCase = true)
    val isMIUIV11: Boolean
        get() = "v11".equals(sMiuiVersionName, ignoreCase = true)
    val isMIUIV12: Boolean
        get() = "v12".equals(sMiuiVersionName, ignoreCase = true)

    //查不到默认高于5.2.4
    val isFlymeVersionHigher5_2_4: Boolean
        get() {
            //查不到默认高于5.2.4
            var isHigher = true
            if (sFlymeVersionName != null && "" != sFlymeVersionName) {
                val matcher = PATTERN_FLYME_VERSION.matcher(sFlymeVersionName)
                if (matcher.find()) {
                    val versionString = matcher.group()
                    if (!TextUtils.isEmpty(versionString) && "" != versionString) {
                        val version = versionString.split("\\.").toTypedArray()
                        if (version.size == 3) {
                            if (version[0].toInt() < 5) {
                                isHigher = false
                            } else if (version[0].toInt() > 5) {
                                isHigher = true
                            } else {
                                if (version[1].toInt() < 2) {
                                    isHigher = false
                                } else if (version[1].toInt() > 2) {
                                    isHigher = true
                                } else {
                                    if (version[2].toInt() < 4) {
                                        isHigher = false
                                    } else if (version[2].toInt() >= 5) {
                                        isHigher = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return isMeizu && isHigher
        }

    /**
     * 判断是否是 flyme 系统
     */
    val isFlyme: Boolean
        get() = !TextUtils.isEmpty(sFlymeVersionName) && sFlymeVersionName!!.contains(ROM_FLYME)

    //两种方式有待验证...
    //Log.e("123",!TextUtils.isEmpty(sSmartisanVersionName) && sSmartisanVersionName.contains(ROM_SMARTISAN));
    val isSmartisan: Boolean
        get() =//两种方式有待验证...
            //Log.e("123",!TextUtils.isEmpty(sSmartisanVersionName) && sSmartisanVersionName.contains(ROM_SMARTISAN));
            BRAND.contains(ROM_SMARTISAN)

    fun is360(): Boolean {
        return BRAND.contains(ROM_QIKU) || BRAND.contains(ROM_360)
    }

    val isMeizu: Boolean
        get() = isPhone(BOARD_MEIZU) || isFlyme

    /**
     * 判断是否为小米
     * https://dev.mi.com/doc/?p=254
     */
    val isXiaomi: Boolean
        get() = "xiaomi" == Build.MANUFACTURER.toLowerCase()
    val isVivo: Boolean
        get() = BRAND.contains(ROM_VIVO) || BRAND.contains(ROM_BBK)
    val isOppo: Boolean
        get() = BRAND.contains(ROM_OPPO)
    val isHuawei: Boolean
        get() = BRAND.contains(ROM_HUAWEI) || BRAND.contains(ROM_HONOR)
    val isEssentialPhone: Boolean
        get() = BRAND.contains(ESSENTIAL)

    /**
     * 判断是否为 ZUK Z1 和 ZTK C2016。
     * 两台设备的系统虽然为 android 6.0，但不支持状态栏icon颜色改变，因此经常需要对它们进行额外判断。
     */
    val isZUKZ1: Boolean
        get() {
            val board = Build.MODEL
            return board != null && board.toLowerCase().contains(ROM_ZUKZ1)
        }

    val isZTKC2016: Boolean
        get() {
            val board = Build.MODEL
            return board != null && board.toLowerCase().contains(ROM_ZTEC2016)
        }

    private fun isPhone(boards: Array<String>): Boolean {
        val board = Build.BOARD ?: return false
        for (board1 in boards) {
            if (board == board1) {
                return true
            }
        }
        return false
    }

    /**
     * 判断悬浮窗权限（目前主要用户魅族与小米的检测）
     */
    fun isFloatWindowOpAllowed(context: Context): Boolean {
        val version = Build.VERSION.SDK_INT
        return if (version >= Build.VERSION_CODES.KITKAT) {
            checkOp(context, 24) // 24 是 AppOpsManager.OP_SYSTEM_ALERT_WINDOW 的值，该值无法直接访问
        } else {
            try {
                context.applicationInfo.flags and 1 shl 27 == 1 shl 27
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun checkOp(context: Context, op: Int): Boolean {
        val version = Build.VERSION.SDK_INT
        if (version >= Build.VERSION_CODES.KITKAT) {
            val manager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            try {
                val method = manager.javaClass.getDeclaredMethod(
                    "checkOp",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType,
                    String::class.java
                )
                val property = method.invoke(
                    manager,
                    op,
                    Binder.getCallingUid(),
                    context.packageName
                ) as Int
                return AppOpsManager.MODE_ALLOWED == property
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }

    private fun getLowerCaseName(p: Properties, get: Method, key: String): String? {
        var name = p.getProperty(key)
        if (name == null) {
            try {
                name = get.invoke(null, key) as String
            } catch (ignored: Exception) {
            }
        }
        if (name != null) {
            name = name.toLowerCase(Locale.getDefault())
        }
        return name
    }

    init {
        val properties = Properties()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // android 8.0，读取 /system/uild.prop 会报 permission denied
            var fileInputStream: FileInputStream? = null
            try {
                fileInputStream =
                    FileInputStream(File(Environment.getRootDirectory(), "build.prop"))
                properties.load(fileInputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                fileInputStream?.close()
            }
        }
        val clzSystemProperties: Class<*>?
        try {
            clzSystemProperties = Class.forName("android.os.SystemProperties")
            val getMethod: Method = clzSystemProperties.getDeclaredMethod(
                "get",
                String::class.java
            )
            // miui
            sMiuiVersionName = getLowerCaseName(
                properties,
                getMethod,
                KEY_VERSION_MIUI
            )
            //flyme
            sFlymeVersionName = getLowerCaseName(
                properties,
                getMethod,
                KEY_VERSION_FLYME
            )
            //emui
            sEmuiVersionName = getLowerCaseName(
                properties,
                getMethod,
                KEY_VERSION_EMUI
            )
            //oppo
            sOppoVersionName = getLowerCaseName(
                properties,
                getMethod,
                KEY_VERSION_OPPO
            )
            //smartisan
            sSmartisanVersionName = getLowerCaseName(
                properties,
                getMethod,
                KEY_VERSION_SMARTISAN
            )
            //vivo
            sVivoVersionName = getLowerCaseName(
                properties,
                getMethod,
                KEY_VERSION_VIVO
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}