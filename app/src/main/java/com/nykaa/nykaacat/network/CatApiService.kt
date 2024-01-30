package com.nykaa.nykaacat.network

import com.nykaa.nykaacat.model.CatsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("images/search")
    suspend fun getCats(
        @Query("limit") limit: Int
    ): Response<List<CatsItem>>
}