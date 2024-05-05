package com.example.budgettracker.ui.adddata

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.budgettracker.R
import com.example.budgettracker.data.accounts.AccountEntity
import com.example.budgettracker.data.expences.ExpencesEntity
import com.example.budgettracker.data.income.IncomeEntity
import com.example.budgettracker.databinding.FragmentAddExpencesBinding
import com.example.budgettracker.databinding.FragmentAddIncomeBinding
import com.example.budgettracker.ui.viewModel.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.Year
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class AddIncomeFragment : Fragment() {
    private lateinit var binding: FragmentAddIncomeBinding
    private val viewModel: BudgetViewModel by viewModels()
    private val calendar = Calendar.getInstance()
    private var incomeId = -1
    var incFirst = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllAccounts().observe(viewLifecycleOwner) { accounts ->
            val accountsList = accounts.map { it.accountName }
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, accountsList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerAccount.adapter = adapter
        }
        binding.etDate.requestFocus()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        binding.etDate.setText(formattedDate)
        binding.run {
            incomeId = arguments?.getInt("incomeId", -1) ?: -1
            if (incomeId != -1) {
                CoroutineScope(Dispatchers.IO).launch {
                    val incomeEntity: IncomeEntity = viewModel.getIById(incomeId)
                    withContext(Dispatchers.Main) {
                        etSum.setText(incomeEntity.sum.toString())
                        etDate.setText(incomeEntity.date)
                        etComment.setText(incomeEntity.comment)
                        incFirst = incomeEntity.sum.toString()

                        val accountName = incomeEntity.account
                        withContext(Dispatchers.Main) {
                            val adapter = binding.spinnerAccount.adapter as? ArrayAdapter<String>
                            val position = adapter?.getPosition(accountName)
                            if (position != null && position >= 0) {
                                binding.spinnerAccount.setSelection(position)
                            }
                        }
                    }
                }
            }

            etDate.setOnClickListener {
                showDatePickerAlertDialog()
            }
            btnSave.setOnClickListener {

                if (incomeId != -1) {
                    val accountEntity =
                        viewModel.getAccountByName(spinnerAccount.selectedItem.toString())
                    val sumFromEditText = etSum.text.toString().toIntOrNull() ?: 0
                    val diff = if (incFirst.isNotEmpty()) {
                        sumFromEditText - incFirst.toInt()
                    } else {
                        0
                    }
                    val sum = accountEntity.sum ?: 0
                    accountEntity.sum = sum + diff
                    viewModel.updateA(accountEntity)
                } else {
                    val accountEntity =
                        viewModel.getAccountByName(spinnerAccount.selectedItem.toString())
                    val sumFromEditText = etSum.text.toString().toIntOrNull() ?: 0
                    val sum = accountEntity.sum ?: 0
                    accountEntity.sum = sum + sumFromEditText
                    viewModel.updateA(accountEntity)
                }
                if (incomeId != -1) {
                    update()
                } else {
                    insert()
                }
            }
            btnDelete.setOnClickListener {
                if (incomeId != -1) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val accountEntity =
                            viewModel.getAccountByName(spinnerAccount.selectedItem.toString())
                        val sumFromEditText = etSum.text.toString().toIntOrNull() ?: 0
                        val sum = accountEntity.sum ?: 0
                        accountEntity.sum = sum - sumFromEditText
                        viewModel.updateA(accountEntity)
                        withContext(Dispatchers.Main) {
                            delete()
                        }
                    }
                }
            }
        }
    }

    private fun FragmentAddIncomeBinding.update() {
        val account = spinnerAccount.selectedItem.toString()
        if (etSum.text.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните поле суммы!", Toast.LENGTH_SHORT).show()
        } else {
            val updatedData = IncomeEntity(
                id = incomeId,
                account = account,
                sum = etSum.text.toString().toInt(),
                date = etDate.text.toString(),
                comment = etComment.text.toString()
            )
            viewModel.updateI(updatedData)
            findNavController().navigateUp()
        }
    }

    private fun FragmentAddIncomeBinding.insert() {
        val account = spinnerAccount.selectedItem.toString()
        if (etSum.text.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните поле суммы!", Toast.LENGTH_SHORT).show()
        } else {
            val data = IncomeEntity(
                account = account,
                sum = etSum.text.toString().toInt(),
                date = etDate.text.toString(),
                comment = etComment.text.toString()
            )
            viewModel.insertI(data)
            findNavController().navigateUp()
        }
    }

    private fun FragmentAddIncomeBinding.delete() {
        val account = spinnerAccount.selectedItem.toString()
        val data = IncomeEntity(
            id = incomeId,
            account = account,
            sum = etSum.text.toString().toInt(),
            date = etDate.text.toString(),
            comment = etComment.text.toString()
        )
        viewModel.deleteI(data)
        findNavController().navigateUp()
    }

    fun showDatePickerAlertDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDay = Calendar.getInstance()
                selectedDay.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDay.time)

                binding.etDate.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}