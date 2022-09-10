package com.gs.test.ui.feature

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gs.test.R
import com.gs.test.di.Injector
import com.gs.test.ui.feature.utils.showPicker
import com.gs.test.ui.theme.ComposeSampleTheme
import com.gs.test.viewmodel.DataViewModelFactory
import com.gs.test.viewmodel.ItemDetailViewModel
import com.gs.test.viewmodel.ItemsDataViewModel
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var itemsDataViewModel: ItemsDataViewModel

    @Inject
    lateinit var viewModelFactory: DataViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as Injector).getDataComponent().inject(this)
        itemsDataViewModel =
            ViewModelProvider(this, viewModelFactory).get(ItemsDataViewModel::class.java)
        setContent {
            ComposeSampleTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        TopBarView(
                            context = this@MainActivity,
                            itemsDataViewModel = itemsDataViewModel,
                            navController
                        )
                        NasaApp(
                            itemsDataViewModel,
                            viewModelFactory = viewModelFactory,
                            navController
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun TopBarView(
    context: Context,
    itemsDataViewModel: ItemsDataViewModel,
    navController: NavHostController
) {
    TopAppBar(
        title = { Text(stringResource(R.string.gs_top_bar_title, "Welcome")) },
        backgroundColor = colorResource(id = R.color.teal_200),
        actions = {
            IconButton(onClick = {
                navController.navigate(Router.FavListScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite"
                )
            }


            IconButton(onClick = {
                showPicker(context = context, itemsDataViewModel)
            }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Search"
                )
            }
        }
    )
}

@Composable
fun NasaApp(
    itemViewModel: ItemsDataViewModel,
    viewModelFactory: DataViewModelFactory,
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Router.ListScreen.route) {
        composable(route = Router.ListScreen.route) {
            ItemsListScreen(itemViewModel, livedata = itemViewModel.getItemsLiveData()) {
                navController.navigate("${Router.DetailScreen.route}/${it.date}")
            }
        }
        composable(
            route = "${Router.DetailScreen.route}/{date}",
            arguments = listOf(navArgument("date") {
                type = NavType.StringType
            })
        ) {
            val date: String = it.arguments?.getString("date", "") ?: ""
            val detailsViewModel: ItemDetailViewModel =
                viewModel(factory = viewModelFactory.apply {
                    map["date"] = date
                })
            ItemDetailScreen(livedata = detailsViewModel.getItemsLiveData())
        }

        composable(route = Router.FavListScreen.route) {
            ListData(itemViewModel, itemViewModel.getFavItemsLiveData()) {
                navController.navigate("${Router.DetailScreen.route}/${it.date}")
            }
        }
    }
}