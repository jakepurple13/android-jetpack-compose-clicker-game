package com.example.composeclickergame.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.composeclickergame.ui.theme.ComposeClickerGameTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        setContent {
            ComposeClickerGameTheme {
                GameScreen(
                    score = gameViewModel.score,
                    rate = gameViewModel.rate,
                    onIncrementButtonClicked = gameViewModel::onIncrementButtonClicked,
                    itemCountMap = gameViewModel.itemCountMap,
                    itemDatas = gameViewModel.itemDatas,
                    onStoreItemPurchased = gameViewModel::onStoreItemPurchased
                )
            }
        }
    }
}
