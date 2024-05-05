package com.example.budgettracker.ui.income

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.budgettracker.R
import com.example.budgettracker.data.expences.ExpencesEntity
import com.example.budgettracker.data.income.IncomeEntity
import com.example.budgettracker.databinding.FragmentIncomeBinding
import com.example.budgettracker.ui.expences.ExpencesAdapter
import com.example.budgettracker.ui.viewModel.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class IncomeFragment : Fragment() {

    private lateinit var binding: FragmentIncomeBinding
    private val viewModel: BudgetViewModel by viewModels()
    private val adapter: IncomeAdapter = IncomeAdapter(this::onClick)
    private val calendar = Calendar.getInstance()
    var sumTotal = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvIncomes.adapter = adapter
        val dateFormat = SimpleDateFormat(".MM.yyyy", Locale.getDefault())
        val formattedDate:String = dateFormat.format(calendar.time)
        loadAllIncomes(formattedDate)
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_incomeFragment_to_addIncomeFragment2)
        }

        binding.searchView.setOnCloseListener {
            loadAllIncomes(formattedDate)
            false
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    performSearch(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    performSearch(newText)
                }
                return true
            }
        })
    }

    private fun loadAllIncomes(formattedDate:String) {
        viewModel.getAllIncomeByPartialDate(formattedDate).observe(viewLifecycleOwner) {
            adapter.submitList(it)
            sumTotal = 0
            it.forEach {
                sumTotal += it.sum
            }
            binding.tvBalanceTotal.text = sumTotal.toString()
        }
    }

    private fun performSearch(query: String) {
        viewModel.getAllIncomeByPartialDate(query).observe(viewLifecycleOwner) { incomes ->
            adapter.submitList(incomes)
            sumTotal = 0
            incomes.forEach {
                sumTotal +=it.sum
            }
            binding.tvBalanceTotal.text = sumTotal.toString()
        }
    }

    private fun onClick(dataEntity: IncomeEntity) {
        Log.e("ololo", "onClick: $dataEntity", )
        val action =
            IncomeFragmentDirections.actionIncomeFragmentToAddIncomeFragment2(dataEntity.id!!)
        findNavController().navigate(action)
    }
}
