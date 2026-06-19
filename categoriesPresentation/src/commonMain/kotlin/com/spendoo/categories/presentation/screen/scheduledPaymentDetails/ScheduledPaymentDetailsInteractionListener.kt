package com.spendoo.categories.presentation.screen.scheduledPaymentDetails

interface ScheduledPaymentDetailsInteractionListener {
    fun onBackClicked()
    fun onEditClicked()
    fun onDeleteClicked()
    fun onSkipClicked()
    fun onPayClicked()
    fun onEditBottomSheetDismissed()
    fun onReloadDetails()
}
