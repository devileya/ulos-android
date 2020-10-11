package com.devileya.ulos.domain.repository

import com.devileya.ulos.domain.network.ApiInteractor
import com.devileya.ulos.model.UlosPredictionRequest
import com.devileya.ulos.model.UlosPredictionResponse
import com.devileya.ulos.utils.UseCaseResult
import timber.log.Timber

/**
 * Created by Arif Fadly Siregar 11/10/20.
 */
interface UlosRepository {
    suspend fun postUlosPrediction(data: UlosPredictionRequest): UseCaseResult<UlosPredictionResponse>
}

class UlosRepositoryImpl(private val apiInteractor: ApiInteractor): UlosRepository {
    override suspend fun postUlosPrediction(data: UlosPredictionRequest): UseCaseResult<UlosPredictionResponse> {
        return try {
            val result = apiInteractor.postUlosPredictionAsync(data).await()
            Timber.d("result $result")
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            Timber.d("error repository $ex")
            UseCaseResult.Error(ex)
        }
    }
}