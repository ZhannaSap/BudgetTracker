package com.example.budgettracker.ui.expences

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budgettracker.R
import com.example.budgettracker.databinding.FragmentExpencesBinding
import com.example.budgettracker.databinding.FragmentHomeBinding

class ExpencesFragment : Fragment() {
    private lateinit var binding: FragmentExpencesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentExpencesBinding.inflate(inflater,container,false)
        return  binding.root
    }

}