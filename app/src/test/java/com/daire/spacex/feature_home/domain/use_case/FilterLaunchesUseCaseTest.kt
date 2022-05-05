package com.daire.spacex.feature_home.domain.use_case

import com.daire.spacex.DataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class FilterLaunchesUseCaseTest {

    private val filterLaunchesUseCase = FilterLaunchesUseCase
    private val launchItems = DataFactory.getMockLaunches()

    @Test
    fun `ensure filter removes correct years`() {
        val filterYear = "1"
        val filteredList = filterLaunchesUseCase.filterLaunchesByYear(filterYear, launchItems)
        filteredList.forEach {
            assertEquals(filterYear, it.year);
        }
    }
}