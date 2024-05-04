package com.example.budgettracker.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.budgettracker.R
import com.example.budgettracker.databinding.FragmentHomeBinding
import com.example.budgettracker.ui.viewModel.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    var expencesTotal = 0
    var incomesTotal = 0
    private val viewModel: BudgetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickers()
        binding.run {
            viewModel.getAllIncomes().observe(viewLifecycleOwner) {
                incomesTotal = 0
                it.forEach {
                    incomesTotal += it.sum
                }
                tvIncomeTotal.text = "+${incomesTotal.toString()}"
                tvBalanceTotal.text = (incomesTotal - expencesTotal).toString()
            }
            viewModel.getAllExpences().observe(viewLifecycleOwner) {
                expencesTotal = 0
                it.forEach {
                    expencesTotal += it.sum
                }
                tvExpencesTotal.text = "- ${expencesTotal.toString()}"
                tvBalanceTotal.text = (incomesTotal - expencesTotal).toString()
            }


        }
    }

    private fun initClickers() {
        binding.run {
            containerBalance.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_balanceFragment)
            }
            containerExpences.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_expencesFragment)
            }
            containerIncome.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_incomeFragment)
            }
        }
    }
}