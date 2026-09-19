package com.example.mrd_assessment.core.network.model

data class PageResult<T>(
    val data: List<T>,
    val page: Int,
    val hasMorePages: Boolean
)