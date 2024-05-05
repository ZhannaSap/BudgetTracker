package com.example.budgettracker.data.accounts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val accountName: String,
    var sum: Int? = 0
)
