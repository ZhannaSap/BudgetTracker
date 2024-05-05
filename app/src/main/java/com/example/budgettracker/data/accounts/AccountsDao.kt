package com.example.budgettracker.data.accounts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.budgettracker.data.expences.ExpencesEntity

@Dao
interface AccountsDao {
    @Insert
    suspend fun insert(dataEntity: AccountEntity)

    @Query("SELECT * FROM accounts WHERE accountName = :name")
    fun getAccountByName(name: String): AccountEntity

    @Delete
    suspend fun delete(dataEntity: AccountEntity)

    @Query("SELECT * FROM accounts ORDER BY id ASC")
    fun getAll(): LiveData<List<AccountEntity>>

    @Update
    suspend fun update(dataEntity: AccountEntity)

    @Query("SELECT * FROM accounts WHERE id = :itemId")
    fun getAccountById(itemId: Int): AccountEntity
}