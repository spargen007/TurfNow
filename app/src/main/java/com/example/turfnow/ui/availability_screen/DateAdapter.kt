package com.example.turfnow.ui.availability_screen

import android.os.Build
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.turfnow.databinding.DatePickerLayoutBinding
import java.time.LocalDate

class DateAdapter(private val onItemClicked: (LocalDate) -> Unit,private val startDate: LocalDate) : ListAdapter<LocalDate, DateAdapter.DateViewHolder>(
    DiffCallback
){
    var tracker: SelectionTracker<String>? = null

    inner class DateViewHolder(private var binding: DatePickerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(date: LocalDate, isActivated:Boolean=false) {
            binding.dateNameText.text = date.dayOfMonth.toString()
            binding.dayNameText.text = date.dayOfWeek.name.take(3)
            binding.monthNameText.text = date.month.name.take(3)
            binding.checkmark.isVisible = isActivated

        }
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<String> =
            object : ItemDetailsLookup.ItemDetails<String>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): String = getItem(adapterPosition).toString()
                override fun inSelectionHotspot(e: MotionEvent): Boolean { return true }
            }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val viewHolder =DateViewHolder(
            DatePickerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val item = getItem(position)
        tracker?.let {
            holder.bind(item,it.isSelected(item.toString()))
        } ?: holder.bind(item)
    }
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<LocalDate>() {
            override fun areItemsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
               return oldItem == newItem
            }
        }
    }
}