package com.gitee.android.view

import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.util.*

class DynamicTimeFormat(yearFormat: String?, dateFormat: String?, timeFormat: String?) :
    SimpleDateFormat(
        String.format(locale, "%s %s %s", yearFormat, dateFormat, timeFormat), locale
    ) {
    private var mFormat = "%s"

    constructor(format: String) : this() {
        mFormat = format
    }

    constructor(
        format: String = "%s",
        yearFormat: String? = "yyyy年",
        dateFormat: String? = "M月d日",
        timeFormat: String? = "HH:mm"
    ) : this(yearFormat, dateFormat, timeFormat) {
        mFormat = format
    }

    override fun format(date: Date, toAppendTo: StringBuffer, pos: FieldPosition): StringBuffer {
        var appendTo = toAppendTo
        appendTo = super.format(date, appendTo, pos)
        val otherCalendar = calendar
        val todayCalendar = Calendar.getInstance()
        val hour = otherCalendar[Calendar.HOUR_OF_DAY]
        val times = appendTo.toString().split(" ").toTypedArray()
        val moment = if (hour == 12) moments[0] else moments[hour / 6 + 1]
        val timeFormat = moment + " " + times[2]
        val dateFormat = times[1] + " " + timeFormat
        val yearFormat = times[0] + dateFormat
        appendTo.delete(0, appendTo.length)
        val yearTemp = todayCalendar[Calendar.YEAR] == otherCalendar[Calendar.YEAR]
        if (yearTemp) {
            val todayMonth = todayCalendar[Calendar.MONTH]
            val otherMonth = otherCalendar[Calendar.MONTH]
            if (todayMonth == otherMonth) { //表示是同一个月
                when (todayCalendar[Calendar.DATE] - otherCalendar[Calendar.DATE]) {
                    0 -> appendTo.append(timeFormat)
                    1 -> {
                        appendTo.append("昨天 ")
                        appendTo.append(timeFormat)
                    }
                    2 -> {
                        appendTo.append("前天 ")
                        appendTo.append(timeFormat)
                    }
                    3, 4, 5, 6 -> {
                        val dayOfMonth = otherCalendar[Calendar.WEEK_OF_MONTH]
                        val todayOfMonth = todayCalendar[Calendar.WEEK_OF_MONTH]
                        if (dayOfMonth == todayOfMonth) { //表示是同一周
                            val dayOfWeek = otherCalendar[Calendar.DAY_OF_WEEK]
                            if (dayOfWeek != 1) { //判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                                appendTo.append(weeks[otherCalendar[Calendar.DAY_OF_WEEK] - 1])
                                appendTo.append(' ')
                                appendTo.append(timeFormat)
                            } else {
                                appendTo.append(dateFormat)
                            }
                        } else {
                            appendTo.append(dateFormat)
                        }
                    }
                    else -> appendTo.append(dateFormat)
                }
            } else {
                appendTo.append(dateFormat)
            }
        } else {
            appendTo.append(yearFormat)
        }
        val length = appendTo.length
        appendTo.append(String.format(locale, mFormat, appendTo.toString()))
        appendTo.delete(0, length)
        return appendTo
    }

    companion object {
        private const val serialVersionUID = 7504670306731250366L
        private val locale = Locale.CHINA
        private val weeks = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
        private val moments = arrayOf("中午", "凌晨", "早上", "下午", "晚上")
    }
}