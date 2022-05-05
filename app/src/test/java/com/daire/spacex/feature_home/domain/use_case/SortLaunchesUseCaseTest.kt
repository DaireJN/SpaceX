package com.daire.spacex.feature_home.domain.use_case

import com.daire.spacex.DataFactory
import junit.framework.Assert.assertEquals
import org.junit.Test

class SortLaunchesUseCaseTest {

    private val dataFactory = DataFactory
    private val sortLaunchesUseCase = SortLaunchesUseCase

    @Test
    fun `ensure sort order`() {
        val unorderedList = dataFactory.getMockLaunches()

        val sortedAscendingList =
            sortLaunchesUseCase.sortBy(LaunchOrderType.ASCENDING, unorderedList)
        assertEquals(
            dataFactory.getMockLaunchesOrderedAsc(), sortedAscendingList
        )

        val sortedDescendingList =
            sortLaunchesUseCase.sortBy(LaunchOrderType.DESCENDING, unorderedList)
        assertEquals(
            dataFactory.getMockLaunchesOrderedDescending(), sortedDescendingList
        )

        val sortedNoneList =
            sortLaunchesUseCase.sortBy(LaunchOrderType.NONE, unorderedList)
        assertEquals(
            unorderedList, sortedNoneList
        )

    }
}