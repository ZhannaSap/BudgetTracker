package com.example.budgettracker.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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


    //Expences requests
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

    fun deleteE(dataEntity: ExpencesEntity) {
        viewModelScope.launch {
            repository.deleteE(dataEntity)
        }
    }
    fun getEByDays(): LiveData<List<ExpencesEntity>> = repository.getAllExpencesByDays()
    fun getAllExpences(): LiveData<List<ExpencesEntity>> = repository.getAllExpences()

    fun getEById(id: Int): ExpencesEntity = repository.getExpenceById(id)


    //Incomes requests

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

    fun deleteI(dataEntity: IncomeEntity) {
        viewModelScope.launch {
            repository.deleteI(dataEntity)
        }
    }

    fun getAllIncomes(): LiveData<List<IncomeEntity>> = repository.getAllIncome()

    fun getIByDays(): LiveData<List<IncomeEntity>> = repository.getAllIncomesByDays()
    fun  getIById(id:Int):IncomeEntity = repository.getIncomeById(id)

    fun getAllIncomeByPartialDate(partialDate: String): LiveData<List<IncomeEntity>> =
        repository.getAllIncomeByPartialDate(partialDate)

    fun getAllExpencesByPartialDate(partialDate: String): LiveData<List<ExpencesEntity>> =
        repository.getAllExpencesByPartialDate(partialDate)

}

