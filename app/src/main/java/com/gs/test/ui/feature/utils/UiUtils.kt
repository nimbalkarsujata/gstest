package com.gs.test.ui.feature.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
import com.gs.test.viewmodel.ItemDetailViewModel
import java.util.*

fun getDate(latest: Boolean): String {
    val c = Calendar.getInstance()
    if (latest)
        c.add(Calendar.MONTH, +1)
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    return "$year-$month-$day"
}

@Composable
@ReadOnlyComposable
fun appDimension(@DimenRes id: Int) = dimensionResource(id = id)


fun showPicker(context: Context, itemViewModel: ItemDetailViewModel) {
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
