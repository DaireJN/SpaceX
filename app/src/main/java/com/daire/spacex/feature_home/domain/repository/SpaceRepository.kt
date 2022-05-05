package com.daire.spacex.feature_home.domain.repository

import com.daire.spacex.common.Resource
import com.daire.spacex.feature_home.domain.model.CompanyInfo
import com.daire.spacex.feature_home.domain.model.launches.Launches
import kotlinx.coroutines.flow.Flow

interface SpaceRepository {
    suspend fun getCompanyInfo(): Flow<Resource<CompanyInfo>>
    suspend fun getLaunches(): Flow<Resource<Launches>>
}