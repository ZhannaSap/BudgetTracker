package com.example.budgettracker.data.repository

import androidx.lifecycle.LiveData
import com.example.budgettracker.data.BudgetDatabase
import com.example.budgettracker.data.expences.ExpencesEntity
import com.example.budgettracker.data.income.IncomeEntity
import javax.inject.Inject

class Repository @Inject constructor(private val db: BudgetDatabase) {


    //Expences requets
    fun getAllExpences(): LiveData<List<ExpencesEntity>> = db.expencesDao().getAll()
    fun getAllExpencesByDays(): LiveData<List<ExpencesEntity>> = db.expencesDao().getAllByDate()
    fun getExpenceById(id: Int): ExpencesEntity = db.expencesDao().getItemById(id)
    fun getAllExpencesByPartialDate(partialDate: String): LiveData<List<ExpencesEntity>> =
        db.expencesDao().getAllByPartialDate(partialDate)
    suspend fun insertE(dataEntity: ExpencesEntity) = db.expencesDao().insert(dataEntity)
    suspend fun deleteE(dataEntity: ExpencesEntity) = db.expencesDao().delete(dataEntity)
    suspend fun updeteE(dataEntity: ExpencesEntity) = db.expencesDao().update(dataEntity)


    //Incomes requests

    fun getAllIncome(): LiveData<List<IncomeEntity>> = db.incomeDao().getAll()
    fun getAllIncomesByDays(): LiveData<List<IncomeEntity>> = db.incomeDao().getAllByDate()
    fun getIncomeById(id: Int): IncomeEntity = db.incomeDao().getItemById(id)
    suspend fun insertI(dataEntity: IncomeEntity) = db.incomeDao().insert(dataEntity)
    suspend fun deleteI(dataEntity: IncomeEntity) = db.incomeDao().delete(dataEntity)
    suspend fun updeteI(dataEntity: IncomeEntity) = db.incomeDao().update(dataEntity)
    fun getAllIncomeByPartialDate(partialDate: String): LiveData<List<IncomeEntity>> =
        db.incomeDao().getAllByPartialDate(partialDate)

}