package com.example.budgettracker.ui.balance

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettracker.data.accounts.AccountEntity
import com.example.budgettracker.databinding.ItemBinding

class AccountAdapter(private val onClick: (dataModel: AccountEntity) -> Unit) :
    ListAdapter<AccountEntity, AccountViewHolder>(
        ExpencesDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        return AccountViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }
}

class AccountViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(dataEntity: AccountEntity) {
        binding.tvAccountName.text = dataEntity.accountName
        binding.tvBalanceTotal.text = dataEntity.sum.toString()
    }
}

class ExpencesDiffUtil : DiffUtil.ItemCallback<AccountEntity>() {
    override fun areItemsTheSame(oldItem: AccountEntity, newItem: AccountEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AccountEntity, newItem: AccountEntity): Boolean {
        return oldItem == newItem
    }


}