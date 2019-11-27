@file:JvmName("Utils")
package com.merseyside.partyapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.merseyside.partyapp.CalcApplication
import com.merseyside.partyapp.R
import com.merseyside.partyapp.data.entity.MemberStatistic
import com.merseyside.partyapp.data.entity.Order
import com.merseyside.partyapp.data.entity.Result
import com.merseyside.partyapp.data.entity.Statistic
import com.upstream.basemvvmimpl.utils.isNotZero
import com.upstream.basemvvmimpl.utils.isZero
import kotlinx.android.synthetic.main.fragment_member_statistic.view.*
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import java.math.BigDecimal
import java.math.RoundingMode


@SuppressLint("SimpleDateFormat")
fun getDateTime(timestamp: Long): String? {
    return try {
        val sdf = SimpleDateFormat("dd MMM", getCurrentLocale(CalcApplication.getInstance().getContext()))
        val netDate = Date(timestamp)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}

fun getHoursDateTime(timestamp: Long): String? {
    return try {
        val sdf = SimpleDateFormat("HH:mm", getCurrentLocale(CalcApplication.getInstance().getContext()))
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

    val doubleString = bigInteger.setScale(2, RoundingMode.HALF_UP).toString()

    return if (doubleString.endsWith(".00")) {
        doubleString.replace(".00", "")
    } else {
        doubleString
    }
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

    return text.toString().toUpperCase(getCurrentLocale(CalcApplication.getInstance().getContext()))
}

fun getShareableStatistic(context: Context, statistic: Statistic): String {
    val builder = StringBuilder()

    fun getString(@StringRes resId: Int, vararg args: String): String {
        return context.getString(resId, *args)
    }

    statistic.let {
        builder.appendln("${getString(R.string.total_spend)}: ${doubleToStringPrice(it.totalSpend)} ${it.currency}")
        builder.appendln("${getString(R.string.total_debt)}: ${doubleToStringPrice(it.totalDebt)} ${it.currency}").appendln()

        it.membersStatistic.forEachIndexed { i, member->
            builder.appendln(getMemberStatistic(context, member, i + 1))
        }
    }

    return builder.toString()
}

fun getMemberStatistic(context: Context, member: MemberStatistic, index: Int? = null): String {
    fun getString(@StringRes resId: Int, vararg args: String): String {
        return context.getString(resId, *args)
    }

    val number = if (index != null) {
        "$index. "
    } else {
        ""
    }

    val memberBuilder = StringBuilder("$number${member.member.name}").appendln()

    if (member.totalSpend.isNotZero()) memberBuilder.appendln("${getString(R.string.spend)} ${doubleToStringPrice(member.totalSpend)} ${member.currency}")
    if (member.totalDebt.isNotZero())  memberBuilder.appendln("${getString(R.string.owed)} ${doubleToStringPrice(member.totalDebt)} ${member.currency}")
    if (member.totalLend.isNotZero())  memberBuilder.appendln("${getString(R.string.lend)} ${doubleToStringPrice(member.totalLend)} ${member.currency}")

    memberBuilder.appendln()

    val ordersBuilder = StringBuilder(getString(R.string.all_orders)).appendln(" ${member.member.name}")

    member.orders.forEach { order ->
        val opponent = when {
            order.ownerId == order.member.id -> {
                getString(R.string.for_yourself)
            }
            order is Order.OrderReceiver -> {
                getString(R.string.from_member, order.member.name)
            }
            else -> {
                getString(R.string.for_member, order.member.name)
            }
        }

        ordersBuilder.appendln("${order.title} $opponent ${doubleToStringPrice(order.price)} ${member.currency}")
    }

    memberBuilder.appendln(ordersBuilder)
    ordersBuilder.clear()

    val resultBuilder = StringBuilder(getString(R.string.result)).appendln()

    member.priceResult.forEach { result ->

        val opponent = when (result) {
            is Result.ResultLender -> {
                getString(R.string.debit, result.price.toString(), member.currency)
            }
            else -> {
                getString(R.string.debt, result.price.toString(), member.currency)
            }
        }

        resultBuilder.appendln("${result.member.name} $opponent")
    }

    memberBuilder.append(resultBuilder)
    resultBuilder.clear()

    return memberBuilder.toString()
}

fun shareStatistic(context: Context, text: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            text
        )
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}