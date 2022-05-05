package com.daire.spacex.feature_home.presentation.home


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomePageReducer(initial: HomePageViewState = HomePageViewState()) {

    private val _state: MutableStateFlow<HomePageViewState> by lazy {
        MutableStateFlow(initial)
    }

    val state: StateFlow<HomePageViewState>
        get() = _state

    fun sendEvent(event: HomePageEvent) {
        reduce(_state.value, event)
    }

    private fun setState(newState: HomePageViewState) {
        _state.value = (newState)
    }

    private fun reduce(oldState: HomePageViewState, event: HomePageEvent) {
        when (event) {
            is HomePageEvent.OnCompanyInfoLoading -> {
                setState(oldState.copy(companyInfoState = Pair(HomePageLoadingState.LOADING, oldState.companyInfoState.second)))
            }
            is HomePageEvent.OnCompanyInfoSuccess -> {
                var state = oldState.copy()
                event.companyInfo.also { companyInfo ->
                    state =
                        state.copy(companyInfoState = Pair(HomePageLoadingState.LOADED, companyInfo))
                }
                setState(state)
            }
            is HomePageEvent.OnCompanyInfoFailed -> {
                setState(oldState.copy(companyInfoState = Pair(HomePageLoadingState.FAILED, oldState.companyInfoState.second)))
            }
            HomePageEvent.OnLaunchesFailed -> {
                setState(oldState.copy(launchesState = Pair(HomePageLoadingState.FAILED, oldState.launchesState.second)))
            }
            HomePageEvent.OnLaunchesLoading -> {
                setState(oldState.copy(launchesState = Pair(HomePageLoadingState.LOADING, oldState.launchesState.second)))
            }
            is HomePageEvent.OnLaunchesSuccess -> {
                var state = oldState.copy()
                event.launches.also { launches ->
                    state =
                        state.copy(
                            launchesState = Pair(HomePageLoadingState.LOADED, launches),
                            originalLaunchList = launches.launchItems,
                            availableLaunchYears = launches.launchItems.map {
                                it.year
                            }.distinct()
                        )
                }
                setState(state)
            }
            is HomePageEvent.OnLaunchesSorted -> {
                var state = oldState.copy()
                event.sortedList.also { sortedLaunches ->
                    state =
                        state.copy(
                            launchOrder = event.sortOrderType,
                            launchesState = Pair(
                                oldState.launchesState.first,
                                oldState.launchesState.second.copy(launchItems = sortedLaunches)
                            )
                        )
                }
                setState(state)
            }
            is HomePageEvent.OnLaunchesFiltered -> {
                var state = oldState.copy()
                event.filteredList.also { filteredLaunches ->
                    state =
                        state.copy(
                            launchesState = Pair(
                                oldState.launchesState.first,
                                oldState.launchesState.second.copy(launchItems = filteredLaunches)
                            )
                        )
                }
                setState(state)
            }
        }
    }
}