package com.pratclot.employees.data.tools

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.pratclot.employees.R
import com.pratclot.employees.domain.DataItem
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.joda.time.LocalDate
import org.joda.time.Years
import org.joda.time.format.DateTimeFormat
import java.lang.Exception
import java.lang.NullPointerException

@BindingAdapter("setText")
fun TextView.setText(item: DataItem) {
    when (item) {
        is DataItem.Specialty -> text = item.name
        is DataItem.Employee -> {
            var parsedDate: LocalDate? = parseDate(item)
            val age = getAge(parsedDate)
            text = """
                ${item.l_name.toLowerCase().capitalize()} 
                ${item.f_name.toLowerCase().capitalize()} 
                ${age}
            """.trimIndent()
        }
    }
}

private fun getAge(parsedDate: LocalDate?): String {
    return when (parsedDate) {
        null -> "-"
        else -> Years.yearsBetween(parsedDate, LocalDate.now()).years.toString()
    }
}

private fun parseDate(item: DataItem.Employee): LocalDate? {
    val fmt = when (item.birthday?.indexOf("-")) {
        2 -> DateTimeFormat.forPattern("dd-MM-yyyy")
        4 -> DateTimeFormat.forPattern("yyyy-MM-dd")
        else -> DateTimeFormat.fullDate()
    }
    var parsedDate: LocalDate?
    try {
        parsedDate = fmt.parseLocalDate(item.birthday)
    } catch (e: IllegalArgumentException) {
        parsedDate = null
    }
    return parsedDate
}

@BindingAdapter("setImage")
fun ImageView.setImage(item: DataItem) {
    try {
        Picasso.get()
            .load((item as DataItem.Employee).avatr_url)
            .error(R.drawable.ic_baseline_not_interested_24)
            .into(this)
    } catch (e: java.lang.IllegalArgumentException) {
        setImageResource(R.drawable.ic_baseline_not_interested_24)
    }
}

@BindingAdapter("setProInfo")
fun TextView.setProInfo(item: DataItem) {
    val fmt = DateTimeFormat.forPattern("dd.MM.YYYY г.")
    var parsedDate: LocalDate? = parseDate(item as DataItem.Employee)
    val age = getAge(parsedDate)
    val fmtDate = try {
        fmt.print(parsedDate)
    } catch (e: java.lang.IllegalArgumentException) {
        "-"
    }
    text = """
        ${item.f_name.toLowerCase().capitalize()} ${item.l_name.toLowerCase().capitalize()}
        ${fmtDate}
        ${age}
        ${item.specialty.joinToString("\n        ") { it.name }}
    """.trimIndent()
}