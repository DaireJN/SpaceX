package com.daire.spacex.feature_home.data.remote

import com.daire.spacex.feature_home.data.remote.dto.Options
import com.daire.spacex.feature_home.data.remote.dto.QueryDefinition
import com.daire.spacex.feature_home.data.remote.dto.company_info.CompanyInfoDto
import com.daire.spacex.feature_home.data.remote.dto.launches.LaunchesDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SpaceApi {
    @GET("company")
    suspend fun getCompanyInfo(): CompanyInfoDto

    @POST("launches/query")
    suspend fun getAllLaunches(
        @Body query: QueryDefinition = QueryDefinition(
            options = Options(listOf("rocket"))
        )
    ): LaunchesDto

}