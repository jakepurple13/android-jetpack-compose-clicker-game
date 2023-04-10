package com.example.composeclickergame.view

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeclickergame.model.ItemData
import com.example.composeclickergame.model.ItemDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val itemDataRepo: ItemDataRepository,
) : ViewModel() {
    val itemDatas get() = itemDataRepo.itemDatas

    val itemCountMap = mutableStateMapOf<String, Int>()

    var score by mutableStateOf(0)
        private set

    val rate by derivedStateOf {
        itemDatas.sumOf { itemData ->
            itemData.rate * (itemCountMap[itemData.name] ?: 0)
        }
    }

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                score += rate
            }
        }
    }

    fun onIncrementButtonClicked() {
        score += 1
    }

    fun onStoreItemPurchased(itemData: ItemData) {
        if (score < itemData.price) {
            return
        }
        score -= itemData.price
        itemCountMap[itemData.name] = (itemCountMap[itemData.name] ?: 0) + 1
    }
}