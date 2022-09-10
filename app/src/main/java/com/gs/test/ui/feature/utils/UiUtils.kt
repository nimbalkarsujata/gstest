package com.gs.test.ui.feature.utils

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.DimenRes
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.gs.test.viewmodel.ItemsDataViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun getDate(latest: Boolean): String {
    val c = Calendar.getInstance()
    if (latest)
        c.add(Calendar.MONTH, +1);
    val year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    return "$year-$month-$day"
}

fun <A> String.fromJson(type: Class<A>): A {
    return Gson().fromJson(this, type)
}

fun <A> A.toJson(): String? {
    return Gson().toJson(this)
}

@Composable
@ReadOnlyComposable
fun appFontDimension(@DimenRes id: Int) = dimensionResource(id = id).value.sp

@Composable
@ReadOnlyComposable
fun appDimension(@DimenRes id: Int) = dimensionResource(id = id)


fun showPicker(context: Context, itemViewModel: ItemsDataViewModel) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context, { _: DatePicker, day: Int, month: Int, year: Int ->
            itemViewModel.fetchBasedOnDateSelection(date = "$day-${month + 1}-$year")
        }, year, month, day
    )
    datePickerDialog.show()
}
