package com.gs.test.ui.feature

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gs.test.data.model.Item
@Composable
fun ItemDetailScreen(item: Item?) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp))
    ) {
        Text(
            text = "",
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(10.dp))

        AsyncImage(
            model = item?.url,
            contentDescription = "",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = item?.title?:"",
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.h5
        )

        Text(
            text = item?.overview?:"",
            modifier = Modifier
                .padding(20.dp)
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

