package com.daire.spacex.feature_home.domain.use_case

import com.daire.spacex.feature_home.domain.model.launches.LaunchItem

object SortLaunchesUseCase {
    fun sortBy(orderType: LaunchOrderType?, originalList: List<LaunchItem>): List<LaunchItem> {
        return when (orderType) {
            LaunchOrderType.ASCENDING -> {
                originalList.sortedBy {
                    it.timeStamp
                }
            }
            LaunchOrderType.DESCENDING -> {
                originalList.sortedByDescending {
                    it.timeStamp
                }
            }
            LaunchOrderType.NONE -> {
                originalList
            }
            else -> originalList
        }
    }
}

enum class LaunchOrderType {
    ASCENDING,
    DESCENDING,
    NONE
}

