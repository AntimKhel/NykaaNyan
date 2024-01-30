package com.nykaa.nykaacat.utils

import androidx.annotation.Keep
import androidx.compose.runtime.Stable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> retryFlow(retryTrigger: RetryTrigger, flowProvider: () -> Flow<T>) =
    retryTrigger.retryEvent.filter { it == RetryTrigger.State.RETRYING }
        .flatMapConcat { flowProvider() }
        .onEach { retryTrigger.retryEvent.value = RetryTrigger.State.IDLE }

class RetryTrigger {
    enum class State { RETRYING, IDLE }

    val retryEvent = MutableStateFlow(State.RETRYING)

    fun retry() {
        retryEvent.value = State.RETRYING
    }
}

@Keep
@Stable
sealed class UIState<out R : Any> {
    class Success<out R : Any>(val data: R?) : UIState<R>()
    data object Loading : UIState<Nothing>()
    class Error<out R : Any>(val data: R?) : UIState<R>()
}