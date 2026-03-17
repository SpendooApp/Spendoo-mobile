package com.spendoo.identity.domain.di

import com.spendoo.identity.domain.service.AuthorizationService
import com.spendoo.identity.domain.useCase.LoginUseCase
import com.spendoo.identity.domain.useCase.validation.auth.ValidationUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::LoginUseCase)
    singleOf(::AuthorizationService)
    singleOf(::ValidationUseCase)
}