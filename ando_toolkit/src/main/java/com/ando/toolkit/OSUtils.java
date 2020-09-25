package com.ando.toolkit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.ando.toolkit.config.ScreenType;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设备类型工具类
 *
 * @author javakam
 * @date 2019/3/22 10:56
 */
@SuppressLint("PrivateApi")
public class OSUtils {

    private final static Pattern PATTERN_FLYME_VERSION = Pattern.compile("(\\d+\\.){2}\\d");
    //
    public static final String ROM_MIUI = "miui";
    public static final String ROM_EMUI = "emui";
    public final static String ROM_FLYME = "flyme";
    public static final String ROM_BBK = "bbk";
    public static final String ROM_VIVO = "vivo";
    public static final String ROM_OPPO = "oppo";
    public static final String ROM_HUAWEI = "huawei";
    public static final String ROM_HONOR = "honor";
    public static final String ROM_SMARTISAN = "smartisan";
    public static final String ROM_QIKU = "qiku";
    public static final String ROM_360 = "360";
    public final static String ROM_ZTEC2016 = "zte c2016";
    public final static String ROM_ZUKZ1 = "zuk z1";

    private final static String KEY_VERSION_MIUI = "ro.miui.ui.version.name";
    private final static String KEY_VERSION_FLYME = "ro.build.display.id";
    private static final String KEY_VERSION_EMUI = "ro.build.version.emui";
    private static final String KEY_VERSION_OPPO = "ro.build.version.opporom";
    private static final String KEY_VERSION_SMARTISAN = "ro.smartisan.version";
    private static final String KEY_VERSION_VIVO = "ro.vivo.os.version";

    private final static String ESSENTIAL = "essential";
    private final static String[] BOARD_MEIZU = {"m9", "M9", "mx", "MX"};

    private static String sMiuiVersionName;
    private static String sFlymeVersionName;
    private static String sEmuiVersionName;
    private static String sOppoVersionName;
    private static String sSmartisanVersionName;
    private static String sVivoVersionName;

    private static int sScreenType;
    private static boolean sIsTabletChecked;
    private static boolean sIsTabletValue ;

    private static final String BRAND = Build.BRAND.toLowerCase();

    private OSUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    static {
        Properties properties = new Properties();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // android 8.0，读取 /system/uild.prop 会报 permission denied
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
                properties.load(fileInputStream);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Utils.closeIOQuietly(fileInputStream);
            }
        }

        Class<?> clzSystemProperties = null;
        try {
            clzSystemProperties = Class.forName("android.os.SystemProperties");
            Method getMethod = clzSystemProperties.getDeclaredMethod("get", String.class);
            // miui
            sMiuiVersionName = getLowerCaseName(properties, getMethod, KEY_VERSION_MIUI);
            //flyme
            sFlymeVersionName = getLowerCaseName(properties, getMethod, KEY_VERSION_FLYME);
            //emui
            sEmuiVersionName = getLowerCaseName(properties, getMethod, KEY_VERSION_EMUI);
            //oppo
            sOppoVersionName = getLowerCaseName(properties, getMethod, KEY_VERSION_OPPO);
            //smartisan
            sSmartisanVersionName = getLowerCaseName(properties, getMethod, KEY_VERSION_SMARTISAN);
            //vivo
            sVivoVersionName = getLowerCaseName(properties, getMethod, KEY_VERSION_VIVO);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //=======================屏幕尺寸===========================//

    /**
     * 检验设备屏幕的尺寸
     *
     * @param context
     * @return
     */
    private static int checkScreenSize(Context context) {
        int screenSize = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            //证明是平板
            if (screenSize >= Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                return ScreenType.BIG_TABLET;
            } else {
                return ScreenType.SMALL_TABLET;
            }
        } else {
            return ScreenType.PHONE;
        }
    }

    /**
     * 判断是否平板设备
     *
     * @return true:平板,false:手机
     */
    public static int getScreenType() {
        if (sIsTabletChecked) {
            return sScreenType;
        }
        sScreenType = checkScreenSize(AppUtils.getContext());
        sIsTabletChecked = true;
        return sScreenType;
    }

    /**
     * 是否是平板
     *
     * @return
     */
    public static boolean isTablet() {
        return getScreenType() == ScreenType.SMALL_TABLET || getScreenType() == ScreenType.BIG_TABLET;
    }

    /**
     * 判断是否是MIUI系统
     */
    public static boolean isMIUI() {
        return !TextUtils.isEmpty(sMiuiVersionName);
    }

    public static boolean isMIUIV5() {
        return "v5".equalsIgnoreCase(sMiuiVersionName);
    }

    public static boolean isMIUIV6() {
        return "v6".equalsIgnoreCase(sMiuiVersionName);
    }

    public static boolean isMIUIV7() {
        return "v7".equalsIgnoreCase(sMiuiVersionName);
    }

    public static boolean isMIUIV8() {
        return "v8".equalsIgnoreCase(sMiuiVersionName);
    }

    public static boolean isMIUIV9() {
        return "v9".equalsIgnoreCase(sMiuiVersionName);
    }

    public static boolean isMIUIV10() {
        return "v10".equalsIgnoreCase(sMiuiVersionName);
    }

    public static boolean isMIUIV11() {
        return "v11".equalsIgnoreCase(sMiuiVersionName);
    }

    public static boolean isMIUIV12() {
        return "v12".equalsIgnoreCase(sMiuiVersionName);
    }

    public static boolean isFlymeVersionHigher5_2_4() {
        //查不到默认高于5.2.4
        boolean isHigher = true;
        if (sFlymeVersionName != null && !"".equals(sFlymeVersionName)) {
            final Matcher matcher = PATTERN_FLYME_VERSION.matcher(sFlymeVersionName);
            if (matcher.find()) {
                String versionString = matcher.group();
                if (!TextUtils.isEmpty(versionString) && !"".equals(versionString)) {
                    String[] version = versionString.split("\\.");
                    if (version.length == 3) {
                        if (Integer.valueOf(version[0]) < 5) {
                            isHigher = false;
                        } else if (Integer.valueOf(version[0]) > 5) {
                            isHigher = true;
                        } else {
                            if (Integer.valueOf(version[1]) < 2) {
                                isHigher = false;
                            } else if (Integer.valueOf(version[1]) > 2) {
                                isHigher = true;
                            } else {
                                if (Integer.valueOf(version[2]) < 4) {
                                    isHigher = false;
                                } else if (Integer.valueOf(version[2]) >= 5) {
                                    isHigher = true;
                                }
                            }
                        }
                    }

                }
            }
        }
        return isMeizu() && isHigher;
    }

    /**
     * 判断是否是 flyme 系统
     */
    public static boolean isFlyme() {
        return !TextUtils.isEmpty(sFlymeVersionName) && sFlymeVersionName.contains(ROM_FLYME);
    }

    public static boolean isSmartisan() {
        //两种方式有待验证...
        //Log.e("123",!TextUtils.isEmpty(sSmartisanVersionName) && sSmartisanVersionName.contains(ROM_SMARTISAN));
        return BRAND.contains(ROM_SMARTISAN);
    }

    public static boolean is360() {
        return BRAND.contains(ROM_QIKU) || BRAND.contains(ROM_360);
    }

    public static boolean isMeizu() {
        return isPhone(BOARD_MEIZU) || isFlyme();
    }

    /**
     * 判断是否为小米
     * https://dev.mi.com/doc/?p=254
     */
    public static boolean isXiaomi() {
        return "xiaomi".equals(Build.MANUFACTURER.toLowerCase());
    }

    public static boolean isVivo() {
        return BRAND.contains(ROM_VIVO) || BRAND.contains(ROM_BBK);
    }

    public static boolean isOppo() {
        return BRAND.contains(ROM_OPPO);
    }

    public static boolean isHuawei() {
        return BRAND.contains(ROM_HUAWEI) || BRAND.contains(ROM_HONOR);
    }

    public static boolean isEssentialPhone() {
        return BRAND.contains(ESSENTIAL);
    }

    /**
     * 判断是否为 ZUK Z1 和 ZTK C2016。
     * 两台设备的系统虽然为 android 6.0，但不支持状态栏icon颜色改变，因此经常需要对它们进行额外判断。
     */
    public static boolean isZUKZ1() {
        final String board = Build.MODEL;
        return board != null && board.toLowerCase().contains(ROM_ZUKZ1);
    }

    public static boolean isZTKC2016() {
        final String board = Build.MODEL;
        return board != null && board.toLowerCase().contains(ROM_ZTEC2016);
    }

    private static boolean isPhone(String[] boards) {
        final String board = Build.BOARD;
        if (board == null) {
            return false;
        }
        for (String board1 : boards) {
            if (board.equals(board1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断悬浮窗权限（目前主要用户魅族与小米的检测）。
     */
    public static boolean isFloatWindowOpAllowed(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.KITKAT) {
            return checkOp(context, 24);  // 24 是AppOpsManager.OP_SYSTEM_ALERT_WINDOW 的值，该值无法直接访问
        } else {
            try {
                return (context.getApplicationInfo().flags & 1 << 27) == 1 << 27;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @TargetApi(19)
    private static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.KITKAT) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Method method = manager.getClass().getDeclaredMethod("checkOp", int.class, int.class, String.class);
                int property = (Integer) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
                return AppOpsManager.MODE_ALLOWED == property;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Nullable
    private static String getLowerCaseName(Properties p, Method get, String key) {
        String name = p.getProperty(key);
        if (name == null) {
            try {
                name = (String) get.invoke(null, key);
            } catch (Exception ignored) {
            }
        }
        if (name != null) {
            name = name.toLowerCase();
        }
        return name;
    }

}