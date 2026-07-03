package com.spendoo.identity.data.di

import com.russhwolf.settings.Settings
import com.spendoo.identity.data.repository.AuthenticationRepositoryImpl
import com.spendoo.identity.data.repository.ProfileRepositoryImpl
import com.spendoo.identity.data.repository.RegisterRepositoryImpl
import com.spendoo.identity.data.repository.ResetPasswordRepositoryImpl
import com.spendoo.identity.data.repository.SettingsRepositoryImpl
import com.spendoo.identity.domain.repository.AuthenticationRepository
import com.spendoo.identity.domain.repository.ProfileRepository
import com.spendoo.identity.domain.repository.RegisterRepository
import com.spendoo.identity.domain.repository.ResetPasswordRepository
import com.spendoo.identity.domain.repository.SettingsRepository
import com.spendoo.identity.domain.service.AuthorizationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private const val COIL_CLIENT = "CoilClient"
private const val BASE_URL = "baseUrl"
private const val IDENTITY_SCOPE = "IdentityScope"

val identityDataModule = module {
    singleOf(::Settings)
    singleOf(::AuthenticationRepositoryImpl) bind AuthenticationRepository::class
    singleOf(::ResetPasswordRepositoryImpl) bind ResetPasswordRepository::class
    singleOf(::RegisterRepositoryImpl) bind RegisterRepository::class
    singleOf(::ProfileRepositoryImpl) bind ProfileRepository::class
    singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class
    singleOf(::AuthorizationService)
    single {
        provideHttpClient(
            baseUrl = get<String>(named(BASE_URL)),
            authorizationService = { get<AuthorizationService>() },
            settingsRepository = { get<SettingsRepository>() },
            globalNavigationHandler = get(),
        )
    }
    single(named(COIL_CLIENT)) {
        provideCoilClient()
    }
    single(named(IDENTITY_SCOPE)) { CoroutineScope(Dispatchers.Default) }
    includes(platformIdentityDataModule)
}
