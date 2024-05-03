package com.example.budgettracker.data.repository

import androidx.lifecycle.LiveData
import com.example.budgettracker.data.BudgetDatabase
import com.example.budgettracker.data.expences.ExpencesEntity
import com.example.budgettracker.data.income.IncomeEntity
import javax.inject.Inject

class Repository @Inject constructor(private val db: BudgetDatabase) {

    fun getAllExpences(): LiveData<List<ExpencesEntity>> =db.expencesDao().getAll()

    suspend fun insertE(dataEntity: ExpencesEntity) = db.expencesDao().insert(dataEntity)
    suspend fun deleteE(dataEntity: ExpencesEntity) = db.expencesDao().delete(dataEntity)
    suspend fun updeteE(dataEntity: ExpencesEntity) = db.expencesDao().update(dataEntity)


    fun getAllIncome(): LiveData<List<IncomeEntity>> =db.incomeDao().getAll()

    suspend fun insertI(dataEntity: IncomeEntity) = db.incomeDao().insert(dataEntity)
    suspend fun deleteI(dataEntity: IncomeEntity) = db.incomeDao().delete(dataEntity)
    suspend fun updeteI(dataEntity: IncomeEntity) = db.incomeDao().update(dataEntity)
}