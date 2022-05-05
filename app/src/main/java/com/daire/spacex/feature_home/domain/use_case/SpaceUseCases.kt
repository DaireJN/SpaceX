package com.daire.spacex.feature_home.domain.use_case

data class SpaceUseCases(
    val getCompanyInfoUseCase: GetCompanyInfoUseCase,
    val getLaunchesUseCase: GetLaunchesUseCase,
    val sortLaunchesUseCase: SortLaunchesUseCase,
    val filterLaunchesUseCase: FilterLaunchesUseCase
)