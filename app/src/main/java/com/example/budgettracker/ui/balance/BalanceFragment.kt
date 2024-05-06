package com.example.budgettracker.ui.balance

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.budgettracker.R
import com.example.budgettracker.data.accounts.AccountEntity
import com.example.budgettracker.data.income.IncomeEntity
import com.example.budgettracker.databinding.FragmentBalanceBinding
import com.example.budgettracker.databinding.FragmentHomeBinding
import com.example.budgettracker.ui.expences.ExpencesAdapter
import com.example.budgettracker.ui.viewModel.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BalanceFragment : Fragment() {
    private lateinit var binding: FragmentBalanceBinding
    private val adapter: AccountAdapter = AccountAdapter(this::onClick)
    private val viewModel: BudgetViewModel by viewModels()
    var incomesTotal = 0
    var expencesTotal = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBalanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvBalanceAccounts.adapter = adapter

        viewModel.getAllAccounts().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            countTotalSum()
        }


        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_balanceFragment_to_addAccountFragment)
        }
    }

    private fun updateAccountsBalance(balanceMap: Map<String, Int>) {
        val updatedAccounts = adapter.currentList.map { account ->
            val updatedSum = balanceMap[account.accountName] ?: 0
            account.copy(sum = updatedSum)
        }
        adapter.submitList(updatedAccounts)
    }

    private fun countTotalSum() {
        var expencesMap = mutableMapOf<String, Int>() // Хранит сумму расходов для каждого счета
        var incomesMap = mutableMapOf<String, Int>() // Хранит сумму доходов для каждого счета

        viewModel.getAllExpences().observe(viewLifecycleOwner) { expences ->
            expences.forEach { expence ->
                val currentSum = expencesMap[expence.account] ?: 0
                expencesMap[expence.account] = currentSum + expence.sum
            }

            viewModel.getAllIncomes().observe(viewLifecycleOwner) { incomes ->
                incomes.forEach { income ->
                    val currentSum = incomesMap[income.account] ?: 0
                    incomesMap[income.account] = currentSum + income.sum
                }

                // Теперь подсчитаем баланс для каждого счета
                val updatedAccounts = adapter.currentList.map { account ->
                    val expencesSum = expencesMap[account.accountName] ?: 0
                    val incomesSum = incomesMap[account.accountName] ?: 0
                    val balance = incomesSum - expencesSum
                    account.copy(sum = balance)
                }

                // Обновим список счетов
                adapter.submitList(updatedAccounts)

                // Теперь подсчитаем общий баланс
                var totalBalance = 0
                updatedAccounts.forEach { account ->
                    totalBalance += account.sum ?: 0
                }
                binding.tvBalanceTotal.text = totalBalance.toString()
            }
        }

    }

    private fun onClick(accountEntity: AccountEntity) {
        val action =
            BalanceFragmentDirections.actionBalanceFragmentToAddAccountFragment(accountEntity.id!!)
        findNavController().navigate(action)
    }
}