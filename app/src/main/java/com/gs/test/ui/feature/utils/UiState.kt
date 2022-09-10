package com.gs.test.ui.feature.utils

/**
 * UiState class maintains the state for compose widgets.
 * Each of the classes & object represent a state of the widget.
 * Further Assistance : https://stackoverflow.com/questions/44243763/how-to-make-sealed-classes-generic-in-kotlin/59422833
 */
sealed class UiState<out T>() {
    object Hidden : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    class Error(val throwable: Throwable? = null, val data: Any? = null) : UiState<Nothing>()
    class Success<T>(val data: T? = null) : UiState<T>()
}
