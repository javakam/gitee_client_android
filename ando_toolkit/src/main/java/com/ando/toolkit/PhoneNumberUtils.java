package com.ando.toolkit;

import android.text.TextUtils;

/**
 * Title: PhoneNumberUtils
 * <p>
 * Description:
 * </p>
 *
 * @author javakam
 * @date 2019/8/5  14:48
 */
public class PhoneNumberUtils {
    /**
     * 验证手机格式
     *
     * @param mobileNum 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     *                  联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
     *                  总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     */
    public static boolean isPhoneNum(String mobileNum) {
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][358]\\d{9}";
        if (TextUtils.isEmpty(mobileNum)) {
            return false;
        } else {
            return mobileNum.matches(telRegex);
        }
    }

    /**
     * 至少包含大小写字母及数字中的两种
     */
    public static boolean isLetterDigit(String password) {
        boolean isDigit = false;  //定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false; //定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) { //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(password.charAt(i))) { //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && password.matches(regex);
        return isRight;
    }
}
