package com.example.composeclickergame.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeclickergame.model.ItemData

@Preview
@Composable
fun StoreBottomSheet(
    itemDatas: List<ItemData> = emptyList(),
    itemCountMap: Map<String, Int> = emptyMap(),
    onStoreItemPurchased: (ItemData) -> Unit = {},
) {
    LazyColumn(
        Modifier.padding(top = 16.dp),
        content = {
            items(
                items = itemDatas,
                key = { it.name },
                itemContent = { itemData ->
                    StoreItem(
                        itemData,
                        itemCountMap[itemData.name] ?: 0,
                        onStoreItemPurchased,
                    )
                }
            )
        },
    )
}

@Composable
private fun StoreItem(
    itemData: ItemData,
    itemCount: Int,
    onItemPurchased: (ItemData) -> Unit,
) {
    ListItem(
        leadingContent = { Text(text = "($itemCount)") },
        headlineContent = { Text(text = itemData.name) },
        supportingContent = { Text(text = "${itemData.rate}/s") },
        trailingContent = {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                ElevatedButton(
                    onClick = { onItemPurchased(itemData) },
                ) { Text(text = "$${itemData.price}") }
            }
        },
    )
}
