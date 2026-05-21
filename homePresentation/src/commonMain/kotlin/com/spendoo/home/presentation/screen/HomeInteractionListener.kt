package com.spendoo.home.presentation.screen

interface HomeInteractionListener {
    fun onOfferClicked(offerId: String)
    fun onGoalClicked(goalId: String)
    fun onSpendingClicked(spendingId: String)
    fun onViewAllOffersClicked()
    fun onViewAllGoalsClicked()
    fun onViewAllSpendingClicked()
    fun onNotificationClicked()
    fun onProfileClicked()
}
