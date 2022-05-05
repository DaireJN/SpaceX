package com.daire.spacex.feature_home.domain.use_case

import com.daire.spacex.feature_home.domain.repository.SpaceRepository
import javax.inject.Inject

class GetLaunchesUseCase @Inject constructor(
    private val repository: SpaceRepository
) {
    suspend fun invoke() = repository.getLaunches()
}