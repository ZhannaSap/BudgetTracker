package com.example.budgettracker.ui.income

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettracker.data.expences.ExpencesEntity
import com.example.budgettracker.databinding.ItemMoneyDataBinding

class IncomeAdapter (private val onClick: (dataModel: ExpencesEntity)-> Unit) : ListAdapter<ExpencesEntity, DataViewHolder>(NewsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            ItemMoneyDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClick(getItem(position))
        }
    }
}

class DataViewHolder(private val binding: ItemMoneyDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(dataEntity: ExpencesEntity) {
        binding.tvCategory.text = dataEntity.category
        binding.tvDate.text = dataEntity.date
        binding.tvSum.text = dataEntity.sum.toString()
    }
}

class NewsDiffUtil : DiffUtil.ItemCallback<ExpencesEntity>() {
    override fun areItemsTheSame(oldItem: ExpencesEntity, newItem: ExpencesEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ExpencesEntity, newItem: ExpencesEntity): Boolean {
        return oldItem == newItem
    }


}