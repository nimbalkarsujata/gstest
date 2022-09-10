package com.gs.test.ui.feature

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.gs.test.R
import com.gs.test.data.model.Item
import com.gs.test.data.model.Items
import com.gs.test.ui.feature.utils.UiState
import com.gs.test.ui.feature.utils.appDimension
import com.gs.test.viewmodel.ItemsDataViewModel


@SuppressLint("UnrememberedMutableState")
@Composable
fun ItemsListScreen(
    itemViewModel: ItemsDataViewModel,
    livedata: LiveData<UiState<Items>>,
    navCallBack: (Item) -> Unit
) {
    Column {
        val uiState: UiState<Items>? by livedata.observeAsState(livedata.value)
        when (uiState) {
            is UiState.Hidden -> {
            }
            is UiState.Loading -> {
                Loading()
            }
            is UiState.Error -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "No data available")
                }
            }
            is UiState.Success -> {
                (uiState as UiState.Success<Items>).data?.let {
                    ListData(itemViewModel = itemViewModel, itemList = it.results, navCallBack = navCallBack)
                }
            }
        }
    }
}

@Composable
fun ListData(itemViewModel: ItemsDataViewModel, itemList:LiveData<List<Item>>, navCallBack: (Item) -> Unit) {
    val list by itemList.observeAsState(itemList.value?:ArrayList())
    ListData(itemViewModel, list, navCallBack)
}

@Composable
fun ListData(itemViewModel: ItemsDataViewModel, itemList: List<Item>, navCallBack: (Item) -> Unit) {
    val isRefreshing by itemViewModel.isRefreshing.collectAsState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { itemViewModel.refreshData() },
    ) {
        LazyColumn(contentPadding = PaddingValues(appDimension(id = R.dimen.dimen_16))) {
            items(itemList) { item ->
                DataItem(item = item, navCallBack, itemViewModel)
            }
            item {
                LaunchedEffect(true) {
                    Log.d("Sujata","Sujata at end ---->")
                }
            }
        }
    }
}


@Composable
private fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun DataItem(item: Item, navCallBack: (Item) -> Unit, itemViewModel: ItemsDataViewModel) {
    val imageSize: Dp by animateDpAsState(targetValue = appDimension(id = R.dimen.dimen_80))
    Card(
        shape = RoundedCornerShape(appDimension(id = R.dimen.dimen_8)),
        elevation = appDimension(id = R.dimen.dimen_2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                appDimension(id = R.dimen.dimen_10),
                appDimension(id = R.dimen.dimen_10),
                appDimension(id = R.dimen.dimen_10),
                appDimension(id = R.dimen.dimen_10)
            )
            .clickable { navCallBack(item) }
    ) {
        Row(
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.url,
                contentDescription = null,
                modifier = Modifier
                    .size(imageSize)
                    .weight(1f)
                    .padding(appDimension(id = R.dimen.dimen_10))
                    .align(CenterVertically)
            )
            Column(
                modifier = Modifier
                    .weight(3f)
                    .align(CenterVertically)
                    .padding(appDimension(id = R.dimen.dimen_10))
                    .fillMaxWidth(0.8f)
            ) {
                Text(text = item.title, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(appDimension(id = R.dimen.dimen_4)))
                Text(
                    text = item.overview,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
                Spacer(modifier = Modifier.height(appDimension(id = R.dimen.dimen_4)))
                Text(
                    text = "Date: ${item.date}",
                    style = MaterialTheme.typography.caption,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
            Icon(
                imageVector = if (itemViewModel.itemFavLiveData.value?.contains(item) == true)
                    Icons.Filled.Favorite
                else
                    Icons.Default.FavoriteBorder,
                tint = colorResource(id = R.color.teal_200),
                contentDescription = "Icon Favorite",
                modifier = Modifier
                    .align(CenterVertically)
                    .weight(1f)
                    .padding(appDimension(id = R.dimen.dimen_8))
                    .clickable {
                        itemViewModel.updateValue(item)
                    }
            )
        }
    }
}

