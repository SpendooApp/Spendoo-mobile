package com.spendoo.util

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import com.spendoo.categories.api.AddTransactionRoute
import com.spendoo.categories.api.CategoriesRoute
import com.spendoo.categories.api.FinancialActionRoute
import com.spendoo.categories.api.ScheduledPaymentDetailsRoute
import com.spendoo.categories.api.ScheduledPaymentsRoute
import com.spendoo.categories.api.TransactionDetailsRoute
import com.spendoo.categories.api.TopSpendingCategoriesRoute
import com.spendoo.categories.api.CategoryOffersRoute
import com.spendoo.categories.api.EditTransactionRoute
import com.spendoo.chatbot.api.ChatbotRoute
import com.spendoo.goals.api.AchievementsRoute
import com.spendoo.goals.api.GoalsRoute
import com.spendoo.home.api.HomeRoute
import com.spendoo.home.api.NotificationsRoute
import com.spendoo.identity.api.CreateNewPasswordRoute
import com.spendoo.identity.api.ForgetPasswordRoute
import com.spendoo.identity.api.LoginRoute
import com.spendoo.identity.api.OnBoardingRoute
import com.spendoo.identity.api.ProfileRoute
import com.spendoo.identity.api.SignUpRoute
import com.spendoo.identity.api.SplashRoute
import com.spendoo.identity.api.SubscriptionRoute
import com.spendoo.identity.api.VerifyEmailRoute
import com.spendoo.statistics.api.DownloadRoute
import com.spendoo.statistics.api.ExportRoute
import com.spendoo.statistics.api.StatisticsRoute
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

fun buildNavigationSerializerConfig(): SavedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(SplashRoute::class, SplashRoute.serializer())
            subclass(OnBoardingRoute::class, OnBoardingRoute.serializer())
            subclass(LoginRoute::class, LoginRoute.serializer())
            subclass(SignUpRoute::class, SignUpRoute.serializer())
            subclass(ForgetPasswordRoute::class, ForgetPasswordRoute.serializer())
            subclass(VerifyEmailRoute::class, VerifyEmailRoute.serializer())
            subclass(CreateNewPasswordRoute::class, CreateNewPasswordRoute.serializer())
            subclass(ProfileRoute::class, ProfileRoute.serializer())
            subclass(HomeRoute::class, HomeRoute.serializer())
            subclass(GoalsRoute::class, GoalsRoute.serializer())
            subclass(ChatbotRoute::class, ChatbotRoute.serializer())
            subclass(CategoriesRoute::class, CategoriesRoute.serializer())
            subclass(AddTransactionRoute::class, AddTransactionRoute.serializer())
            subclass(StatisticsRoute::class, StatisticsRoute.serializer())
            subclass(ScheduledPaymentsRoute::class, ScheduledPaymentsRoute.serializer())
            subclass(ScheduledPaymentDetailsRoute::class, ScheduledPaymentDetailsRoute.serializer())
            subclass(TransactionDetailsRoute::class, TransactionDetailsRoute.serializer())
            subclass(ExportRoute::class, ExportRoute.serializer())
            subclass(DownloadRoute::class, DownloadRoute.serializer())
            subclass(AchievementsRoute::class, AchievementsRoute.serializer())
            subclass(NotificationsRoute::class, NotificationsRoute.serializer())
            subclass(SubscriptionRoute::class, SubscriptionRoute.serializer())
            subclass(FinancialActionRoute::class, FinancialActionRoute.serializer())
            subclass(TopSpendingCategoriesRoute::class, TopSpendingCategoriesRoute.serializer())
            subclass(CategoryOffersRoute::class, CategoryOffersRoute.serializer())
            subclass(EditTransactionRoute::class, EditTransactionRoute.serializer())
        }
    }
}