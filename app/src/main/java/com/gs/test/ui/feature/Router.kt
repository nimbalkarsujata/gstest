package com.gs.test.ui.feature

open class Router(val route: String) {
    object MainScreen : Router("main_screen")
    object FavListScreen : Router("favorite_list_screen")
    object DetailScreen : Router("detail_screen")
    object DateSelectedScreen : Router("selected_item_screen")
}

