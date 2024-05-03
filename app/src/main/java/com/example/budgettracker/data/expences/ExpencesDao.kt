package com.example.budgettracker.data.expences

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

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
}