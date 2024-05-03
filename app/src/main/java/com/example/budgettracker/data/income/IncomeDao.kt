package com.example.budgettracker.data.income

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.budgettracker.data.expences.ExpencesEntity

@Dao
interface IncomeDao {
    @Insert
    suspend fun insert(dataEntity: IncomeEntity)

    @Delete
    suspend fun delete(dataEntity: IncomeEntity)

    @Query("SELECT*FROM income")
    fun getAll(): LiveData<List<IncomeEntity>>

    @Update
    suspend fun update(dataEntity: IncomeEntity)
}