package com.ando.toolkit

import java.util.regex.Pattern

/**
 * Title:ValidateUtils
 *
 *
 * Description:有效性验证
 *
 *
 * @author javakam
 * @date 2019/10/31 10:39
 */
object ValidateUtils {
    /*----------常用输入验证------*/ //匹配双字节字符(包括汉字在内)：[^x00-xff]             ---已验证
    fun isDoubleByteString(inputString: String?): Boolean {
        val pattern = Pattern.compile("[^x00-xff]")
        val matcher = pattern.matcher(inputString)
        return matcher.find()
    }

    //匹配HTML标记的正则表达式：/< (.*)>.*|< (.*) />/      ---未验证：可以实现HTML过滤
    fun isHtmlString(inputString: String?): Boolean {
        val pattern = Pattern.compile("/< (.*)>.*|< (.*) />/")
        val matcher = pattern.matcher(inputString)
        return matcher.find()
    }

    //匹配首尾空格的正则表达式：[\\s*)]+\\w+[\\s*$]         ---已验证
    fun isTrimStartAndEndInthisString(inputString: String?): Boolean {
        val pattern = Pattern.compile("[\\s*)]+\\w+[\\s*$]")
        val matcher = pattern.matcher(inputString)
        return matcher.find()
    }

    //邮箱规则：用户名@服务器名.后缀                                   ---已验证
    //匹配Email地址的正则表达式：^([a-z0-9A-Z]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}
    fun isEmail(inputString: String?): Boolean {
        val pattern =
            Pattern.compile("^([a-z0-9A-Z]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}")
        val matcher = pattern.matcher(inputString)
        return matcher.find()
    }

    //匹配网址URL的正则表达式：^http://[a-zA-Z0-9./\\s]      ---已验证
    fun isUrl(inputString: String?): Boolean {
        val pattern = Pattern.compile("^http://[a-zA-Z0-9./\\s]")
        val matcher = pattern.matcher(inputString)
        return matcher.find()
    }

    //验证用户密码:“^[a-zA-Z]\\w{5,17}$”
    //正确格式为：以字母开头，长度在6-18                      --已验证
    fun isPassword(inputString: String?): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z]\\w{5,17}$")
        val matcher = pattern.matcher(inputString)
        return matcher.find()
    }

    //验证身份证是否有效15位或18位  ^\\d{15}(\\d{2}[0-9xX])?$  ---已验证<包括对年月日的合法性进行验证>
    fun isIdCard(inputString: String): Boolean {
        var pattern = Pattern.compile("^\\d{15}(\\d{2}[0-9xX])?$")
        var matcher = pattern.matcher(inputString)
        if (matcher.find()) {                                 //对年月日字符串的验证
            val power = inputString.substring(inputString.length - 12, inputString.length - 4)
            pattern =
                Pattern.compile("^[1-2]+([0-9]{3})+(0[1-9][0-2][0-9]|0[1-9]3[0-1]|1[0-2][0-3][0-1]|1[0-2][0-2][0-9])")
            matcher = pattern.matcher(power)
        }
        return matcher.find()
    }

    //验证固定电话号码   ^(([0-9]{3,4})|([0-9]{3,4})-)?[0-9]{7,8}$ ---已验证
    fun isTelePhone(inputString: String?): Boolean {
        val pattern = Pattern.compile("^(([0-9]{3,4})|([0-9]{3,4})-)?[0-9]{7,8}$")
        val matcher = pattern.matcher(inputString)
        return matcher.find()
    }

    /**
     * 校验手机号
     *
     *
     * 手机号开头集合  -> 建议后台判断,前端不能包含所有的手机号!!!
     * <pre>
     * 166，
     * 176，177，178
     * 180，181，182，183，184，185，186，187，188，189
     * 145，147
     * 130，131，132，133，134，135，136，137，138，139
     * 150，151，152，153，155，156，157，158，159
     * 198，199
    </pre> *
     */
    @Deprecated("")
    fun isMobilePhone(str: String?): Boolean {
        val regExp =
            "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57]|19[0-9]|16[0-9])[0-9]{8}$"
        val p = Pattern.compile(regExp)
        val m = p.matcher(str)
        return m.matches()
    }

    /**
     * 校验手机号
     *
     *
     * 匹配手机号的规则：[3578]是手机号第二位可能出现的数字
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    fun isMobilePhone2(mobile: String?): Boolean {
        return Pattern.matches("^[1][35789][0-9]{9}$", mobile)
    }

    //只能输入汉字，匹配中文字符的正则表达式：^[\u4e00-\u9fa5]*$--已验证
    fun isChineseString(inputString: String?): Boolean {
        val pattern = Pattern.compile("^[\u4e00-\u9fa5]*$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    /*--------- 数字操作验证---对于使用过正则表达式的人而言，下面的就太简单了故不再测试--*/ //匹配正整数 ^[1-9]d*$　 　
    fun isPositiveInteger(inputString: String?): Boolean {
        val pattern = Pattern.compile("^[1-9]d*$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //匹配负整数 ^-[1-9]d*$ 　
    fun isNegativeInteger(inputString: String?): Boolean {
        val pattern = Pattern.compile("^-[1-9]d*$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //匹配整数  ^-?[1-9]d*$　　
    fun isInteger(inputString: String?): Boolean {
        val pattern = Pattern.compile("^-?[1-9]d*$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //匹配非负整数（正整数 + 0） ^[1-9]d*|0$　
    fun isNotNegativeInteger(inputString: String?): Boolean {
        val pattern = Pattern.compile("^[1-9]d*|0$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //匹配非正整数（负整数 + 0） ^-[1-9]d*|0$　
    fun isNotPositiveInteger(inputString: String?): Boolean {
        val pattern = Pattern.compile("^-[1-9]d*|0$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //匹配正浮点数    ^[1-9]d*.d*|0.d*[1-9]d*$　　
    fun isPositiveFloat(inputString: String?): Boolean {
        val pattern = Pattern.compile("^[1-9]d*.d*|0.d*[1-9]d*$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //匹配负浮点数    ^-([1-9]d*.d*|0.d*[1-9]d*)$　
    fun isNegativeFloat(inputString: String?): Boolean {
        val pattern = Pattern.compile("^-([1-9]d*.d*|0.d*[1-9]d*)$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //匹配浮点数   ^-?([1-9]d*.d*|0.d*[1-9]d*|0?.0+|0)$　
    fun isFloat(inputString: String?): Boolean {
        val pattern = Pattern.compile("^-?([1-9]d*.d*|0.d*[1-9]d*|0?.0+|0)$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //匹配非负浮点数（正浮点数 + 0）^[1-9]d*.d*|0.d*[1-9]d*|0?.0+|0$　　
    fun isNotNegativeFloat(inputString: String?): Boolean {
        val pattern = Pattern.compile("^[1-9]d*.d*|0.d*[1-9]d*|0?.0+|0$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //匹配非正浮点数（负浮点数 + 0）^(-([1-9]d*.d*|0.d*[1-9]d*))|0?.0+|0$
    fun isNotPositiveFloat(inputString: String?): Boolean {
        val pattern = Pattern.compile("^(-([1-9]d*.d*|0.d*[1-9]d*))|0?.0+|0$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入数字：“^[0-9]*$”
    fun isNumber(inputString: String?): Boolean {
        val pattern = Pattern.compile("^[0-9]*$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入n位的数字：“^d{n}$”
    fun isNumberFormatLength(length: Int, inputString: String?): Boolean {
        val pattern = Pattern.compile("^d{$length}$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入至少n位数字：“^d{n,}$”
    fun isNumberLengthLess(length: Int, inputString: String?): Boolean {
        val pattern = Pattern.compile("^d{$length,}$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入m-n位的数字：“^d{m,n}$”
    fun isNumberLengthBetweenLowerAndUpper(lower: Int, upper: Int, inputString: String?): Boolean {
        val pattern = Pattern.compile("^d{$lower,$upper}$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入零和非零开头的数字：“^(0|[1-9][0-9]*)$”
    fun isNumberStartWithZeroOrNot(inputString: String?): Boolean {
        val pattern = Pattern.compile("^(0|[1-9][0-9]*)$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入有两位小数的正实数：“^[0-9]+(.[0-9]{2})?$”
    fun isNumberInPositiveWhichHasTwolengthAfterPoint(inputString: String?): Boolean {
        val pattern = Pattern.compile("^[0-9]+(.[0-9]{2})?$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入有1-3位小数的正实数：“^[0-9]+(.[0-9]{1,3})?$”
    fun isNumberInPositiveWhichHasOneToThreelengthAfterPoint(inputString: String?): Boolean {
        val pattern = Pattern.compile("^[0-9]+(.[0-9]{1,3})?$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入非零的正整数：“^+?[1-9][0-9]*$”
    fun isIntegerUpZero(inputString: String?): Boolean {
        val pattern = Pattern.compile("^+?[1-9][0-9]*$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入非零的负整数：“^-[1-9][0-9]*$”
    fun isIntegerBlowZero(inputString: String?): Boolean {
        val pattern = Pattern.compile("^-[1-9][0-9]*$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入由26个英文字母组成的字符串：“^[A-Za-z]+$”
    fun isEnglishAlphabetString(inputString: String?): Boolean {
        val pattern = Pattern.compile("^[A-Za-z]+$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入由26个大写英文字母组成的字符串：“^[A-Z]+$”
    fun isUppercaseEnglishAlphabetString(inputString: String?): Boolean {
        val pattern = Pattern.compile("^[A-Z]+$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入由26个小写英文字母组成的字符串：“^[a-z]+$”
    fun isLowerEnglishAlphabetString(inputString: String?): Boolean {
        val pattern = Pattern.compile("^[a-z]+$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入由数字和26个英文字母组成的字符串：“^[A-Za-z0-9]+$”
    fun isNumberEnglishAlphabetString(inputString: String?): Boolean {
        val pattern = Pattern.compile("^[A-Za-z0-9]+$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }

    //只能输入由数字、26个英文字母或者下划线组成的字符串：“^w+$”
    fun isNumberEnglishAlphabetWithUnderlineString(inputString: String?): Boolean {
        val pattern = Pattern.compile("^w+$")
        val macher = pattern.matcher(inputString)
        return macher.find()
    }
}