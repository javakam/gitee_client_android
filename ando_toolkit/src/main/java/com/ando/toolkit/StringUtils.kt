package com.ando.toolkit

import java.util.*

object StringUtils {

    /**
     * 自动生成32位的 UUID
     */
    
    fun generateUUID(): String = UUID.randomUUID().toString().replace("-", "")
    
    fun noNull(text: CharSequence?): String = if (text.isNullOrBlank()) "" else text.toString()
    
    fun noNull(any: Any?): String = if (any is CharSequence) noNull(any) else ""

    fun noNullZero(text: CharSequence?): CharSequence = if (text.isNullOrBlank()) "0" else text

    //字符串转换unicode
    fun stringToUnicode(string: String): String {
        val unicode = StringBuffer()
        for (element in string) {
            unicode.append("\\u" + Integer.toHexString(element.toInt())) // 转换为unicode
        }
        return unicode.toString()
    }

    //unicode 转字符串
    fun unicodeToString(unicode: String): String {
        val string = StringBuffer()
        val hex = unicode.split("\\\\u".toRegex()).toTypedArray()
        for (i in 1 until hex.size) {
            val data = hex[i].toInt(16) // 转换出每一个代码点
            string.append(data.toChar()) // 追加成string
        }
        return string.toString()
    }

    // Equals
    //-----------------------------------------------------------------------

    /**
     *
     * Compares two CharSequences, returning `true` if they represent
     * equal sequences of characters.
     *
     * `null`s are handled without exceptions. Two `null`
     * references are considered to be equal. The comparison is **case sensitive**.
     *
     * <pre>
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
     * </pre>
     *
     * @param cs1 the first CharSequence, may be `null`
     * @param cs2 the second CharSequence, may be `null`
     * @return `true` if the CharSequences are equal (case-sensitive), or both `null`
     * @date 3.0 Changed signature from equals(String, String) to equals(CharSequence, CharSequence)
     * @see Object.equals
     * @see .equalsIgnoreCase
     */
    fun equals(cs1: CharSequence?, cs2: CharSequence?): Boolean {
        if (cs1 === cs2) {
            return true
        }
        if (cs1 == null || cs2 == null) {
            return false
        }
        if (cs1.length != cs2.length) {
            return false
        }
        if (cs1 is String && cs2 is String) {
            return cs1 == cs2
        }
        // Step-wise comparison
        val length = cs1.length
        for (i in 0 until length) {
            if (cs1[i] != cs2[i]) {
                return false
            }
        }
        return true
    }

    /**
     *
     * Compares two CharSequences, returning `true` if they represent
     * equal sequences of characters, ignoring case.
     *
     *
     * `null`s are handled without exceptions. Two `null`
     * references are considered equal. The comparison is **case insensitive**.
     *
     * <pre>
     * StringUtils.equalsIgnoreCase(null, null)   = true
     * StringUtils.equalsIgnoreCase(null, "abc")  = false
     * StringUtils.equalsIgnoreCase("abc", null)  = false
     * StringUtils.equalsIgnoreCase("abc", "abc") = true
     * StringUtils.equalsIgnoreCase("abc", "ABC") = true
    </pre> *
     *
     * @param cs1 the first CharSequence, may be `null`
     * @param cs2 the second CharSequence, may be `null`
     * @return `true` if the CharSequences are equal (case-insensitive), or both `null`
     * @date 3.0 Changed signature from equalsIgnoreCase(String, String) to equalsIgnoreCase(CharSequence, CharSequence)
     * @see .equals
     */
    fun equalsIgnoreCase(
        cs1: CharSequence?,
        cs2: CharSequence?
    ): Boolean {
        if (cs1 === cs2) {
            return true
        }
        if (cs1 == null || cs2 == null) {
            return false
        }
        return if (cs1.length != cs2.length) {
            false
        } else regionMatches(cs1, true, 0, cs2, 0, cs1.length)
    }

    /**
     * Green implementation of regionMatches.
     *
     * @param cs the `CharSequence` to be processed
     * @param ignoreCase whether or not to be case insensitive
     * @param thisStart the index to start on the `cs` CharSequence
     * @param substring the `CharSequence` to be looked for
     * @param start the index to start on the `substring` CharSequence
     * @param length character length of the region
     * @return whether the region matched
     */
    private fun regionMatches(
        cs: CharSequence, ignoreCase: Boolean, thisStart: Int,
        substring: CharSequence, start: Int, length: Int
    ): Boolean {
        if (cs is String && substring is String) {
            return cs.regionMatches(
                thisStart,
                substring,
                start,
                length,
                ignoreCase = ignoreCase
            )
        }
        var index1 = thisStart
        var index2 = start
        var tmpLen = length

        // Extract these first so we detect NPEs the same as the java.lang.String version
        val srcLen = cs.length - thisStart
        val otherLen = substring.length - start

        // Check for invalid parameters
        if (thisStart < 0 || start < 0 || length < 0) {
            return false
        }

        // Check that the regions are long enough
        if (srcLen < length || otherLen < length) {
            return false
        }
        while (tmpLen-- > 0) {
            val c1 = cs[index1++]
            val c2 = substring[index2++]
            if (c1 == c2) {
                continue
            }
            if (!ignoreCase) {
                return false
            }

            // The same check as in String.regionMatches():
            if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                && Character.toLowerCase(c1) != Character.toLowerCase(c2)
            ) {
                return false
            }
        }
        return true
    }

}