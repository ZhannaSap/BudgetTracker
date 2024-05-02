package com.example.budgettracker.ui.adddata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budgettracker.R
import com.example.budgettracker.databinding.FragmentAddDataBinding


class AddDataFragment : Fragment() {

    private lateinit var binding: FragmentAddDataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDataBinding.inflate(inflater, container, false)
        return binding.root
    }


}