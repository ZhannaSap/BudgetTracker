package com.example.budgettracker.ui.expences

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettracker.data.expences.ExpencesEntity
import com.example.budgettracker.databinding.ItemMoneyDataBinding

class ExpencesAdapter (private val onClick: (dataModel: ExpencesEntity)-> Unit) : ListAdapter<ExpencesEntity, DataViewHolder>(
    ExpencesDiffUtil()
) {

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
        val item = getItem(position)
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }
}

class DataViewHolder(private val binding: ItemMoneyDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(dataEntity: ExpencesEntity) {
        binding.tvCategory.text = dataEntity.category
        binding.tvDate.text = dataEntity.date
        binding.tvSum.text = dataEntity.sum.toString()
        binding.tvComment.text = dataEntity.comment
    }
}

class ExpencesDiffUtil : DiffUtil.ItemCallback<ExpencesEntity>() {
    override fun areItemsTheSame(oldItem: ExpencesEntity, newItem: ExpencesEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ExpencesEntity, newItem: ExpencesEntity): Boolean {
        return oldItem == newItem
    }


}