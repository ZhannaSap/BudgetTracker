package com.example.budgettracker.ui.expences

import android.os.Bundle
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
import com.example.budgettracker.databinding.FragmentExpencesBinding
import com.example.budgettracker.databinding.FragmentHomeBinding
import com.example.budgettracker.ui.income.IncomeAdapter
import com.example.budgettracker.ui.income.IncomeFragmentDirections
import com.example.budgettracker.ui.viewModel.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpencesFragment : Fragment() {
    private lateinit var binding: FragmentExpencesBinding
    private val adapter: ExpencesAdapter = ExpencesAdapter(this::onClick)
    private val viewModel: BudgetViewModel by viewModels()
    var sumTotal = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvBalanceAccounts.adapter = adapter

        loadAllExpences()


        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_expencesFragment_to_addDataFragment)
        }
        binding.searchView.setOnCloseListener {
            loadAllExpences()
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
                }else{
                    val list = emptyList<ExpencesEntity>()
                    adapter.submitList(list)
                }
                return true
            }
        })
    }

    private fun loadAllExpences() {
        viewModel.getEByDays().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            sumTotal = 0
            it.forEach {
                sumTotal += it.sum
            }
            binding.tvBalanceTotal.text = sumTotal.toString()
        }
    }

    private fun performSearch(query: String) {
        viewModel.getAllExpencesByPartialDate(query).observe(viewLifecycleOwner) { expences ->
            adapter.submitList(expences)
            sumTotal = 0
            expences.forEach {
                sumTotal +=it.sum
            }
            binding.tvBalanceTotal.text = sumTotal.toString()
        }
    }

    private fun onClick(expencesEntity: ExpencesEntity) {
        val action = ExpencesFragmentDirections.actionExpencesFragmentToAddDataFragment(expencesEntity.id!!)
        findNavController().navigate(action)
    }

}