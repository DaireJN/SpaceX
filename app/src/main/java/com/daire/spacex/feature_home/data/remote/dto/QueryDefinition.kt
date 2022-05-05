package com.daire.spacex.feature_home.data.remote.dto

data class QueryDefinition(
    val options: Options
)

data class Options(
    val populate: List<String>
)