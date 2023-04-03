package com.example.turfnow.selection

import android.os.Build
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.turfnow.database.dao.AvailabilityDao
import com.example.turfnow.database.entity.Category
import com.example.turfnow.ui.availability_screen.DateAdapter
import com.example.turfnow.ui.availability_screen.TimeAdapter
import com.example.turfnow.ui.home.CategoryAdapter
import java.time.LocalDate

class MyKeyProvider(private val adapter: ListAdapter<out Any, out RecyclerView.ViewHolder>): ItemKeyProvider<String>(SCOPE_CACHED) {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun getKey(position: Int): String? = when (adapter.currentList[position]) {
                is Category -> (adapter.currentList[position] as Category).name
                is LocalDate ->(adapter.currentList[position] as LocalDate).toString()
                is AvailabilityDao.TimeSlot ->(adapter.currentList[position] as AvailabilityDao.TimeSlot).slot_time
                else -> null
        }
        @RequiresApi(Build.VERSION_CODES.O)
        override fun getPosition(key: String): Int =
                adapter.currentList.indexOfFirst {
                        when (it) {
                                is Category -> it.name == key
                                is LocalDate -> it.toString()==key
                                is AvailabilityDao.TimeSlot -> it.slot_time == key
                                else -> false
                        }
                }
}
class MyItemDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<String>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<String>? {
                val view = recyclerView.findChildViewUnder(event.x, event.y)
                if (view != null) {
                        return when (recyclerView.getChildViewHolder(view)) {
                                is CategoryAdapter.CategoryViewHolder -> (recyclerView.getChildViewHolder(view) as CategoryAdapter.CategoryViewHolder).getItemDetails()
                                is DateAdapter.DateViewHolder -> (recyclerView.getChildViewHolder(view) as DateAdapter.DateViewHolder).getItemDetails()
                                is TimeAdapter.TimeViewHolder -> (recyclerView.getChildViewHolder(view) as TimeAdapter.TimeViewHolder).getItemDetails()
                                else -> {
                                       null
                                }
                        }
                }
                return null
        }
}