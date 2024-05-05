package com.example.budgettracker.ui.adddata

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.budgettracker.R
import com.example.budgettracker.data.accounts.AccountEntity
import com.example.budgettracker.databinding.FragmentAddAccountBinding
import com.example.budgettracker.databinding.FragmentAddIncomeBinding
import com.example.budgettracker.ui.viewModel.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.file.Files.delete

@AndroidEntryPoint
class AddAccountFragment : Fragment() {
    private lateinit var binding: FragmentAddAccountBinding
    private val viewModel: BudgetViewModel by viewModels()
    private var accountId = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountId = arguments?.getInt("accountId", -1) ?: -1
        binding.run {
            if (accountId != -1) {
                CoroutineScope(Dispatchers.IO).launch {
                    val accountEntity: AccountEntity = viewModel.getAccountById(accountId)
                    withContext(Dispatchers.Main) {
                        etName.setText(accountEntity.accountName)
                    }
                }
            }
            btnSave.setOnClickListener {
                if (accountId!= -1){
                    updateAcocount()
                }else{
                    insertAcccount()
                }
            }
            btnDelete.setOnClickListener {
                if (accountId != -1) {
                    delete()
                }
            }
        }
    }

    private fun delete() {
        binding.run {
            if (etName.text.toString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Поле названия не должно быть пустым!!!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val toDeleteAccount = AccountEntity(
                    id = accountId,
                    accountName = etName.text.toString(),
                )
                viewModel.deleteA(toDeleteAccount)
                findNavController().navigateUp()
            }
        }
    }

    private fun insertAcccount() {
        binding.run {
            if (etName.text.toString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Поле названия не должно быть пустым!!!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val accountEntity = AccountEntity(
                    accountName = etName.text.toString(),
                )
                viewModel.insertA(accountEntity)
                findNavController().navigateUp()
            }
        }
    }

    private fun updateAcocount() {
        binding.run {
            if (etName.text.toString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Поле названия не должно быть пустым!!!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val updatedAccount = AccountEntity(
                    id = accountId,
                    accountName = etName.text.toString(),
                )
                viewModel.updateA(updatedAccount)
                findNavController().navigateUp()
            }
        }
    }
}