package com.gs.test.ui.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gs.test.data.model.Item
import com.gs.test.di.Injector
import com.gs.test.ui.feature.utils.fromJson
import com.gs.test.ui.feature.utils.toJson
import com.gs.test.ui.theme.ComposeSampleTheme
import com.gs.test.viewmodel.DataViewModelFactory
import com.gs.test.viewmodel.ItemsDataViewModel
import javax.inject.Inject


class MainActivity : ComponentActivity() {

    private lateinit var itemsDataViewModel: ItemsDataViewModel

    @Inject
    lateinit var viewModelFactory: DataViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as Injector).getDataComponent().inject(this)
        itemsDataViewModel = ViewModelProvider(this, viewModelFactory).get(ItemsDataViewModel::class.java)
        setContent {
            ComposeSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NasaApp(itemsDataViewModel, viewModelFactory = viewModelFactory)
                }
            }
        }
    }
}

@Composable
fun NasaApp(itemViewModel: ItemsDataViewModel, viewModelFactory: DataViewModelFactory) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "destination_item_list") {
        composable("destination_item_list") {
            ItemsListScreen( livedata = itemViewModel.getItemsLiveData()) {
                val itemString = it.toJson()
                navController.navigate("destination_item_details/$itemString")
            }
        }
        composable(
            "destination_item_details/{item}",
            arguments = mutableStateListOf(navArgument("item") {
                type = NavType.StringType
            })
        ) {
            val value: String = it.arguments?.getString("item", "") ?: ""
            val dataItem = value.fromJson(Item::class.java)
            ItemDetailScreen(dataItem)
        }
    }
}