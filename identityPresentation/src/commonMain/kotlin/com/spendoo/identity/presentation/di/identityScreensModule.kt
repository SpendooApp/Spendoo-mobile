package com.spendoo.identity.presentation.di

import com.spendoo.identity.presentation.navigation.effector.Effector
import com.spendoo.identity.presentation.navigation.effector.EffectorImpl
import com.spendoo.identity.presentation.screen.signup.SignUpViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val identityScreensModule = module {
    singleOf(::EffectorImpl) bind Effector::class
    viewModelOf(::SignUpViewModel)
}