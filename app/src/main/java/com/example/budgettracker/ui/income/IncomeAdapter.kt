package com.example.budgettracker.ui.income

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettracker.data.expences.ExpencesEntity
import com.example.budgettracker.data.income.IncomeEntity
import com.example.budgettracker.databinding.ItemMoneyDataBinding

class IncomeAdapter (private val onClick: (dataModel: IncomeEntity)-> Unit) : ListAdapter<IncomeEntity, IncomeViewHolder>(
    NewsDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        return IncomeViewHolder(
            ItemMoneyDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClick(getItem(position))
        }
    }
}

class IncomeViewHolder(private val binding: ItemMoneyDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(dataEntity: IncomeEntity) {
        binding.tvCategory.text = dataEntity.account
        binding.tvDate.text = dataEntity.date
        binding.tvSum.text = dataEntity.sum.toString()
    }
}

class NewsDiffUtil : DiffUtil.ItemCallback<IncomeEntity>() {
    override fun areItemsTheSame(oldItem: IncomeEntity, newItem: IncomeEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: IncomeEntity, newItem: IncomeEntity): Boolean {
        return oldItem == newItem
    }


}