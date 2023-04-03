package com.example.turfnow.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turfnow.databinding.FragmentSearchResultsLayoutBinding
import com.example.turfnow.ui.home.TurfAdapter


class SearchResultFragment:Fragment() {
    val args:SearchResultFragmentArgs by navArgs()
    private lateinit var turfRecyclerview: RecyclerView
    private var _binding: FragmentSearchResultsLayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultsLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        turfRecyclerview = binding.specificTurfRecyclerview
        val adapter = TurfAdapter(requireContext()){
            val action:NavDirections = SearchResultFragmentDirections.actionSearchResultFragmentToSingleTurfFragment(it,args.user)
            findNavController().navigate(action)
        }
        adapter.submitList(args.turflist.toMutableList())
        binding.noResultText.isVisible = args.turflist.toMutableList().size <= 0
        turfRecyclerview.adapter = adapter
        turfRecyclerview.layoutManager= LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)
        turfRecyclerview.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.resultText.text = "Result for '${args.result}'"
    }
}