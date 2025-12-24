package com.example.primohomepage.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primohomepage.domain.ArticleFeedsRepository
import com.example.primohomepage.domain.GetArticleFeedUseCase
import com.example.primohomepage.domain.model.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArticleFeedUseCase: GetArticleFeedUseCase
): ViewModel() {
    //todo for test -->
    //todo create state here with stateFlow then handle error case exception here
    // UI State: Using a StateFlow to hold the list of articleModels
    private val _homeScreenState = MutableStateFlow<HomeScreenState>(HomeScreenState())
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState

    fun getFeeds() {
        viewModelScope.launch {
            try {
               val (primoInfo, articleList) =  getArticleFeedUseCase()
                _homeScreenState.update {
                    it.copy(
                        primoInfo = primoInfo,
                        articleModels = articleList
                    )
                }
            } catch (e: Exception) {

            }
        }
    }
}