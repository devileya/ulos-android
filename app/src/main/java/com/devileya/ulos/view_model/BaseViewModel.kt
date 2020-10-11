package com.devileya.ulos.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by Arif Fadly Siregar 11/10/20.
 */
abstract class BaseViewModel : ViewModel(), CoroutineScope {
    companion object {
        val showError = MutableLiveData<String>()
        val showLoading = MutableLiveData<Boolean>()
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}