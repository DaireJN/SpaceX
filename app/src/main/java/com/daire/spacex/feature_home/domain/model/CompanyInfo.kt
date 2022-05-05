package com.daire.spacex.feature_home.domain.model

data class CompanyInfo(
    val name: String = "",
    val founderName: String = "",
    val yearFounded: Int = 0,
    val numberOfEmployees: Int = 0,
    val numberOfLaunchSites: Int = 0,
    val valuation: Long = 0
)