package com.spendoo.home.presentation.screen

interface HomeInteractionListener {
    fun onReload()
    fun onOfferClicked(offerId: String)
    fun onGoalClicked(goalId: String)
    fun onSpendingClicked(categoryId: String)
    fun onViewAllOffersClicked()
    fun onViewAllGoalsClicked()
    fun onViewAllSpendingClicked()
    fun onNotificationClicked()
    fun onProfileClicked()
}
