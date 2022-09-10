package com.gs.test.ui.feature

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import com.gs.test.R
import com.gs.test.data.model.Item
import com.gs.test.ui.feature.utils.UiState
import com.gs.test.ui.feature.utils.appDimension
import com.gs.test.viewmodel.ItemsDataViewModel

@Composable
fun ItemDetailScreen(livedata: LiveData<UiState<Item>>, itemViewModel: ItemsDataViewModel?, itemList: LiveData<List<Item>>) {
    val uiState: UiState<Item>? by livedata.observeAsState(livedata.value)
    val favoriteItemsList by itemList.observeAsState(itemList.value ?: ArrayList())
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
            (uiState as UiState.Success<Item>).data?.let {
                DetailScreen(it, itemViewModel, favoriteItemsList)
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
        CircularProgressIndicator(progress = 0.5f)
    }
}

@Composable
fun DetailScreen(item: Item, itemViewModel: ItemsDataViewModel?, favoriteItemsList: List<Item>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(appDimension(id = R.dimen.dimen_10))
            .shadow(
                appDimension(id = R.dimen.dimen_2),
                RoundedCornerShape(appDimension(id = R.dimen.dimen_8))
            )
    ) {
        Row(
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.date,
                modifier = Modifier
                    .padding(appDimension(id = R.dimen.dimen_20))
                    .weight(2f),
                style = MaterialTheme.typography.subtitle2
            )
            Icon(
                imageVector = if (favoriteItemsList.contains(item) == true)
                    Icons.Filled.Favorite
                else
                    Icons.Default.FavoriteBorder,
                tint = colorResource(id = R.color.teal_200),
                contentDescription = "Icon Favorite",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .padding(appDimension(id = R.dimen.dimen_8))
                    .clickable {
                        itemViewModel?.updateValue(item)
                    }
            )
        }

        Spacer(modifier = Modifier.height(appDimension(id = R.dimen.dimen_10)))

        AsyncImage(
            model = item.url,
            contentDescription = "",
            modifier = Modifier
                .size(appDimension(id = R.dimen.dimen_200))
                .align(Alignment.CenterHorizontally)
                .padding(appDimension(id = R.dimen.dimen_20))
        )

        Spacer(modifier = Modifier.height(appDimension(id = R.dimen.dimen_10)))

        Text(
            text = item.title,
            modifier = Modifier
                .padding(appDimension(id = R.dimen.dimen_20))
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.h5
        )

        Text(
            text = item.overview,
            modifier = Modifier
                .padding(appDimension(id = R.dimen.dimen_20))
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Preview
@Composable
fun TestPreview() {
    //Item(1, "Test data for item detail", "/test", "Test item")
    // ItemDetailScreen(items = Item)
}

