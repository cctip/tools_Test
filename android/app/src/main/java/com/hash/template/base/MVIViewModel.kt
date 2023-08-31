package com.hash.template.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KProperty1

open class MVIViewModel<T>(initState: T) : ViewModel() {

    protected var innerState: MutableStateFlow<T> = MutableStateFlow(initState)
    var errorState: MutableStateFlow<Throwable?> = MutableStateFlow(null)
    var loadingState: MutableStateFlow<LoadState?> = MutableStateFlow(null)

    private val exceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            onException(coroutineContext, throwable)
        }

    open fun onException(context: CoroutineContext, throwable: Throwable) {
        throwable.printStackTrace()
        launch {
            errorState.emit(throwable)
            stopLoading()
        }
    }

    val state: SharedFlow<T> = innerState.asSharedFlow()

    suspend fun setState(block: T.() -> T) {
        val v = block(innerState.value)
        innerState.emit(v)
    }

    suspend fun <K> onEach(field: KProperty1<T, K>, observer: (K) -> Unit) {
        innerState.map { field.get(it) }.distinctUntilChanged().collect {
            observer(it)
        }
    }

    suspend fun <K1, K2> onEach2(
        field1: KProperty1<T, K1>, field2: KProperty1<T, K2>, observer: (K1, K2) -> Unit
    ) {
        innerState.map {
            Action2(field1.get(it), field2.get(it))
        }.distinctUntilChanged().resolveSubscription { (a, b) ->
            observer(a, b)
        }
    }

    suspend fun <K1, K2, K3> onEach3(
        field1: KProperty1<T, K1>,
        field2: KProperty1<T, K2>,
        field3: KProperty1<T, K3>,
        observer: (K1, K2, K3) -> Unit
    ) {
        innerState.map {
            Action3(field1.get(it), field2.get(it), field3.get(it))
        }.distinctUntilChanged().resolveSubscription { (a, b, c) ->
            observer(a, b, c)
        }
    }

    private fun <T : Any> Flow<T>.resolveSubscription(action: suspend (T) -> Unit): Job {
        return viewModelScope.launch(start = CoroutineStart.UNDISPATCHED) {
            yield()
            collectLatest(action)
        }
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            block.invoke(this)
        }
    }

    fun <T> launchIn(
        dispatch: CoroutineDispatcher = Dispatchers.Main, block: suspend CoroutineScope.() -> T
    ) {
        viewModelScope.launch(exceptionHandler + dispatch) {
            block.invoke(this)
        }
    }

    fun <T> launchInIO(block: suspend CoroutineScope.() -> T) {
        launchIn(Dispatchers.IO, block)
    }

    fun <T> launchInMain(block: suspend CoroutineScope.() -> T) {
        launchIn(Dispatchers.Main, block)
    }

    fun clearError() {
        launch {
            errorState.emit(null)
        }
    }

    protected suspend fun startLoading() {
        loadingState.emit(LoadState.Loading)
    }

    protected suspend fun stopLoading() {
        loadingState.emit(null)
    }
}
