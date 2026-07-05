package com.spendoo.identity.presentation.di

import com.spendoo.identity.presentation.screen.createNewPassword.CreateNewPasswordViewModel
import com.spendoo.identity.presentation.screen.forgetPassword.ForgetPasswordViewModel
import com.spendoo.identity.presentation.screen.login.LoginViewModel
import com.spendoo.identity.presentation.screen.onboarding.OnboardingViewModel
import com.spendoo.identity.presentation.screen.signup.SignUpViewModel
import com.spendoo.identity.presentation.screen.verifyEmail.VerifyEmailViewModel
import com.spendoo.identity.presentation.screen.addFollower.AddFollowerViewModel
import com.spendoo.identity.presentation.screen.editProfile.EditProfileViewModel
import com.spendoo.identity.presentation.screen.followers.FollowersViewModel
import com.spendoo.identity.presentation.screen.following.FollowingViewModel
import com.spendoo.identity.presentation.screen.profile.ProfileViewModel
import com.spendoo.identity.presentation.screen.subscription.SubscriptionViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val identityScreensModule = module {
    viewModelOf(::SignUpViewModel)
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ForgetPasswordViewModel)
    viewModelOf(::VerifyEmailViewModel)
    viewModelOf(::CreateNewPasswordViewModel)
    viewModelOf(::SubscriptionViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::FollowingViewModel)
    viewModelOf(::FollowersViewModel)
    viewModelOf(::AddFollowerViewModel)
    viewModelOf(::EditProfileViewModel)
}