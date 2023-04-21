package com.example.turfnow.ui.booking_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turfnow.databinding.FragmentBookingHistoryBinding
import com.example.turfnow.dependency.MyApplication
import com.google.android.material.snackbar.Snackbar

class BookingHistoryFragment : Fragment() {

    private var _binding: FragmentBookingHistoryBinding? = null

    private val binding get() = _binding!!

    private val bookingHistoryViewModel: BookingHistoryViewModel by viewModels {
        BookingHistoryViewModelFactory(MyApplication(requireContext()).appContainer)
    }

    private lateinit var bookingsRecyclerview: RecyclerView

    private lateinit var bookingAdapter: BookingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loadingBar.isVisible = true
        binding.bookingHistoryRecyclerView.isVisible = false
        var snackbar:Snackbar?=null
        val userId :Long?= requireActivity().intent.extras?.getLong("userid")
        bookingHistoryViewModel.getBookingHistoryList(userId!!)
        bookingsRecyclerview = binding.bookingHistoryRecyclerView
        bookingsRecyclerview.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        bookingAdapter = BookingAdapter {  }
            bookingHistoryViewModel.bookingsList.observe(viewLifecycleOwner){
                bookingAdapter.submitList(it)
            }
        bookingHistoryViewModel.snackbarMessage.observe(viewLifecycleOwner) { message ->
            binding.noBookingsText.isVisible = bookingAdapter.itemCount <= 0
            if(message!=null){
                binding.loadingBar.isVisible = true
                binding.bookingHistoryRecyclerView.isVisible =false
                snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
                snackbar?.show()
            }
            else{
                snackbar?.dismiss()
                binding.loadingBar.isVisible = false
                binding.bookingHistoryRecyclerView.isVisible = true
            }

        }

        bookingsRecyclerview.adapter = bookingAdapter
        bookingsRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}