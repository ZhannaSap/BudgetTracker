package com.example.budgettracker.ui.adddata

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.budgettracker.R
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etDate.requestFocus()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        binding.etDate.setText(formattedDate)

        incomeId = arguments?.getInt("incomeId", -1) ?: -1
        if (incomeId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val incomeEntity: IncomeEntity = viewModel.getIById(incomeId)
                withContext(Dispatchers.Main) {
                    if (incomeEntity != null) {
                        binding.etSum.setText(incomeEntity.sum.toString())
                        binding.etDate.setText(incomeEntity.date)
                        binding.etComment.setText(incomeEntity.comment)
                    }
                }
            }
        }
        binding.run {
            etDate.setOnClickListener {
                showDatePickerAlertDialog()
            }
            btnSave.setOnClickListener {
                if (incomeId != -1) {
                    update()
                } else {
                    insert()
                }
            }
            btnDelete.setOnClickListener {
                delete()
            }
        }
    }

    private fun FragmentAddIncomeBinding.update() {
        if (etSum.text.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните поле суммы!", Toast.LENGTH_SHORT).show()
        } else {
            val updatedData = IncomeEntity(
                id = incomeId,
                account = spinnerAccount.selectedItem.toString(),
                sum = etSum.text.toString().toInt(),
                date = etDate.text.toString(),
                comment = etComment.text.toString()
            )
            viewModel.updateI(updatedData)
            findNavController().navigateUp()
        }
    }

    private fun FragmentAddIncomeBinding.insert() {
        if (etSum.text.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните поле суммы!", Toast.LENGTH_SHORT).show()
        } else {
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

    private fun FragmentAddIncomeBinding.delete() {
        val data = IncomeEntity(
            id= incomeId,
            account = spinnerAccount.selectedItem.toString(),
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