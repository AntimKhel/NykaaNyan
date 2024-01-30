package com.nykaa.nykaacat.network

import com.nykaa.nykaacat.model.CatsItem
import kotlinx.coroutines.flow.Flow

interface CatsRepository {
    fun getCats(limit: Int = 10): Flow<ApiResponse<List<CatsItem>>>
}