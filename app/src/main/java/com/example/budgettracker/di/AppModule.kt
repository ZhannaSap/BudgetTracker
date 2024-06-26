package com.example.budgettracker.di

import android.content.Context
import androidx.room.Room
import com.example.budgettracker.data.BudgetDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): BudgetDatabase {
        return Room.databaseBuilder(
            context,
            BudgetDatabase::class.java,
            "database-budget"
        ).build()
    }
}