package com.example.budgettracker.data.income

import android.accounts.Account
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

    @Query("SELECT * FROM income")
    fun getAll(): LiveData<List<IncomeEntity>>

    @Update
    suspend fun update(dataEntity: IncomeEntity)

    @Query("SELECT * FROM income WHERE id = :itemId")
    fun getItemById(itemId: Int): IncomeEntity

    @Query("SELECT * FROM income WHERE account = :account")
    fun getAllByAccount(account: String): LiveData<List<IncomeEntity>>

    @Query("SELECT * FROM income ORDER BY date DESC")
    fun getAllByDate(): LiveData<List<IncomeEntity>>

    @Query("SELECT * FROM income WHERE date LIKE '%' || :partialDate || '%'")
    fun getAllByPartialDate(partialDate: String): LiveData<List<IncomeEntity>>
}