package com.example.budgettracker.ui.adddata

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.budgettracker.R
import com.example.budgettracker.data.expences.ExpencesEntity
import com.example.budgettracker.databinding.FragmentAddExpencesBinding
import com.example.budgettracker.ui.viewModel.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class AddExpencesFragment : Fragment() {

    private lateinit var binding: FragmentAddExpencesBinding
    private val viewModel: BudgetViewModel by viewModels()
    private val calendar = Calendar.getInstance()
    private var expenceId = -1
    var incFirst = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddExpencesBinding.inflate(inflater, container, false)
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
            expenceId = arguments?.getInt("expenceId", -1) ?: -1
            if (expenceId != -1) {
                CoroutineScope(Dispatchers.IO).launch {
                    val expencesEntity: ExpencesEntity = viewModel.getEById(expenceId)
                    withContext(Dispatchers.Main) {
                        etSum.setText(expencesEntity.sum.toString())
                        etDate.setText(expencesEntity.date)
                        etComment.setText(expencesEntity.comment)

                        val accountName = expencesEntity.account
                        withContext(Dispatchers.Main) {
                            val adapter = binding.spinnerAccount.adapter as? ArrayAdapter<String>
                            val position = adapter?.getPosition(accountName)
                            if (position != null && position >= 0) {
                                binding.spinnerAccount.setSelection(position)
                            }
                        }

                        incFirst = expencesEntity.sum.toString()
                        val categoryPosition = resources.getStringArray(R.array.categories)
                            .indexOf(expencesEntity.category)
                        spinnerCategory.setSelection(categoryPosition)
                    }
                }
            }
            etDate.setOnClickListener {
                showDatePickerAlertDialog()
            }
            btnSave.setOnClickListener {
                if (expenceId != -1) {
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

    private fun FragmentAddExpencesBinding.update() {
        val account = spinnerAccount.selectedItem.toString()
        if (etSum.text.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните поле суммы!", Toast.LENGTH_SHORT)
                .show()
        } else {
            val updatedData = ExpencesEntity(
                id = expenceId,
                category = spinnerCategory.selectedItem.toString(),
                account = account,
                sum = etSum.text.toString().toInt(),
                date = etDate.text.toString(),
                comment = etComment.text.toString()
            )
            viewModel.updateE(updatedData)
            findNavController().navigateUp()
        }
    }

    private fun FragmentAddExpencesBinding.insert() {
        val account = spinnerAccount.selectedItem.toString()
        if (etSum.text.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните поле суммы!", Toast.LENGTH_SHORT)
                .show()
        } else {
            val data = ExpencesEntity(
                category = spinnerCategory.selectedItem.toString(),
                account = account,
                sum = etSum.text.toString().toInt(),
                date = etDate.text.toString(),
                comment = etComment.text.toString()
            )

            viewModel.insertE(data)
            findNavController().navigateUp()
        }
    }

    private fun FragmentAddExpencesBinding.delete() {
        val account = spinnerAccount.selectedItem.toString()
        if (etSum.text.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните поле суммы!", Toast.LENGTH_SHORT)
                .show()
        } else {
            val data = ExpencesEntity(
                id = expenceId,
                category = spinnerCategory.selectedItem.toString(),
                account = account,
                sum = etSum.text.toString().toInt(),
                date = etDate.text.toString(),
                comment = etComment.text.toString()
            )
            viewModel.deleteE(data)
            findNavController().navigateUp()
        }
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