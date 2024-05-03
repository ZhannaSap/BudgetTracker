package com.example.budgettracker.ui.adddata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.budgettracker.data.expences.ExpencesEntity
import com.example.budgettracker.databinding.FragmentAddExpencesBinding
import com.example.budgettracker.ui.viewModel.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddExpencesFragment : Fragment() {

    private lateinit var binding: FragmentAddExpencesBinding
    private val viewModel: BudgetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddExpencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            btnSave.setOnClickListener {
                val data = ExpencesEntity(
                    category = spinnerCategory.selectedItem.toString(),
                    account = spinnerAccount.selectedItem.toString(),
                    sum = etSum.text.toString().toInt(),
                    date = etDate.text.toString(),
                    comment = etComment.text.toString()
                )
                viewModel.insertE(data)
                findNavController().navigateUp()
            }
        }
    }


}