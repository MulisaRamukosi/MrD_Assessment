package com.example.mrd_assessment.core.ui.model

import com.backbase.deferredresources.DeferredText

enum class State {
    SUCCESS,
    LOADING,
    FAILED,
    IDLE,
}

data class ViewStateContainer<T>(
    var viewState: T,
    val uiState: State = State.IDLE,
    val message: DeferredText? = null,
    val isInitialLoad: Boolean = true,
) {
    val isLoading: Boolean
        get() = uiState == State.LOADING
}
