package com.example.mycomposeapplication.ui.modules.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomposeapplication.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityVm @Inject constructor(
//    private val repo: Repository
) : ViewModel() {

    private val _cards = MutableStateFlow(listOf<Int>())
    val cards: StateFlow<List<Int>> get() = _cards

    private val _revealedCardIdsList = MutableStateFlow(listOf<Int>())
    val revealedCardIdsList: StateFlow<List<Int>> get() = _revealedCardIdsList

    init {
        getFakeData()
    }

    private fun getFakeData() {
        viewModelScope.launch(Dispatchers.Default) {
            val testList = arrayListOf<Int>()
            repeat(100) { testList += it }
            _cards.emit(testList)
        }
    }

    fun onItemExpanded(cardId: Int) {
        if (_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.add(cardId)
        }
    }

    fun onItemCollapsed(cardId: Int) {
        if (!_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.remove(cardId)
        }
    }

}