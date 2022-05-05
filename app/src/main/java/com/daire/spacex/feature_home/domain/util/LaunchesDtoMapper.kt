package com.daire.spacex.feature_home.domain.util

import com.daire.spacex.common.DateTimeUtil
import com.daire.spacex.core.domain.util.DomainMapper
import com.daire.spacex.feature_home.data.remote.dto.launches.LaunchesDto
import com.daire.spacex.feature_home.domain.model.launches.LaunchItem
import com.daire.spacex.feature_home.domain.model.launches.Launches
import com.daire.spacex.feature_home.domain.model.launches.RocketInfo
import javax.inject.Inject

class LaunchesDtoMapper @Inject constructor(val dateTimeUtil: DateTimeUtil) :
    DomainMapper<LaunchesDto, Launches> {
    override fun mapToDomainModel(model: LaunchesDto): Launches {
        return Launches(
            launchItems = model.docs?.map {
                LaunchItem(
                    imageUrl = it.links?.patch?.small ?: "",
                    name = it.name ?: "",
                    rocketInfo = RocketInfo(
                        rocketName = it.rocket?.name ?: "",
                        rocketType = it.rocket?.type ?: ""
                    ),
                    date = dateTimeUtil.getDateAsStringFromUnix(it.date_unix ?: 0),
                    time = dateTimeUtil.getTimeAsStringFromUnix(it.date_unix ?: 0),
                    success = it.success ?: false,
                    wasInPast = dateTimeUtil.getHasDateAlreadyOccurred(it.date_unix ?: 0),
                    daysSinceLaunch = dateTimeUtil.getDaysSinceUnix(it.date_unix ?: 0),
                    timeStamp = it.date_unix ?: 0,
                    year = dateTimeUtil.getYearAsStringFromUnix(it.date_unix ?: 0),
                    youtubeUrl = it.links?.webcast,
                    wikipediaUrl = it.links?.wikipedia,
                    articleUrl = it.links?.article
                )
            } ?: emptyList()
        )
    }

}