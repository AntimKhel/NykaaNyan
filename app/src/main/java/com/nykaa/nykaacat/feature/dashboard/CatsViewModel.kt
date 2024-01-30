package com.nykaa.nykaacat.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nykaa.nykaacat.model.CatsItem
import com.nykaa.nykaacat.network.ApiResponse
import com.nykaa.nykaacat.network.CatsRepository
import com.nykaa.nykaacat.utils.RetryTrigger
import com.nykaa.nykaacat.utils.UIState
import com.nykaa.nykaacat.utils.retryFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val catsRepository: CatsRepository
): ViewModel() {

    private val retryTrigger = RetryTrigger()

    val catsUIStateFlow: StateFlow<UIState<List<CatsItem>>> = retryFlow(retryTrigger) {
        catsRepository
            .getCats()
            .map { response ->
                when (response) {
                    is ApiResponse.Loading -> UIState.Loading
                    is ApiResponse.Success -> UIState.Success(response.data)
                    else -> UIState.Error(null)
                }
            }
            .catch {
                emit(UIState.Error(null))
            }
    }.stateIn(
        scope = viewModelScope,
        initialValue = UIState.Loading,
        started = SharingStarted.WhileSubscribed(5000L)
    )
}