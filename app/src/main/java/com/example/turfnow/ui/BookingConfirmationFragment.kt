package com.example.turfnow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.turfnow.databinding.BookingConfirmationBinding

class BookingConfirmationFragment:Fragment() {
    private var _binding: BookingConfirmationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BookingConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.doneBtn.setOnClickListener {
            val action:NavDirections = BookingConfirmationFragmentDirections.actionBookingConfirmationFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }
}