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

        countTotalSum()

        viewModel.getAllAccounts().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_balanceFragment_to_addAccountFragment)
        }
    }

    private fun countTotalSum() {
        viewModel.getAllExpences().observe(viewLifecycleOwner) {
            expencesTotal = 0
            it.forEach {
                expencesTotal += it.sum
            }
            binding.tvBalanceTotal.text = (incomesTotal - expencesTotal).toString()
        }
        viewModel.getAllIncomes().observe(viewLifecycleOwner) {
            incomesTotal = 0
            it.forEach {
                incomesTotal += it.sum
            }
            binding.tvBalanceTotal.text = (incomesTotal - expencesTotal).toString()
        }

    }

    private fun onClick(accountEntity: AccountEntity) {
        val action =
            BalanceFragmentDirections.actionBalanceFragmentToAddAccountFragment(accountEntity.id!!)
        findNavController().navigate(action)
    }
}