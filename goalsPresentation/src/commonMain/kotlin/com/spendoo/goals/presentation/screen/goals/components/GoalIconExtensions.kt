package com.spendoo.goals.presentation.screen.goals.components

import com.spendoo.goals.domain.entity.GoalIcon
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_car
import spendoo.designsystem.generated.resources.ic_categories
import spendoo.designsystem.generated.resources.ic_cinema
import spendoo.designsystem.generated.resources.ic_drink
import spendoo.designsystem.generated.resources.ic_food
import spendoo.designsystem.generated.resources.ic_gift
import spendoo.designsystem.generated.resources.ic_gym
import spendoo.designsystem.generated.resources.ic_health
import spendoo.designsystem.generated.resources.ic_loan
import spendoo.designsystem.generated.resources.ic_mobile
import spendoo.designsystem.generated.resources.ic_pet
import spendoo.designsystem.generated.resources.ic_shopping
import spendoo.designsystem.generated.resources.ic_stats
import spendoo.designsystem.generated.resources.ic_thunder
import spendoo.designsystem.generated.resources.ic_transportation
import spendoo.designsystem.generated.resources.ic_travel
import spendoo.designsystem.generated.resources.ic_wifi

fun GoalIcon.toDrawableResource(): DrawableResource {
    return when (this) {
        GoalIcon.DEFAULT -> Res.drawable.ic_categories
        GoalIcon.FOOD -> Res.drawable.ic_food
        GoalIcon.TRANSPORT -> Res.drawable.ic_travel
        GoalIcon.ENTERTAINMENT -> Res.drawable.ic_cinema
        GoalIcon.HEALTHCARE -> Res.drawable.ic_health
        GoalIcon.EDUCATION -> Res.drawable.ic_stats
        GoalIcon.SHOPPING -> Res.drawable.ic_shopping
        GoalIcon.TRAVEL -> Res.drawable.ic_transportation
        GoalIcon.CAR -> Res.drawable.ic_car
        GoalIcon.MOBILE -> Res.drawable.ic_mobile
        GoalIcon.FINANCE -> Res.drawable.ic_loan
        GoalIcon.COFFEE -> Res.drawable.ic_drink
        GoalIcon.GIFTS -> Res.drawable.ic_gift
        GoalIcon.PETS -> Res.drawable.ic_pet
        GoalIcon.FITNESS -> Res.drawable.ic_gym
        GoalIcon.UTILITIES -> Res.drawable.ic_thunder
        GoalIcon.WIFI -> Res.drawable.ic_wifi
    }
}
