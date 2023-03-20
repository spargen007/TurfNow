package com.example.turfnow.ui.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turfnow.databinding.FragmentFaqBinding
import com.example.turfnow.dependency.MyApplication
import kotlinx.coroutines.launch


class FaqFragment : Fragment() {
    private val faqViewModel: FaqViewModel by viewModels{
        FaqViewModelFactory(MyApplication(requireContext()).appContainer)
    }
    private lateinit var turfRecyclerview: RecyclerView

    private lateinit var faqAdapter:FaqAdapter

    private var _binding: FragmentFaqBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentFaqBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar1)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        faqViewModel.refresh()
        turfRecyclerview = binding.recycler
        faqAdapter = FaqAdapter()
        turfRecyclerview.adapter = faqAdapter
        turfRecyclerview.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )
        turfRecyclerview.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        faqViewModel.viewModelScope.launch {
            faqViewModel.faqList.observe(viewLifecycleOwner){
                if(it.isSuccessful){
                    binding.loadingBar.isVisible = false
                    binding.loadingText.isVisible = false
                    faqAdapter.submitList(it.body())
                }
                else{
                    binding.loadingBar.isVisible = true
                    binding.loadingText.isVisible = true
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}