package com.ando.toolkit;

import java.math.BigDecimal;

/**
 * Title:NumberUtils
 * <p>
 * Description:数据工具类
 * </p>
 *
 * @author javakam
 * Date 2018/8/17 14:40
 */
public class NumberUtils {

    private static float setScale(float value, int scale) {
        BigDecimal decimal = new BigDecimal(value);
        return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    private static double setScale(double value, int scale) {
        BigDecimal decimal = new BigDecimal(value);
        return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static int parseInteger(String value) {
        int result = 0;
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static int parseInteger(Number value) {
        int result = 0;
        if (value != null) {
            return value.intValue();
        }
        return result;
    }

    public static long parseLong(String value) {
        long result = 0;
        if (value != null) {
            try {
                return Long.parseLong(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static long parseLong(Number value) {
        long result = 0;
        if (value != null) {
            return value.longValue();
        }
        return result;
    }

    public static float parseFloat(String value, int scale) {
        float result = 0.00f;
        if (value != null) {
            try {
                result = Float.parseFloat(value);
                return setScale(result, scale);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static float parseFloat(Number value, int scale) {
        float result = 0.00f;
        if (value != null) {
            return setScale(value.floatValue(), scale);
        }
        return result;
    }

    public static double parseDouble(String value, int scale) {
        double result = 0.00f;
        if (value != null) {
            try {
                result = Double.parseDouble(value);
                return setScale(result, scale);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static double parseDouble(Number value, int scale) {
        double result = 0.00f;
        if (value != null) {
            return setScale(value.doubleValue(), scale);
        }
        return result;
    }


}
