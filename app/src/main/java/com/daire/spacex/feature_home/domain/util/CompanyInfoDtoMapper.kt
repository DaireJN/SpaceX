package com.daire.spacex.feature_home.domain.util

import com.daire.spacex.core.domain.util.DomainMapper
import com.daire.spacex.feature_home.data.remote.dto.company_info.CompanyInfoDto
import com.daire.spacex.feature_home.domain.model.CompanyInfo

class CompanyInfoDtoMapper : DomainMapper<CompanyInfoDto, CompanyInfo> {
    override fun mapToDomainModel(model: CompanyInfoDto): CompanyInfo {
        return CompanyInfo(
            founderName = model.founder,
            name = model.name,
            numberOfEmployees = model.employees,
            numberOfLaunchSites = model.launch_sites,
            valuation = model.valuation,
            yearFounded = model.founded
        )
    }
}