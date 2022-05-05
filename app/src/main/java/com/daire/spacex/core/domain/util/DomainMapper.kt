package com.daire.spacex.core.domain.util

interface DomainMapper<T, DomainModel> {
    fun mapToDomainModel(model: T): DomainModel
}