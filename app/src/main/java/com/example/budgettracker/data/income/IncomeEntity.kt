package com.example.budgettracker.data.income

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income")
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val account: String,
    val sum: Int,
    val date: String,
    val comment: String? = " ",
)
