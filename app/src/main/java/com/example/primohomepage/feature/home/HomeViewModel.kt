package com.example.primohomepage.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primohomepage.domain.ArticleFeedsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ArticleFeedsRepository
): ViewModel() {
    //todo for test -->
    //todo create state here with stateFlow then handle error case exception here
    // UI State: Using a StateFlow to hold the list of articles
//    private val _articles = MutableStateFlow<List<Article>>(emptyList())
//    val articles: StateFlow<List<Article>> = _articles
//
//    // Loading State: Useful for showing a ProgressBar
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading: StateFlow<Boolean> = _isLoading

    fun getFeeds() {
        viewModelScope.launch {
            repository.getFeeds().onSuccess {
                Log.d("json feeds success", it.toString())
            }.onFailure {
                it.localizedMessage?.let { it1 -> Log.d("json feeds failure", it1) }
            }
        }
    }
}