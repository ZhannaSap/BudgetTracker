package com.example.budgettracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budgettracker.data.accounts.AccountEntity
import com.example.budgettracker.data.accounts.AccountsDao
import com.example.budgettracker.data.expences.ExpencesDao
import com.example.budgettracker.data.expences.ExpencesEntity
import com.example.budgettracker.data.income.IncomeDao
import com.example.budgettracker.data.income.IncomeEntity

@Database(entities = [ExpencesEntity::class, IncomeEntity::class, AccountEntity::class], version = 1)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun expencesDao(): ExpencesDao
    abstract fun incomeDao(): IncomeDao
    abstract fun accountDao(): AccountsDao
}