package com.example.budgettracker.data.expences

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.budgettracker.data.income.IncomeEntity

@Dao
interface ExpencesDao {
    @Insert
    suspend fun insert(dataEntity: ExpencesEntity)

    @Delete
    suspend fun delete(dataEntity: ExpencesEntity)

    @Query("SELECT*FROM expences")
    fun getAll(): LiveData<List<ExpencesEntity>>

    @Update
    suspend fun update(dataEntity: ExpencesEntity)

    @Query("SELECT * FROM expences WHERE category = :category")
    fun getAllByCategory(category: String): LiveData<ExpencesEntity>

    @Query("SELECT * FROM expences WHERE id = :itemId")
    fun getItemById(itemId: Int): ExpencesEntity

    @Query("SELECT * FROM expences ORDER BY date DESC")
    fun getAllByDate(): LiveData<List<ExpencesEntity>>

    @Query("SELECT * FROM expences WHERE date LIKE '%' || :partialDate || '%'")
    fun getAllByPartialDate(partialDate: String): LiveData<List<ExpencesEntity>>
}