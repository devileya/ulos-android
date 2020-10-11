package com.devileya.ulos.view_model

import androidx.lifecycle.MutableLiveData
import com.devileya.ulos.domain.repository.UlosRepository
import com.devileya.ulos.model.UlosPredictionRequest
import com.devileya.ulos.model.UlosPredictionResponse
import com.devileya.ulos.utils.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Created by Arif Fadly Siregar 11/10/20.
 */
class UlosViewModel(private val repository: UlosRepository): BaseViewModel() {

    val ulosData = MutableLiveData<UlosPredictionResponse>()

    fun postUlosPrediction(imageBase64: String) {
        showLoading.value = true
        launch {
            val result = withContext(Dispatchers.Default) { repository.postUlosPrediction(UlosPredictionRequest(imageBase64)) }
            Timber.d("result vm $result")
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success<UlosPredictionResponse> -> {
                    Timber.d("success ${result.data}")
                    ulosData.postValue(result.data)
                }
                is UseCaseResult.Error -> {
                    showError.value = result.exception.message
                    Timber.d("showError ${showError.value}")
                }
            }
        }
    }

}