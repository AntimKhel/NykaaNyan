package com.nykaa.nykaacat.network

import com.nykaa.nykaacat.model.CatsItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

const val RETRY_TIME_IN_MILLIS = 500L
const val RETRY_ATTEMPT_COUNT = 1

class CatsRepositoryImpl(
    private val apiService: CatApiService,
    private val dispatcher: CoroutineDispatcher,
) : CatsRepository {
    override fun getCats(limit: Int): Flow<ApiResponse<List<CatsItem>>> = flow {
        emit(
            safeApiCall {
                apiService.getCats(limit)
            }
        )
    }.onStart { emit(ApiResponse.Loading()) }
        .retryWhen { cause, attempt ->
            if (cause is IOException && attempt < RETRY_ATTEMPT_COUNT) {
                delay(RETRY_TIME_IN_MILLIS)
                true
            } else {
                false
            }
        }
        .flowOn(dispatcher)
        .catch {
            emit(ApiResponse.Exception(it as? Exception))
        }
}