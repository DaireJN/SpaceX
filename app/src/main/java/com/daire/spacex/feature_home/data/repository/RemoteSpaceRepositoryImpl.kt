package com.daire.spacex.feature_home.data.repository

import com.daire.spacex.common.HTTP_ERROR_MSG
import com.daire.spacex.common.IO_ERROR_MSG
import com.daire.spacex.common.Resource
import com.daire.spacex.feature_home.data.remote.SpaceApi
import com.daire.spacex.feature_home.domain.model.CompanyInfo
import com.daire.spacex.feature_home.domain.model.launches.Launches
import com.daire.spacex.feature_home.domain.repository.SpaceRepository
import com.daire.spacex.feature_home.domain.util.CompanyInfoDtoMapper
import com.daire.spacex.feature_home.domain.util.LaunchesDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteSpaceRepositoryImpl @Inject constructor(
    private val spaceApi: SpaceApi,
    private val companyInfoDtoMapper: CompanyInfoDtoMapper,
    private val launchesDtoMapper: LaunchesDtoMapper
) : SpaceRepository {
    override suspend fun getCompanyInfo(): Flow<Resource<CompanyInfo>> {
        return flow {
            try {
                emit(Resource.Loading<CompanyInfo>())
                val data = companyInfoDtoMapper.mapToDomainModel(spaceApi.getCompanyInfo())
                emit(Resource.Success(data))
            } catch (e: HttpException) {
                emit(Resource.Error<CompanyInfo>(e.localizedMessage ?: HTTP_ERROR_MSG))
            } catch (e: IOException) {
                emit(Resource.Error<CompanyInfo>(IO_ERROR_MSG))
            }
        }
    }

    override suspend fun getLaunches(): Flow<Resource<Launches>> {
        return flow {
            try {
                emit(Resource.Loading<Launches>())
                val data = launchesDtoMapper.mapToDomainModel(spaceApi.getAllLaunches())
                emit(Resource.Success(data))
            } catch (e: HttpException) {
                emit(Resource.Error<Launches>(e.localizedMessage ?: HTTP_ERROR_MSG))
            } catch (e: IOException) {
                emit(Resource.Error<Launches>(IO_ERROR_MSG))
            }
        }
    }


}