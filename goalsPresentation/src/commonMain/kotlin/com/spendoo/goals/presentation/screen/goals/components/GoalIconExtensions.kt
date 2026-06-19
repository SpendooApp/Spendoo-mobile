package com.spendoo.goals.presentation.screen.goals.components

import com.spendoo.shared.domain.entity.CategoryIcon
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

fun CategoryIcon.toDrawableResource(): DrawableResource {
    return when (this) {
        CategoryIcon.DEFAULT -> Res.drawable.ic_categories
        CategoryIcon.FOOD -> Res.drawable.ic_food
        CategoryIcon.TRANSPORT -> Res.drawable.ic_travel
        CategoryIcon.ENTERTAINMENT -> Res.drawable.ic_cinema
        CategoryIcon.HEALTHCARE -> Res.drawable.ic_health
        CategoryIcon.EDUCATION -> Res.drawable.ic_stats
        CategoryIcon.SHOPPING -> Res.drawable.ic_shopping
        CategoryIcon.TRAVEL -> Res.drawable.ic_transportation
        CategoryIcon.CAR -> Res.drawable.ic_car
        CategoryIcon.MOBILE -> Res.drawable.ic_mobile
        CategoryIcon.FINANCE -> Res.drawable.ic_loan
        CategoryIcon.COFFEE -> Res.drawable.ic_drink
        CategoryIcon.GIFTS -> Res.drawable.ic_gift
        CategoryIcon.PETS -> Res.drawable.ic_pet
        CategoryIcon.FITNESS -> Res.drawable.ic_gym
        CategoryIcon.UTILITIES -> Res.drawable.ic_thunder
        CategoryIcon.WIFI -> Res.drawable.ic_wifi
    }
}
