package com.spendoo.notifications.domain.scheduler

import com.spendoo.notifications.domain.entity.NotificationType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class NotificationReminderManager(
    private val scheduler: LocalNotificationScheduler
) {
    companion object {
        const val START_ID = 1000
        const val END_ID = 1024
        var reminderIntervalMs: Long = 6 * 60 * 60 * 1000L //6 hours
    }

    fun startReminderCycle(
        coroutineScope: CoroutineScope,
        title: String,
        body: String,
        isReminderEnabled: Boolean
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            scheduler.cancelAll(START_ID, END_ID)

            if (!isReminderEnabled) {
                return@launch
            }

            val totalReminders = END_ID - START_ID + 1
            for (i in 0 until totalReminders) {
                val reminderId = START_ID + i
                val delayMs = (i + 1) * reminderIntervalMs
                scheduler.schedule(reminderId, title, body, delayMs, NotificationType.TRACKING_REMINDER)
            }
        }
    }
}
