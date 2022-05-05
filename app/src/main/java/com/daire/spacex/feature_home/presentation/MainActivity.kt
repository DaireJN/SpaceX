package com.daire.spacex.feature_home.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.daire.spacex.feature_home.presentation.home.HomePageIntent
import com.daire.spacex.feature_home.presentation.home.HomePageReducer
import com.daire.spacex.feature_home.presentation.home.HomePageScreen
import com.daire.spacex.feature_home.presentation.home.HomePageViewModel
import com.daire.spacex.ui.theme.SpaceXTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<HomePageViewModel>()
    @Inject lateinit var homePageReducer: HomePageReducer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onIntent(HomePageIntent.GetCompanyInfo)
        viewModel.onIntent(HomePageIntent.GetLaunches)

        setContent {
            val viewState by homePageReducer.state.collectAsState()

            SpaceXTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    HomePageScreen(homePageViewState = viewState,
                        sortOrderSelected = {
                            viewModel.onIntent(HomePageIntent.SortLaunches(it.first, it.second))
                        },
                        filterYearSelected = {
                            viewModel.onIntent(HomePageIntent.FilterLaunches(it.first, it.second, viewState.launchOrder))
                        },
                        onLaunchMediaClicked = {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                        }
                    )
                }
            }
        }
    }
}