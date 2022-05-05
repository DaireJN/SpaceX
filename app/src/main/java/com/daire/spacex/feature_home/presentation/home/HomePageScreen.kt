package com.daire.spacex.feature_home.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.daire.spacex.R
import com.daire.spacex.feature_home.domain.model.CompanyInfo
import com.daire.spacex.feature_home.domain.model.launches.LaunchItem
import com.daire.spacex.feature_home.domain.model.launches.Launches
import com.daire.spacex.feature_home.domain.use_case.LaunchOrderType
import com.daire.spacex.ui.theme.SpaceXTheme

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceXTheme {
        Scaffold {
            HomePageScreen(homePageViewState = HomePageViewState(), {}, {}, {})
        }
    }
}

@Composable
fun HomePageScreen(
    homePageViewState: HomePageViewState,
    sortOrderSelected: (Pair<LaunchOrderType, List<LaunchItem>>) -> Unit,
    filterYearSelected: (Pair<String, List<LaunchItem>>) -> Unit,
    onLaunchMediaClicked: (String) -> Unit
) {
    Column {
        HomePageHeader(homePageViewState = homePageViewState, sortOrderSelected = sortOrderSelected, filterYearSelected = filterYearSelected)
        CompanyInfo(companyInfoState = homePageViewState.companyInfoState)
        Launches(launchesState = homePageViewState.launchesState, onLaunchMediaClicked)
    }
}

@Composable
fun HomePageHeader(
    homePageViewState: HomePageViewState,
    sortOrderSelected: (Pair<LaunchOrderType, List<LaunchItem>>) -> Unit,
    filterYearSelected: (Pair<String, List<LaunchItem>>) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f), text = stringResource(R.string.spacex), fontSize = 24.sp, textAlign = TextAlign.Center
        )
        IconButton(onClick = {
            openDialog.value = true
        }, enabled = homePageViewState.launchesState.second.launchItems.isNotEmpty(), content = {
            Icon(
                modifier = Modifier.size(32.dp, 32.dp),
                painter = painterResource(
                    id = R.drawable.ic_filter_on
                ),
                contentDescription = null,
            )
        })
    }
    if (openDialog.value) {
        FilterDialog(
            setShowDialog = {
                openDialog.value = it
            },
            yearsAvailable = homePageViewState.availableLaunchYears,
            sortOrderSelected = sortOrderSelected,
            originalLaunchItems = homePageViewState.originalLaunchList,
            launchItems = homePageViewState.launchesState.second.launchItems,
            filterYearSelected = filterYearSelected
        )
    }
}

@Composable
fun CompanyInfo(companyInfoState: Pair<HomePageLoadingState, CompanyInfo>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SubHeader(heading = stringResource(R.string.company))
        when (companyInfoState.first) {
            HomePageLoadingState.LOADED -> {
                Text(text = getCompanyInfoString(companyInfo = companyInfoState.second), fontSize = 16.sp, modifier = Modifier.padding(8.dp))
            }
            HomePageLoadingState.LOADING -> {
                LoadingView()
            }
            HomePageLoadingState.FAILED -> {
                NetworkFailedView(stringResource(R.string.company_info_failure))
            }
            HomePageLoadingState.NONE -> {
                LoadingView()
            }
        }
    }
}

@Composable
fun Launches(
    launchesState: Pair<HomePageLoadingState, Launches>,
    onLaunchMediaClicked: (String) -> Unit,
) {

    val currentSelectedItem =
        remember { mutableStateOf(launchesState.second.launchItems.firstOrNull()) }
    val openDialog = remember { mutableStateOf(false) }

    currentSelectedItem.value?.let {
        if (openDialog.value) {
            LaunchItemDialog(
                launchItem = it,
                setShowDialog = {
                    openDialog.value = it
                },
                onLaunchMediaClicked = onLaunchMediaClicked
            )
        }
    }

    SubHeader(heading = stringResource(R.string.launches))
    when (launchesState.first) {
        HomePageLoadingState.LOADED -> {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(launchesState.second.launchItems) { launchItem ->
                    LaunchItem(launchItem = launchItem, onLaunchItemClicked = {
                        openDialog.value = true
                        currentSelectedItem.value = launchItem
                    })
                }
            }
        }
        HomePageLoadingState.LOADING -> {
            LoadingView()
        }
        HomePageLoadingState.FAILED -> {
            NetworkFailedView(message = stringResource(R.string.unable_to_load_launches))

        }
        HomePageLoadingState.NONE -> {
            LoadingView()
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun LaunchItem(launchItem: LaunchItem, onLaunchItemClicked: (LaunchItem) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = {
                onLaunchItemClicked.invoke(launchItem)
            }),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AsyncImage(
            modifier = Modifier
                .size(width = 56.dp, height = 56.dp)
                .weight(1f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(launchItem.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "launch image"
        )
        Column(
            Modifier.weight(3f)
        ) {
            Text(textAlign = TextAlign.Start, text = "Mission: ${launchItem.name}")
            Text(textAlign = TextAlign.Start, text = "Date / Time: ${launchItem.date} at ${launchItem.time}")
            Text(textAlign = TextAlign.Start, text = "Rocket: ${launchItem.rocketInfo.rocketName} / ${launchItem.rocketInfo.rocketType}")
            Text(
                textAlign = TextAlign.Start, text = "Days: ${
                    if (launchItem.wasInPast) {
                        "since"
                    } else {
                        "from"
                    }
                } now: ${launchItem.daysSinceLaunch} days"
            )
        }
        Icon(
            modifier = Modifier
                .size(width = 32.dp, height = 32.dp)
                .weight(1f),
            painter = painterResource(
                id = if (launchItem.success) {
                    R.drawable.ic_launch_success
                } else {
                    R.drawable.ic_launch_failure
                }
            ),
            contentDescription = null,
        )
    }
}

@Composable
fun SubHeader(heading: String) {
    Box(
        modifier = Modifier
            .background(
                MaterialTheme.colors.primary
            )
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = heading,
            color = MaterialTheme.colors.secondaryVariant,
            fontSize = 16.sp
        )
    }
}

@Composable
fun LoadingView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(8.dp)
        )
    }
}

@Composable
fun NetworkFailedView(message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = message, fontSize = 24.sp)
    }
}

@Composable
fun FilterDialog(
    yearsAvailable: List<String>,
    setShowDialog: (Boolean) -> Unit,
    sortOrderSelected: (Pair<LaunchOrderType, List<LaunchItem>>) -> Unit,
    filterYearSelected: (Pair<String, List<LaunchItem>>) -> Unit,
    originalLaunchItems: List<LaunchItem>,
    launchItems: List<LaunchItem>
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.filter),
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = "",
                            tint = colorResource(android.R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }

                    val yearDropDownTitle = stringResource(R.string.filter_by_year)
                    var yearDropDownExpanded by remember { mutableStateOf(false) }
                    var yearSelected by remember { mutableStateOf(yearDropDownTitle) }

                    Box(modifier = Modifier.padding(start = 40.dp, top = 10.dp, end = 40.dp, bottom = 10.dp)) {
                        Button(onClick = { yearDropDownExpanded = !yearDropDownExpanded }) {
                            Text(yearSelected)
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = null,
                            )
                        }
                        DropdownMenu(
                            modifier = Modifier.height(150.dp),
                            expanded = yearDropDownExpanded,
                            onDismissRequest = { yearDropDownExpanded = false },
                        ) {
                            yearsAvailable.forEach { year ->
                                DropdownMenuItem(onClick = {
                                    yearDropDownExpanded = false
                                    yearSelected = year
                                }) {
                                    Text(text = year)
                                }
                            }
                        }
                    }

                    var descAscDropDownExpanded by remember { mutableStateOf(false) }
                    var descAscOptionSelected by remember { mutableStateOf("Sort By") }
                    var selectedSortType by remember { mutableStateOf(LaunchOrderType.NONE) }

                    Box(modifier = Modifier.padding(start = 40.dp, top = 10.dp, end = 40.dp, bottom = 10.dp)) {
                        Button(onClick = { descAscDropDownExpanded = !descAscDropDownExpanded }) {
                            Text(descAscOptionSelected)
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = null,
                            )
                        }
                        DropdownMenu(
                            modifier = Modifier.height(100.dp),
                            expanded = descAscDropDownExpanded,
                            onDismissRequest = { descAscDropDownExpanded = false },
                        ) {
                            DropdownMenuItem(onClick = {
                                descAscDropDownExpanded = false
                                descAscOptionSelected = "Ascending"
                                selectedSortType = LaunchOrderType.ASCENDING
                            }) {
                                Text(text = "Ascending")
                            }
                            DropdownMenuItem(onClick = {
                                descAscDropDownExpanded = false
                                descAscOptionSelected = "Descending"
                                selectedSortType = LaunchOrderType.DESCENDING
                            }) {
                                Text(text = "Descending")
                            }
                        }
                    }

                    Box(modifier = Modifier.padding(start = 40.dp, top = 10.dp, end = 40.dp, bottom = 10.dp)) {
                        Button(
                            onClick = {
                                setShowDialog(false)
                                if (selectedSortType != LaunchOrderType.NONE) {
                                    sortOrderSelected.invoke(Pair(selectedSortType, launchItems))
                                }
                                if (yearSelected != yearDropDownTitle) {
                                    filterYearSelected.invoke(Pair(yearSelected, originalLaunchItems))
                                }
                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = stringResource(R.string.apply_filter))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LaunchItemDialog(
    launchItem: LaunchItem,
    setShowDialog: (Boolean) -> Unit,
    onLaunchMediaClicked: (String) -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = launchItem.name,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "",
                        tint = colorResource(android.R.color.darker_gray),
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .clickable { setShowDialog(false) }
                    )
                }
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Box(modifier = Modifier.padding(start = 40.dp, top = 10.dp, end = 40.dp, bottom = 10.dp)) {
                            Button(
                                onClick = {
                                    launchItem.youtubeUrl?.let { onLaunchMediaClicked.invoke(it) }
                                    setShowDialog(false)
                                },
                                shape = RoundedCornerShape(50.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Text(text = stringResource(R.string.you_tube))
                            }
                        }
                    }
                }
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Box(modifier = Modifier.padding(start = 40.dp, top = 10.dp, end = 40.dp, bottom = 10.dp)) {
                            Button(
                                onClick = {
                                    launchItem.wikipediaUrl?.let { onLaunchMediaClicked.invoke(it) }
                                    setShowDialog(false)
                                },
                                shape = RoundedCornerShape(50.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Text(text = stringResource(R.string.wikipedia))
                            }
                        }
                    }
                }
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Box(modifier = Modifier.padding(start = 40.dp, top = 10.dp, end = 40.dp, bottom = 10.dp)) {
                            Button(
                                onClick = {
                                    launchItem.articleUrl?.let { onLaunchMediaClicked.invoke(it) }
                                    setShowDialog(false)
                                },
                                shape = RoundedCornerShape(50.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Text(text = stringResource(R.string.article))
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getCompanyInfoString(companyInfo: CompanyInfo): String {
    return "${companyInfo.name} was founded by ${companyInfo.founderName}" +
            " in ${companyInfo.yearFounded}." +
            " It has now ${companyInfo.numberOfEmployees} employees, " +
            "${companyInfo.numberOfLaunchSites} launch sites," +
            " and is valued at USD ${companyInfo.valuation}"
}
