package com.daire.spacex.feature_home.data.remote.dto.launches

data class Core(
    val core: String?,
    val flight: Int?,
    val gridfins: Boolean?,
    val landing_attempt: Boolean?,
    val legs: Boolean?,
    val reused: Boolean?
)