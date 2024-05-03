package com.example.budgettracker.ui.adddata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.budgettracker.R
import com.example.budgettracker.data.expences.ExpencesEntity
import com.example.budgettracker.data.income.IncomeEntity
import com.example.budgettracker.databinding.FragmentAddExpencesBinding
import com.example.budgettracker.databinding.FragmentAddIncomeBinding
import com.example.budgettracker.ui.viewModel.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddIncomeFragment : Fragment() {
    private lateinit var binding: FragmentAddIncomeBinding
    private val viewModel: BudgetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            btnSave.setOnClickListener {
                val data = IncomeEntity(
                    account = spinnerAccount.selectedItem.toString(),
                    sum = etSum.text.toString().toInt(),
                    date = etDate.text.toString(),
                    comment = etComment.text.toString()
                )
                viewModel.insertI(data)
                findNavController().navigateUp()
            }
        }
    }
}