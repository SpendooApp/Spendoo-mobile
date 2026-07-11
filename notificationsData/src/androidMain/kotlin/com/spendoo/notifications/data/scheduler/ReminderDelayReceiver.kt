package com.spendoo.notifications.data.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mmk.kmpnotifier.KMPNotifier
import com.mmk.kmpnotifier.local.localNotifier
import com.spendoo.notifications.domain.entity.NotificationType

class ReminderDelayReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getIntExtra("id", 1000) ?: 1000
        val title = intent?.getStringExtra("title") ?: "Track your spending"
        val body = intent?.getStringExtra("body") ?: "Don't forget to record your expenses today to keep your budget on track!"
        val type = intent?.getStringExtra("type") ?: NotificationType.TRACKING_REMINDER.name

        KMPNotifier.localNotifier.notify {
            this.id = id
            this.title = title
            this.body = body
            this.payloadData = mapOf("type" to type)
        }
    }
}
