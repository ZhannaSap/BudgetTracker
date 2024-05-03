package com.example.budgettracker.data.expences

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expences")
data class ExpencesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val category: String,
    val account: String,
    val sum: Int,
    val date: String,
    val comment: String? = " ",
)
