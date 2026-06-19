package com.spendoo.categories.presentation.screen.scheduledPayments

interface ScheduledPaymentsInteractionListener {
    fun onReload()
    fun onBackClicked()
    fun onAddScheduledPaymentClicked()
    fun onAddScheduledPaymentBottomSheetDismissed()
    fun setScheduledPaymentToEdit(payment: ScheduledPaymentUiState)
    fun onScheduledPaymentActionsSheetDismissed()
    fun onListScrolled()
    fun navigateToDetails(paymentId: String)
    fun onSkipPayment(paymentId: String)
    fun onPayPayment(paymentId: String)
}
