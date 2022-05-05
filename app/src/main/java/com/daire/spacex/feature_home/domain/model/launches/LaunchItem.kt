package com.daire.spacex.feature_home.domain.model.launches

data class LaunchItem(
    val name: String,
    val date: String,
    val time: String,
    val imageUrl: String,
    val rocketInfo: RocketInfo,
    val success: Boolean,
    val wasInPast: Boolean,
    val daysSinceLaunch: String,
    val timeStamp: Int,
    val year: String,
    val youtubeUrl: String?,
    val articleUrl: String?,
    val wikipediaUrl: String?
)

data class RocketInfo(
    val rocketName: String,
    val rocketType: String
)
