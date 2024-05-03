package com.example.budgettracker.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgettracker.data.expences.ExpencesEntity
import com.example.budgettracker.data.income.IncomeEntity
import com.example.budgettracker.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun insertE(dataEntity: ExpencesEntity) {
        viewModelScope.launch {
            repository.insertE(dataEntity)
        }
    }

    fun updateE(dataEntity: ExpencesEntity) {
        viewModelScope.launch {
            repository.updeteE(dataEntity)
        }
    }

    suspend fun deleteE(dataEntity: ExpencesEntity) {
        viewModelScope.launch {
            repository.deleteE(dataEntity)
        }
    }

    fun getAllExpences(): LiveData<List<ExpencesEntity>> = repository.getAllExpences()

    fun insertI(dataEntity: IncomeEntity) {
        viewModelScope.launch {
            repository.insertI(dataEntity)
        }
    }

    fun updateI(dataEntity: IncomeEntity) {
        viewModelScope.launch {
            repository.updeteI(dataEntity)
        }
    }

    suspend fun deleteI(dataEntity: IncomeEntity) {
        viewModelScope.launch {
            repository.deleteI(dataEntity)
        }
    }

    fun getAllIncomes(): LiveData<List<IncomeEntity>> = repository.getAllIncome()
}

