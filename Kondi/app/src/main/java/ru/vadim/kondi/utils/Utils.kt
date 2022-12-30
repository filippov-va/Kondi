package ru.vadim.kondi.utils

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class Utils {

    fun doubleToMoney(double: Double): String {
        return NumberFormat.getCurrencyInstance(Locale("ru", "RU")).format(double)
    }

    fun executeAfterDelay(delay: Long=400, function: () -> (Unit)) {
        Handler(Looper.getMainLooper()).postDelayed({
            function()
        }, delay)

    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateInFormat(format: String = "dd.MM.yyyy"): String {
        return SimpleDateFormat(format).format(Date())
    }

    fun getCurrentDateInFormat(format: String = "dd.MM.yyyy", date: Date): String {
        return SimpleDateFormat(format).format(date)
    }

    fun convertToEpoch(str: String): Long {
        val l = LocalDate.parse(str, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        return l.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond + (3600 * 5)
    }

    fun convertToTime(long: Long?, param: Boolean = false): String {


        val dt =
            Instant.ofEpochSecond(long ?: 0)
                .atZone(ZoneId.systemDefault())


        val year: String = dt.toString().take(4)
        val month = dt.toString().drop(5).take(2)
        val day = dt.toString().drop(8).take(2)

        if (!param) {
            return "$day.$month.$year"
        } else {

            var str = "$day "

            when (month) {
                "01" -> {
                    str += "января "
                }
                "02" -> {
                    str += "февраля "
                }
                "03" -> {
                    str += "марта "
                }
                "04" -> {
                    str += "апреля "
                }
                "05" -> {
                    str += "мая "
                }
                "06" -> {
                    str += "июня "
                }
                "07" -> {
                    str += "июля "
                }
                "08" -> {
                    str += "августа "
                }
                "09" -> {
                    str += "сентября "
                }
                "10" -> {
                    str += "октября "
                }
                "11" -> {
                    str += "ноября "
                }
                "12" -> {
                    str += "декабря "
                }
            }
            str += year

            return str
        }
    }




}