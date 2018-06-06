package com.wengtao.my_utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {
    companion object {

        /**
         * 获取当前时间
         *
         * @param format "yyyy-MM-dd HH:mm:ss"
         * @return 当前时间
         */
        fun getCurrentTime(format: String): String {
            var date = Date()
            var simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
            return simpleDateFormat.format(date)
        }

        /**
         * 获取当前时间为本月的第几周
         *
         * @return WeekOfMonth
         */
        fun getWeekOfMonth(): Int {
            val calendar = Calendar.getInstance()
            val week = calendar.get(Calendar.WEEK_OF_MONTH)
            return week - 1
        }


        /**
         * 获取当前时间为本周的第几天
         *
         * @return DayOfWeek
         */
        fun getDayOfWeek(): Int {
            val calendar = Calendar.getInstance()
            var day = calendar.get(Calendar.DAY_OF_WEEK)
            day = if (day == 1) {
                7
            } else {
                day - 1
            }
            return day
        }

        /**
         * 获取当前时间的年份
         *
         * @return 年份
         */
        fun getYear(): Int {
            return Calendar.getInstance().get(Calendar.YEAR)
        }

        /**
         * 获取当前时间的月份
         *
         * @return 月份
         */
        fun getMonth(): Int {
            return GregorianCalendar.getInstance().get(Calendar.MONTH)
        }

        /**
         * 获取当前时间是哪天
         *
         * @return 哪天
         */
        fun getDay(): Int {
            return GregorianCalendar.getInstance().get(Calendar.DATE)
        }

        /**
         * 时间比较大小
         *
         * @param date1 date1
         * @param date2 date2
         * @param format "yyyy-MM-dd HH:mm:ss"
         * @return 1:date1大于date2；
         * -1:date1小于date2
         */
        fun compareDate(data1: String, data2: String, format: String): Int {
            val df = SimpleDateFormat(format, Locale.getDefault())
            try {
                val dt1 = df.parse(data1)
                val dt2 = df.parse(data2)
                return when {
                    dt1.time > dt2.time -> 1
                    dt1.time < dt2.time -> -1
                    else -> 0
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return 0
        }


        /**
         * 时间加减
         *
         * @param day       如"2015-09-22"
         * @param dayAddNum 加减值
         * @return 结果
         */
        fun timeAddSubtract(day: String, dayAddNumber: Int): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val newDate = Date(simpleDateFormat.parse(day).time + dayAddNumber * 24 * 60 * 60 * 1000)
            return simpleDateFormat.format(newDate)
        }


        /**
         * 毫秒格式化
         * 使用unixTimestamp2BeijingTime方法
         *
         * @param millisecond 如"1449455517602"
         * @param format      如"yyyy-MM-dd HH:mm:ss"
         * @return 格式化结果
         */
        fun millisecond2String(millSecond: Any, format: String): String {
            return SimpleDateFormat(format, Locale.getDefault()).format(millSecond)
        }

        /**
         * 时间戳转北京时间
         *
         * @param millisecond 如"1449455517602"
         * @param format      如"yyyy-MM-dd HH:mm:ss"
         * @return 格式化结果
         */
        fun unixTimestamp2BeijingTime(millisecond: Any, format: String): String {
            return SimpleDateFormat(format, Locale.getDefault()).format(millisecond)
        }


        /**
         * 北京时间转时间戳
         * 注意第一个参数和第二个参数格式必须一样
         *
         * @param beijingTime 如"2016-6-26 20:35:9"
         * @param format      如"yyyy-MM-dd HH:mm:ss"
         * @return 时间戳
         */
        fun beijingTime2UnixTimestamp(beijingTime: String, format: String): Long {
            val SimpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
            var unixTimeStamp: Long = 0
            val data = SimpleDateFormat.parse(beijingTime)
            unixTimeStamp = data.time / 1000
            return unixTimeStamp
        }
    }
}