package com.example.budgettracker.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgettracker.data.accounts.AccountEntity
import com.example.budgettracker.data.expences.ExpencesEntity
import com.example.budgettracker.data.income.IncomeEntity
import com.example.budgettracker.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    fun  getAllExpencesByAccount(account: String):LiveData<List<ExpencesEntity>> = repository.getAllExpencesByAccount(account)


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
    fun getIById(id: Int): IncomeEntity = repository.getIncomeById(id)
    fun getAllIncomeByPartialDate(partialDate: String): LiveData<List<IncomeEntity>> =
        repository.getAllIncomeByPartialDate(partialDate)

    fun getAllExpencesByPartialDate(partialDate: String): LiveData<List<ExpencesEntity>> =
        repository.getAllExpencesByPartialDate(partialDate)

    fun  getAllIncomesByAccount(account:String):LiveData<List<IncomeEntity>> = repository.getAllByAccount(account)

    //Accounts rquests
    fun insertA(dataEntity: AccountEntity) {
        viewModelScope.launch {
            repository.insert(dataEntity)
        }
    }

    fun deleteA(dataEntity: AccountEntity) {
        viewModelScope.launch {
            repository.delete(dataEntity)
        }
    }

    fun getAllAccounts(): LiveData<List<AccountEntity>> = repository.getAll()
    fun updateA(dataEntity: AccountEntity) {
        viewModelScope.launch {
            repository.update(dataEntity)
        }
    }

    fun getAccountById(itemId: Int): AccountEntity = repository.getAccountById(itemId)
    fun getAccountByName(name: String): AccountEntity {
        return repository.getAccountByName(name)
    }
}


