package com.caitlykate.appselect.api

import com.caitlykate.appselect.api.data.ApiData
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.nytimes.com/"

interface ApiRequest {

    @GET("svc/movies/v2/reviews/all.json")
    suspend fun getMovieList(@Query("offset") offset: Int,
                             @Query("api-key") apiKey: String): ApiData
}