package com.devileya.ulos.domain.network

import com.devileya.ulos.model.UlosPredictionRequest
import com.devileya.ulos.model.UlosPredictionResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Arif Fadly Siregar 11/10/20.
 */
interface ApiInteractor {
    @POST("predict")
    fun postUlosPredictionAsync(@Body data: UlosPredictionRequest): Deferred<UlosPredictionResponse>
}