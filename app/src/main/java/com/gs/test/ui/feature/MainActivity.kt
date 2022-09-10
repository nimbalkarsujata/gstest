package com.gs.test.ui.feature

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
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
import com.gs.test.ui.theme.ComposeSampleTheme
import com.gs.test.viewmodel.DataViewModelFactory
import com.gs.test.viewmodel.ItemDetailViewModel
import com.gs.test.viewmodel.ItemsDataViewModel
import java.util.*
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
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(
                    context, { _: DatePicker, day: Int, month: Int, year: Int ->
                        val date = "$day-${month + 1}-$year"
                        navController.navigate("${Router.DateSelectedScreen.route}/${date}")
                    }, year, month, day
                )
                datePickerDialog.show()
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
private fun navigateToDetails(
    date: String,
    favorite: Boolean,
    itemViewModel: ItemsDataViewModel,
    viewModelFactory: DataViewModelFactory,
) {
    val detailsViewModel: ItemDetailViewModel =
        viewModel(factory = viewModelFactory.apply {
            map["date"] = date
            map["favorites"] = favorite
        })
    ItemDetailScreen(
        livedata = detailsViewModel.getItemsLiveData(),
        itemViewModel = itemViewModel,
        itemViewModel.getFavItemsLiveData()
    )
}

@Composable
fun NasaApp(
    itemViewModel: ItemsDataViewModel,
    viewModelFactory: DataViewModelFactory,
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Router.MainScreen.route) {
        composable(route = Router.MainScreen.route) {
            navigateToDetails(
                "",
                false,
                itemViewModel = itemViewModel,
                viewModelFactory = viewModelFactory
            )
        }
        composable(
            route = "${Router.DetailScreen.route}/{date}",
            arguments = listOf(navArgument("date") {
                type = NavType.StringType
            })
        ) {
            val date: String = it.arguments?.getString("date", "") ?: ""
            navigateToDetails(
                date = date,
                true,
                itemViewModel = itemViewModel,
                viewModelFactory = viewModelFactory
            )
        }

        composable(
            route = "${Router.DateSelectedScreen.route}/{date}",
            arguments = listOf(navArgument("date") {
                type = NavType.StringType
            })
        ) {
            val date: String = it.arguments?.getString("date", "") ?: ""
            navigateToDetails(
                date = date,
                favorite = false,
                itemViewModel = itemViewModel,
                viewModelFactory = viewModelFactory
            )
        }

        composable(route = Router.FavListScreen.route) {
            ListData(itemViewModel, itemViewModel.getFavItemsLiveData()) {
                navController.navigate("${Router.DetailScreen.route}/${it.date}")
            }
        }
    }
}