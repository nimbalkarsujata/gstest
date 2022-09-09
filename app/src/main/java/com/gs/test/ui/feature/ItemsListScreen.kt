package com.gs.test.ui.feature

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import com.gs.test.data.model.Item
import com.gs.test.data.model.Items
import com.gs.test.ui.feature.utils.UiState
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun ItemsListScreen(livedata: LiveData<UiState<Items>>, navCallBack: (Item) -> Unit) {

    val uiState: UiState<Items>? by livedata.observeAsState(livedata.value)

    when (uiState) {
        is UiState.Hidden -> {
        }
        is UiState.Loading -> {
        }
        is UiState.Error -> {
            Text(text = "No data available")
        }
        is UiState.Success -> {
            (uiState as UiState.Success<Items>).data?.let {
                LazyColumn(contentPadding = PaddingValues(16.dp)) {
                    items(it.results) { item ->
                        DataItem(item = item, navCallBack)
                    }
                }
            }
        }
    }
}

@Composable
fun DataItem(item: Item, navCallBack: (Item) -> Unit) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val imageSize: Dp by animateDpAsState(targetValue = if (isExpanded) 150.dp else 80.dp)
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 10.dp, 15.dp, 10.dp)
            .clickable { navCallBack(item) }
    ) {
        Row(modifier = Modifier.animateContentSize()) {

            AsyncImage(
                model = item.url,
                contentDescription = null,
                modifier = Modifier
                    .size(imageSize)
                    .padding(10.dp)
                    .align(if (!isExpanded) CenterVertically else Top)
            )
            Column(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(10.dp)
                    .fillMaxWidth(0.8f)
            ) {
                Text(text = item.title, style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.overview,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                )
            }
            Icon(
                imageVector = if (isExpanded)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown,
                contentDescription = "Icon up down arrow",
                modifier = Modifier
                    .align(CenterVertically)
                    .clickable { isExpanded = !isExpanded }
            )
        }
    }
}

