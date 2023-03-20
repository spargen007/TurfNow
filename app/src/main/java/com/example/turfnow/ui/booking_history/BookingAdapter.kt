package com.example.turfnow.ui.booking_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.turfnow.database.dao.BookingDao
import com.example.turfnow.databinding.BookingsLayoutBinding

class BookingAdapter(private val onItemClicked: () -> Unit) : ListAdapter<BookingDao.BookingswithTurf, BookingAdapter.BookingViewHolder>(DiffCallback)
{

    inner class BookingViewHolder(private var binding: BookingsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookingModel: BookingDao.BookingswithTurf) {
            binding.bookingNumberText.text = bookingModel.bookingModel.booking.id.toString()
            binding.date.text = bookingModel.bookingModel.booking.booked_date
            binding.totalPrice.text = bookingModel.bookingModel.booking.total_price.toString()
            binding.slotList.text = bookingModel.bookingModel.bookingSlots.map { it.slotTime }.toString()
            binding.turfName.text = bookingModel.turf.name
            binding.categoryName.text = bookingModel.category.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        //        viewHolder.itemView.setOnClickListener {
//            val position = viewHolder.adapterPosition
//            onItemClicked(getItem(position))
//        }
        return BookingViewHolder(
            BookingsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<BookingDao.BookingswithTurf>() {
            override fun areItemsTheSame(oldItem: BookingDao.BookingswithTurf, newItem: BookingDao.BookingswithTurf): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BookingDao.BookingswithTurf, newItem: BookingDao.BookingswithTurf): Boolean {
                return oldItem.bookingModel.booking == newItem.bookingModel.booking &&
                        oldItem.bookingModel.bookingSlots == newItem.bookingModel.bookingSlots
            }
        }
    }
}