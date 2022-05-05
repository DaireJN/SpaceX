package com.daire.spacex.feature_home.presentation.home

import com.daire.spacex.feature_home.domain.model.CompanyInfo
import com.daire.spacex.feature_home.domain.model.launches.LaunchItem
import com.daire.spacex.feature_home.domain.model.launches.Launches
import com.daire.spacex.feature_home.domain.use_case.LaunchOrderType

data class HomePageViewState(
    val companyInfoState: Pair<HomePageLoadingState, CompanyInfo> = Pair(HomePageLoadingState.NONE, CompanyInfo()),
    val launchesState: Pair<HomePageLoadingState, Launches> = Pair(HomePageLoadingState.NONE, Launches(emptyList())),
    val availableLaunchYears: List<String> = emptyList(),
    val originalLaunchList: List<LaunchItem> = emptyList(),
    val launchOrder: LaunchOrderType = LaunchOrderType.NONE
)

sealed class HomePageIntent {
    object GetCompanyInfo : HomePageIntent()
    object GetLaunches : HomePageIntent()
    data class SortLaunches(
        val sortOrderType: LaunchOrderType,
        val originalList: List<LaunchItem>
    ) : HomePageIntent()

    data class FilterLaunches(
        val filterYear: String,
        val originalList: List<LaunchItem>,
        val sortOrderType: LaunchOrderType
    ) : HomePageIntent()
}

sealed class HomePageEvent {
    data class OnCompanyInfoSuccess(val companyInfo: CompanyInfo) : HomePageEvent()
    object OnCompanyInfoFailed : HomePageEvent()
    object OnCompanyInfoLoading : HomePageEvent()
    data class OnLaunchesSuccess(val launches: Launches) : HomePageEvent()
    object OnLaunchesFailed : HomePageEvent()
    object OnLaunchesLoading : HomePageEvent()
    data class OnLaunchesSorted(
        val sortedList: List<LaunchItem>,
        val sortOrderType: LaunchOrderType
    ) : HomePageEvent()

    data class OnLaunchesFiltered(
        val filteredList: List<LaunchItem>
    ) : HomePageEvent()
}

enum class HomePageLoadingState {
    LOADED, LOADING, FAILED, NONE
}