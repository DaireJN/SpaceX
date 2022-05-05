package com.daire.spacex

import com.daire.spacex.feature_home.domain.model.launches.LaunchItem
import com.daire.spacex.feature_home.domain.model.launches.RocketInfo

object DataFactory {
    fun getMockLaunches() =
        listOf(
            LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 1,
                year = "6",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            ),
            LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 4,
                year = "4",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            ),
            LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 6,
                year = "3",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            ),
            LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 3,
                year = "1",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            ),
            LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 9,
                year = "1",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            )
        )

    fun getMockLaunchesOrderedAsc() =
        listOf(
            LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 1,
                year = "6",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            ),
            LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 3,
                year = "1",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            ),
            LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 4,
                year = "4",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            ),
            LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 6,
                year = "3",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            ),
            LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 9,
                year = "1",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            )
        )

    fun getMockLaunchesOrderedDescending() =
        listOf(
            LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 9,
                year = "1",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            ),

            LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 6,
                year = "3",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            ),

             LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 4,
                year = "4",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            ),
            LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 3,
                year = "1",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            ),
             LaunchItem(
                name = "",
                date = "",
                time = "",
                imageUrl = "",
                rocketInfo = RocketInfo("", ""),
                success = false,
                wasInPast = false, daysSinceLaunch = "",
                timeStamp = 1,
                year = "6",
                youtubeUrl = "",
                articleUrl = "",
                wikipediaUrl = ""
            ),
        )

}