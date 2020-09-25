package com.ando.toolkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title:ValidateUtils
 * <p>
 * Description:有效性验证
 * </p>
 *
 * @author javakam
 * @date 2019/10/31 10:39
 */
public class ValidateUtils {

    /*----------常用输入验证------*/

    //匹配双字节字符(包括汉字在内)：[^x00-xff]             ---已验证
    public static boolean isDoubleByteString(String inputString) {
        Pattern pattern = Pattern.compile("[^x00-xff]");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //匹配HTML标记的正则表达式：/< (.*)>.*|< (.*) />/      ---未验证：可以实现HTML过滤
    public static boolean isHtmlString(String inputString) {
        Pattern pattern = Pattern.compile("/< (.*)>.*|< (.*) />/");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //匹配首尾空格的正则表达式：[\\s*)]+\\w+[\\s*$]         ---已验证
    public static boolean isTrimStartAndEndInthisString(String inputString) {
        Pattern pattern = Pattern.compile("[\\s*)]+\\w+[\\s*$]");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //邮箱规则：用户名@服务器名.后缀                                   ---已验证
    //匹配Email地址的正则表达式：^([a-z0-9A-Z]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}
    public static boolean isEmail(String inputString) {
        Pattern pattern = Pattern.compile("^([a-z0-9A-Z]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //匹配网址URL的正则表达式：^http://[a-zA-Z0-9./\\s]      ---已验证
    public static boolean isUrl(String inputString) {
        Pattern pattern = Pattern.compile("^http://[a-zA-Z0-9./\\s]");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //验证用户密码:“^[a-zA-Z]\\w{5,17}$”
    //正确格式为：以字母开头，长度在6-18                      --已验证
    public static boolean isPassword(String inputString) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]\\w{5,17}$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //验证身份证是否有效15位或18位  ^\\d{15}(\\d{2}[0-9xX])?$  ---已验证<包括对年月日的合法性进行验证>
    public static boolean isIdCard(String inputString) {
        Pattern pattern = Pattern.compile("^\\d{15}(\\d{2}[0-9xX])?$");
        Matcher macher = pattern.matcher(inputString);
        if (macher.find()) {                                 //对年月日字符串的验证
            String power = inputString.substring(inputString.length() - 12, inputString.length() - 4);
            pattern = Pattern.compile("^[1-2]+([0-9]{3})+(0[1-9][0-2][0-9]|0[1-9]3[0-1]|1[0-2][0-3][0-1]|1[0-2][0-2][0-9])");
            macher = pattern.matcher(power);
        }
        return macher.find();
    }

    //验证固定电话号码   ^(([0-9]{3,4})|([0-9]{3,4})-)?[0-9]{7,8}$ ---已验证
    public static boolean isTelePhone(String inputString) {
        Pattern pattern = Pattern.compile("^(([0-9]{3,4})|([0-9]{3,4})-)?[0-9]{7,8}$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    /**
     * 校验手机号
     * <p>
     * 手机号开头集合  -> 建议后台判断,前端不能包含所有的手机号!!!
     * <pre>
     *   166，
     *   176，177，178
     *   180，181，182，183，184，185，186，187，188，189
     *   145，147
     *   130，131，132，133，134，135，136，137，138，139
     *   150，151，152，153，155，156，157，158，159
     *   198，199
     * </pre>
     */
    @Deprecated
    public static boolean isMobilePhone(String str) {
        final String regExp = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57]|19[0-9]|16[0-9])[0-9]{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 校验手机号
     * <p>
     * 匹配手机号的规则：[3578]是手机号第二位可能出现的数字
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobilePhone2(String mobile) {
        return Pattern.matches("^[1][35789][0-9]{9}$", mobile);
    }


    //只能输入汉字，匹配中文字符的正则表达式：^[\u4e00-\u9fa5]*$--已验证
    public static boolean isChineseString(String inputString) {
        Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]*$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    /*--------- 数字操作验证---对于使用过正则表达式的人而言，下面的就太简单了故不再测试--*/

    //匹配正整数 ^[1-9]d*$　 　
    public static boolean isPositiveInteger(String inputString) {
        Pattern pattern = Pattern.compile("^[1-9]d*$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //匹配负整数 ^-[1-9]d*$ 　
    public static boolean isNegativeInteger(String inputString) {
        Pattern pattern = Pattern.compile("^-[1-9]d*$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //匹配整数  ^-?[1-9]d*$　　
    public static boolean isInteger(String inputString) {
        Pattern pattern = Pattern.compile("^-?[1-9]d*$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //匹配非负整数（正整数 + 0） ^[1-9]d*|0$　
    public static boolean isNotNegativeInteger(String inputString) {
        Pattern pattern = Pattern.compile("^[1-9]d*|0$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //匹配非正整数（负整数 + 0） ^-[1-9]d*|0$　
    public static boolean isNotPositiveInteger(String inputString) {
        Pattern pattern = Pattern.compile("^-[1-9]d*|0$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //匹配正浮点数    ^[1-9]d*.d*|0.d*[1-9]d*$　　
    public static boolean isPositiveFloat(String inputString) {
        Pattern pattern = Pattern.compile("^[1-9]d*.d*|0.d*[1-9]d*$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //匹配负浮点数    ^-([1-9]d*.d*|0.d*[1-9]d*)$　
    public static boolean isNegativeFloat(String inputString) {
        Pattern pattern = Pattern.compile("^-([1-9]d*.d*|0.d*[1-9]d*)$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //匹配浮点数   ^-?([1-9]d*.d*|0.d*[1-9]d*|0?.0+|0)$　
    public static boolean isFloat(String inputString) {
        Pattern pattern = Pattern.compile("^-?([1-9]d*.d*|0.d*[1-9]d*|0?.0+|0)$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //匹配非负浮点数（正浮点数 + 0）^[1-9]d*.d*|0.d*[1-9]d*|0?.0+|0$　　
    public static boolean isNotNegativeFloat(String inputString) {
        Pattern pattern = Pattern.compile("^[1-9]d*.d*|0.d*[1-9]d*|0?.0+|0$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //匹配非正浮点数（负浮点数 + 0）^(-([1-9]d*.d*|0.d*[1-9]d*))|0?.0+|0$
    public static boolean isNotPositiveFloat(String inputString) {
        Pattern pattern = Pattern.compile("^(-([1-9]d*.d*|0.d*[1-9]d*))|0?.0+|0$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入数字：“^[0-9]*$”
    public static boolean isNumber(String inputString) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入n位的数字：“^d{n}$”
    public static boolean isNumberFormatLength(int length, String inputString) {
        Pattern pattern = Pattern.compile("^d{" + length + "}$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入至少n位数字：“^d{n,}$”
    public static boolean isNumberLengthLess(int length, String inputString) {
        Pattern pattern = Pattern.compile("^d{" + length + ",}$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入m-n位的数字：“^d{m,n}$”
    public static boolean isNumberLengthBetweenLowerAndUpper(int lower, int upper, String inputString) {
        Pattern pattern = Pattern.compile("^d{" + lower + "," + upper + "}$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入零和非零开头的数字：“^(0|[1-9][0-9]*)$”
    public static boolean isNumberStartWithZeroOrNot(String inputString) {
        Pattern pattern = Pattern.compile("^(0|[1-9][0-9]*)$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入有两位小数的正实数：“^[0-9]+(.[0-9]{2})?$”
    public static boolean isNumberInPositiveWhichHasTwolengthAfterPoint(String inputString) {
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{2})?$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入有1-3位小数的正实数：“^[0-9]+(.[0-9]{1,3})?$”
    public static boolean isNumberInPositiveWhichHasOneToThreelengthAfterPoint(String inputString) {
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{1,3})?$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入非零的正整数：“^+?[1-9][0-9]*$”
    public static boolean isIntegerUpZero(String inputString) {
        Pattern pattern = Pattern.compile("^+?[1-9][0-9]*$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入非零的负整数：“^-[1-9][0-9]*$”
    public static boolean isIntegerBlowZero(String inputString) {
        Pattern pattern = Pattern.compile("^-[1-9][0-9]*$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入由26个英文字母组成的字符串：“^[A-Za-z]+$”
    public static boolean isEnglishAlphabetString(String inputString) {
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入由26个大写英文字母组成的字符串：“^[A-Z]+$”
    public static boolean isUppercaseEnglishAlphabetString(String inputString) {
        Pattern pattern = Pattern.compile("^[A-Z]+$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入由26个小写英文字母组成的字符串：“^[a-z]+$”
    public static boolean isLowerEnglishAlphabetString(String inputString) {
        Pattern pattern = Pattern.compile("^[a-z]+$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入由数字和26个英文字母组成的字符串：“^[A-Za-z0-9]+$”
    public static boolean isNumberEnglishAlphabetString(String inputString) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }

    //只能输入由数字、26个英文字母或者下划线组成的字符串：“^w+$”
    public static boolean isNumberEnglishAlphabetWithUnderlineString(String inputString) {
        Pattern pattern = Pattern.compile("^w+$");
        Matcher macher = pattern.matcher(inputString);
        return macher.find();
    }
}
