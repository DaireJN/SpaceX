package com.daire.spacex.di

import com.daire.spacex.common.Constants
import com.daire.spacex.common.DateTimeUtil
import com.daire.spacex.feature_home.data.remote.SpaceApi
import com.daire.spacex.feature_home.data.repository.RemoteSpaceRepositoryImpl
import com.daire.spacex.feature_home.domain.repository.SpaceRepository
import com.daire.spacex.feature_home.domain.use_case.FilterLaunchesUseCase
import com.daire.spacex.feature_home.domain.use_case.GetCompanyInfoUseCase
import com.daire.spacex.feature_home.domain.use_case.GetLaunchesUseCase
import com.daire.spacex.feature_home.domain.use_case.SortLaunchesUseCase
import com.daire.spacex.feature_home.domain.use_case.SpaceUseCases
import com.daire.spacex.feature_home.domain.util.CompanyInfoDtoMapper
import com.daire.spacex.feature_home.domain.util.LaunchesDtoMapper
import com.daire.spacex.feature_home.presentation.home.HomePageReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSpaceXApi(): SpaceApi =
        Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SpaceApi::class.java)

    @Provides
    @Singleton
    fun provideCompanyInfoDtoMapper() = CompanyInfoDtoMapper()

    @Provides
    @Singleton
    fun provideLaunchesDtoMapper(dateTimeUtil: DateTimeUtil) =
        LaunchesDtoMapper(dateTimeUtil = dateTimeUtil)

    @Provides
    @Singleton
    fun providesRemoteSpaceRepository(
        spaceApi: SpaceApi,
        companyInfoDtoMapper: CompanyInfoDtoMapper,
        launchesDtoMapper: LaunchesDtoMapper
    ): SpaceRepository = RemoteSpaceRepositoryImpl(
        spaceApi = spaceApi,
        companyInfoDtoMapper = companyInfoDtoMapper,
        launchesDtoMapper = launchesDtoMapper
    )

    @Provides
    @Singleton
    fun provideGetCompanyInfoUseCase(repository: SpaceRepository) =
        GetCompanyInfoUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideGetLaunchesUseCase(repository: SpaceRepository) =
        GetLaunchesUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideSpaceUseCases(
        getCompanyInfoUseCase: GetCompanyInfoUseCase,
        getLaunchesUseCase: GetLaunchesUseCase
    ) = SpaceUseCases(
        getCompanyInfoUseCase = getCompanyInfoUseCase,
        getLaunchesUseCase = getLaunchesUseCase,
        sortLaunchesUseCase = SortLaunchesUseCase,
        filterLaunchesUseCase = FilterLaunchesUseCase
    )

    @Provides
    @Singleton
    fun provideHomePageReducer(): HomePageReducer = HomePageReducer()

    @Provides
    @Singleton
    fun providesDateTimeUtil(): DateTimeUtil = DateTimeUtil


}