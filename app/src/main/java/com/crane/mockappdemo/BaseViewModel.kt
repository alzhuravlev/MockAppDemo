package com.crane.mockappdemo

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

enum class ProgressState {
    IDLE, LOADING
}

private var _progressCounter = 0

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val context: Context
        get() = getApplication<Application>().applicationContext

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val IO = Dispatchers.IO

    private val _progressState =
        MutableLiveData<ProgressState>().apply { value = ProgressState.IDLE }
    val progressState: LiveData<ProgressState>
        get() = _progressState

    protected val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    override fun onCleared() {
        job.cancel()
    }

    private fun incProgress() {
        val c = _progressCounter
        if (c == 0)
            _progressState.value = ProgressState.LOADING
        _progressCounter = c + 1
    }

    private fun decProgress() {
        var c = _progressCounter
        if (c > 0) c--
        _progressCounter = c
        if (c == 0)
            _progressState.value = ProgressState.IDLE
    }

    protected fun launch(
        sendErrorMessage: Boolean = true,
        sendProgress: Boolean = false,
        block: suspend () -> Unit
    ) {
        uiScope.launch {
            try {

                if (sendProgress)
                    incProgress()

                withContext(IO) {
                    block()
                }

            } catch (e: Throwable) {
                Log.e(TAG, e.message, e)
                if (sendErrorMessage)
                    _errorMessage.value = e.localizedMessage ?: "Unknown error"
            } finally {
                if (sendProgress)
                    decProgress()
            }
        }
    }

    companion object {
        private val TAG = "BaseViewModel"
    }
}