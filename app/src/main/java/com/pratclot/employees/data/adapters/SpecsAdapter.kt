package com.pratclot.employees.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pratclot.employees.databinding.ListItemBinding
import com.pratclot.employees.domain.DataItem

class SpecsAdapter(val listener: ClickListener) :
    ListAdapter<DataItem, SpecsAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, listener)
    }

    class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem, listener: ClickListener) {
            binding.item = item
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ClickListener(val listener: (item: DataItem) -> Unit) {
    fun onClick(item: DataItem) = listener(item)
}

class DiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return when (oldItem) {
            is DataItem.Specialty -> oldItem.specialty_id == (newItem as DataItem.Specialty).specialty_id
            is DataItem.Employee -> oldItem.run { f_name + l_name + birthday } == (newItem as DataItem.Employee).run { f_name + l_name + birthday }
        }
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}