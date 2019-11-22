@file:JvmName("Utils")
package com.merseyside.partyapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.merseyside.partyapp.CalcApplication
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import java.math.BigDecimal
import java.math.RoundingMode


@SuppressLint("SimpleDateFormat")
fun getDateTime(timestamp: Long): String? {
    return try {
        val sdf = SimpleDateFormat("dd MMM", getCurrentLocale(CalcApplication.getInstance()))
        val netDate = Date(timestamp)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}

fun getHoursDateTime(timestamp: Long): String? {
    return try {
        val sdf = SimpleDateFormat("HH:mm", getCurrentLocale(CalcApplication.getInstance()))
        val netDate = Date(timestamp)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}

fun isPriceValid(price: String?): Boolean {
    if (price != null) {
        return try {
            val priceNum = convertPriceToDouble(price)

            priceNum > 0
        } catch (e: NumberFormatException) {
            false
        }
    }

    return false
}

fun isPercentValid(percentStr: String): Boolean {
    return try {
        val percent = convertPercentToInt(percentStr)
        return (!percentStr.contains(".") && percentStr.length < 3 && percent in 0 .. 100)
    } catch (e: NumberFormatException) {
        false
    }
}

@Throws(NumberFormatException::class)
fun convertPercentToFloat(percentStr: String): Float {
    return percentStr.toFloat()
}

@Throws(NumberFormatException::class)
fun convertPercentToInt(percentStr: String): Int {
    return percentStr.toInt()
}

@Throws(NumberFormatException::class)
fun convertPercentToString(percentFloat: Float): String {
    return percentFloat.toString().split(".")[0]
}

fun getHumanReadablePercents(percentFloat: Float): String {
    return (percentFloat * 100).toInt().toString()
}

fun getInternalPercents(percentStr: String): Float {
    return convertPercentToFloat(percentStr) / 100
}

fun isNameValid(name: String?): Boolean {
    if (name.isNullOrEmpty()) {
        return false
    } else if (name.startsWith(" ")) {
        isNameValid(name.drop(1))
    }

    return name.length in 3..24
}

@Throws(NumberFormatException::class)
fun convertPriceToDouble(price: String): Double {

    return price.toDouble()
}

fun doubleToStringPrice(double: Double): String {
    val bigInteger = BigDecimal(double)

    return bigInteger.setScale(2, RoundingMode.HALF_UP).toString()
}

fun RecyclerView.attachSnapHelperWithListener(
    snapHelper: SnapHelper,
    behavior: SnapOnScrollListener.Behavior = SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
    onSnapPositionChangeListener: SnapOnScrollListener.OnSnapPositionChangeListener
) {
    snapHelper.attachToRecyclerView(this)
    val snapOnScrollListener = SnapOnScrollListener(snapHelper, behavior, onSnapPositionChangeListener)
    addOnScrollListener(snapOnScrollListener)
}

fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
    val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}

fun getCurrentLocale(context: Context): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales.get(0)
    } else {
        context.resources.configuration.locale
    }
}

fun getCircleText(str: String): String {
    val words = str.split(" ")

    val text = StringBuilder()

    if (words.size >= 2) {
        (0..1).forEach { index ->
            text.append(words[index][0])
        }
    } else {
        text.append(words[0][0])
    }

    return text.toString().toUpperCase()
}