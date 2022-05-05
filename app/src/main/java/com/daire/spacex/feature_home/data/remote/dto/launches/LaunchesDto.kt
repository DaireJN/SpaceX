package com.daire.spacex.feature_home.data.remote.dto.launches

data class LaunchesDto(
    val docs: List<Doc>?,
    val hasNextPage: Boolean?,
    val hasPrevPage: Boolean?,
    val limit: Int?,
    val nextPage: Int?,
    val offset: Int?,
    val page: Int?,
    val pagingCounter: Int?,
    val prevPage: Any?,
    val totalDocs: Int?,
    val totalPages: Int?
)