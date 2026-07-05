package com.spendoo.categories.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_titles")
data class ExpenseTitleEntity(
    @PrimaryKey
    val title: String,
    val createdAt: Long = 0L
)
