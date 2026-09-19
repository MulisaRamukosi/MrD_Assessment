package com.example.mrd_assessment.core.network.model

import com.backbase.deferredresources.DeferredText

// used to give response to ui
data class RequestResult<T>(
    var data: T? = null,
    val message: DeferredText? = null,
    val version: Long = System.currentTimeMillis(),
) {
    fun requestFailed(): Boolean {
        val condition = data == null

        return when (data) {
            is Boolean -> {
                !(data as Boolean)
            }

            is List<*> -> {
                (data as List<*>).isEmpty() && message != null
            }

            else -> {
                condition
            }
        }
    }
}
