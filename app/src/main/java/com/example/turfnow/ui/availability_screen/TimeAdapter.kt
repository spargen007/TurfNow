package com.example.turfnow.ui.availability_screen

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.turfnow.R
import com.example.turfnow.database.dao.AvailabilityDao
import com.example.turfnow.databinding.TimePickerLayoutBinding

class TimeAdapter (private val onItemClicked: (AvailabilityDao.TimeSlot) -> Unit) : ListAdapter<AvailabilityDao.TimeSlot, TimeAdapter.TimeViewHolder>(
    DiffCallback
){
    var tracker: SelectionTracker<String>? = null

    inner class TimeViewHolder(private var binding: TimePickerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(time: AvailabilityDao.TimeSlot, isActivated:Boolean=false) {
            binding.timeslot.text = time.slot_time
            binding.priceText.text = "₹ ${time.price}"
            if(isActivated) {
                binding.timeslot.setBackgroundResource(R.color.red)
            }
            else{
                binding.timeslot.setBackgroundResource(R.color.light_golden)
            }
            if(time.booked) {
                binding.timeslot.setTextColor(Color.parseColor("#EC1414"))
            }
            else {
                binding.timeslot.setTextColor(Color.parseColor("#0DEC4C"))
            }
        }
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<String>? {
            val timeSlot = getItem(adapterPosition)
            if (!timeSlot.booked) {
                return object : ItemDetailsLookup.ItemDetails<String>() {
                    override fun getPosition(): Int = adapterPosition
                    override fun getSelectionKey(): String = timeSlot.slot_time
                    override fun inSelectionHotspot(e: MotionEvent): Boolean {
                        return true
                    }
                }
            } else {
                return null
            }
        }
//            object : ItemDetailsLookup.ItemDetails<String>() {
//                override fun getPosition(): Int = adapterPosition
//                override fun getSelectionKey(): String = getItem(adapterPosition).slot_time
//                override fun inSelectionHotspot(e: MotionEvent): Boolean { return true }
//            }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val viewHolder =TimeViewHolder(
            TimePickerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val item = getItem(position)
        tracker?.let {
            holder.bind(getItem(position),it.isSelected(item.slot_time))
        } ?: holder.bind(getItem(position))
    }
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<AvailabilityDao.TimeSlot>() {
            override fun areItemsTheSame(oldItem: AvailabilityDao.TimeSlot, newItem: AvailabilityDao.TimeSlot): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: AvailabilityDao.TimeSlot, newItem: AvailabilityDao.TimeSlot): Boolean {
                return oldItem.slot_time == newItem.slot_time &&
                        oldItem.booked == newItem.booked
            }
        }
    }
}