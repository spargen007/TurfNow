package com.example.turfnow.ui.singleturf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.turfnow.database.entity.GroundType
import com.example.turfnow.databinding.SingleTurfLayoutBinding
import com.example.turfnow.dependency.MyApplication
import com.example.turfnow.selection.MyItemDetailsLookup
import com.example.turfnow.selection.MyKeyProvider
import com.example.turfnow.ui.home.CategoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SingleTurfFragment:Fragment() {
    private var selectionTracker: SelectionTracker<String>? = null
    private val args: SingleTurfFragmentArgs by navArgs()
    private lateinit var categoryRecyclerview: RecyclerView
    private var _binding: SingleTurfLayoutBinding? = null
    private val binding get() = _binding!!
    private val singleTurfViewModel: SingleTurfViewModel by viewModels {
        SingleTurfViewModelFactory(MyApplication(requireContext()).appContainer)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SingleTurfLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var groundtype:GroundType? = null
        binding.turfNameText.text = args.turf.name
        binding.turfAddressText.text = args.turf.location
        binding.ratingText.text = args.turf.ratings
        Glide.with(requireContext()).load(args.turf.image).into(binding.turfImage)
        singleTurfViewModel.getGroundtypeofTurf(args.turf.id.toString())
        categoryRecyclerview = binding.categoriesRecyclerView
        categoryRecyclerview.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        val categoryAdapter = CategoryAdapter(requireContext()) {

        }
        categoryRecyclerview.adapter = categoryAdapter
        selectionTracker = SelectionTracker.Builder(
            "mySelection",
            categoryRecyclerview,
            MyKeyProvider(categoryAdapter),
            MyItemDetailsLookup(categoryRecyclerview),
            StorageStrategy.createStringStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything()
        ).build()
       selectionTracker?.addObserver(
            object : SelectionTracker.SelectionObserver<String>() {
                override fun onSelectionRestored() {
                    super.onSelectionRestored()
                    onSelectionChanged()
                }
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    val items =selectionTracker?.selection!!.size()
                    if(items>0){
                        var capacity = "0"
                        val job=singleTurfViewModel.viewModelScope.launch(Dispatchers.IO){
                            groundtype = singleTurfViewModel.getGroundType(args.turf.id.toString(),
                                selectionTracker!!.selection.first())
                            capacity = groundtype!!.capacity
                        }
                        singleTurfViewModel.viewModelScope.launch(Dispatchers.Main) {
                            job.join()
                            if (capacity != "0") {
                                binding.capacityText.isVisible = true
                                binding.capacityText.text = capacity
                                binding.bookNowText.isVisible = false
                                binding.bookNowBtn.isVisible = true
                            }
                        }
                    }
                    else{
                        binding.capacityText.isVisible = false
                        binding.bookNowText.isVisible = true
                        binding.bookNowBtn.isVisible = false
                    }
                }
            })
        categoryAdapter.tracker = selectionTracker
        categoryRecyclerview.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        singleTurfViewModel.categoryList.observe(viewLifecycleOwner){
            categoryAdapter.submitList(it)
        }
        binding.bookNowBtn.setOnClickListener {
            if(groundtype != null) {
                val action: NavDirections =
                    SingleTurfFragmentDirections.actionSingleTurfFragmentToAvailabilityScreenFragment(groundtype!!.id,args.user)
                findNavController().navigate(action)
            }
        }

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
       selectionTracker?.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        selectionTracker?.onRestoreInstanceState(savedInstanceState)
    }
}