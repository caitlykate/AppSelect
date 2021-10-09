package com.caitlykate.appselect.api.data

data class ApiData(
    val copyright: String,
    val has_more: Boolean,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)