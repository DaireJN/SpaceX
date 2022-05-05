package com.daire.spacex.feature_home.domain.use_case

import com.daire.spacex.feature_home.domain.model.launches.LaunchItem

object FilterLaunchesUseCase {
    fun filterLaunchesByYear(year: String, originalList: List<LaunchItem>) =
        originalList.filter {
            it.year == year
        }
}
