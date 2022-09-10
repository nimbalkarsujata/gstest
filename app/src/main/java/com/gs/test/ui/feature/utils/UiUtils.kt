package com.gs.test.ui.feature.utils

import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
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
