package com.daire.spacex.feature_home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daire.spacex.common.Resource
import com.daire.spacex.feature_home.domain.model.launches.LaunchItem
import com.daire.spacex.feature_home.domain.use_case.LaunchOrderType
import com.daire.spacex.feature_home.domain.use_case.SpaceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val spaceUseCases: SpaceUseCases,
    private val homePageReducer: HomePageReducer
) : ViewModel() {

    fun onIntent(intent: HomePageIntent) {
        viewModelScope.launch {
            when (intent) {
                HomePageIntent.GetCompanyInfo -> getCompanyInfo()
                HomePageIntent.GetLaunches -> getLaunches()
                is HomePageIntent.SortLaunches -> sortLaunches(intent.sortOrderType, intent.originalList)
                is HomePageIntent.FilterLaunches -> filterLaunches(intent.filterYear, intent.originalList, intent.sortOrderType)
            }
        }
    }

    private suspend fun getCompanyInfo() {
        spaceUseCases.getCompanyInfoUseCase.invoke().onEach { resource ->
            when (resource) {
                is Resource.Error -> homePageReducer.sendEvent(HomePageEvent.OnCompanyInfoFailed)
                is Resource.Loading -> homePageReducer.sendEvent(HomePageEvent.OnCompanyInfoLoading)
                is Resource.Success -> {
                    resource.data?.let { companyInfo ->
                        homePageReducer.sendEvent(HomePageEvent.OnCompanyInfoSuccess(companyInfo))
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getLaunches() {
        spaceUseCases.getLaunchesUseCase.invoke().onEach { resource ->
            when (resource) {
                is Resource.Error -> homePageReducer.sendEvent(HomePageEvent.OnLaunchesFailed)
                is Resource.Loading -> homePageReducer.sendEvent(HomePageEvent.OnLaunchesLoading)
                is Resource.Success -> {
                    resource.data?.let { launches ->
                        homePageReducer.sendEvent(HomePageEvent.OnLaunchesSuccess(launches))
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun sortLaunches(
        launchOrderType: LaunchOrderType,
        originalList: List<LaunchItem>
    ) {
        homePageReducer.sendEvent(HomePageEvent.OnLaunchesSorted(spaceUseCases.sortLaunchesUseCase.sortBy(launchOrderType, originalList), launchOrderType))
    }

    private fun filterLaunches(
        filterYear: String,
        originalList: List<LaunchItem>,
        launchOrderType: LaunchOrderType,
    ) {
        // filter list and respect order
        val filteredList =
            spaceUseCases.filterLaunchesUseCase.filterLaunchesByYear(filterYear, originalList)
        homePageReducer.sendEvent(HomePageEvent.OnLaunchesFiltered(spaceUseCases.sortLaunchesUseCase.sortBy(launchOrderType, filteredList)))
    }
}