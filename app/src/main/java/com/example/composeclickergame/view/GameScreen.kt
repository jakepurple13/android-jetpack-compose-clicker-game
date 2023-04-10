package com.example.composeclickergame.view

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composeclickergame.model.ItemData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    parentScope: CoroutineScope = rememberCoroutineScope(),
    score: Int = 0,
    rate: Int = 0,
    onIncrementButtonClicked: () -> Unit = {},
    itemDatas: List<ItemData>,
    itemCountMap: Map<String, Int>,
    onStoreItemPurchased: (ItemData) -> Unit
) {
    val storeBottomSheetState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        sheetContent = {
            StoreBottomSheet(
                itemDatas = itemDatas,
                itemCountMap = itemCountMap,
                onStoreItemPurchased = onStoreItemPurchased,
            )
        },
        scaffoldState = storeBottomSheetState
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    ScoreText(score)
                    Text(
                        text = "$rate/s",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(itemDatas) { data ->
                        ListItem(
                            headlineContent = { Text(text = "${data.name} (${itemCountMap[data.name] ?: 0})") },
                            trailingContent = { Text(text = "${data.rate}/s") },
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        FilledIconButton(
                            modifier = Modifier.size(80.dp),
                            onClick = onIncrementButtonClicked,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                modifier = Modifier.size(56.dp),
                            )
                        }
                    }
                    StoreButton(
                        onClick = { parentScope.launch { storeBottomSheetState.bottomSheetState.expand() } },
                    )
                }
            }
        }
    }
}

@Composable
private fun ScoreText(score: Int) {
    Text(
        text = "$" + animateIntAsState(score).value.toString(),
        style = MaterialTheme.typography.displayMedium,
    )
}

@Composable
private fun StoreButton(onClick: () -> Unit) {
    FilledTonalButton(
        modifier = Modifier.padding(16.dp),
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Store",
        )
        Box(modifier = Modifier.size(8.dp))
        Text(text = "Store")
    }
}