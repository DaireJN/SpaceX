package com.daire.spacex.feature_home.data.remote.dto.launches

data class Links(
    val article: String?,
    val flickr: Flickr?,
    val patch: Patch?,
    val reddit: Reddit?,
    val webcast: String?,
    val wikipedia: String?,
    val youtube_id: String?
)