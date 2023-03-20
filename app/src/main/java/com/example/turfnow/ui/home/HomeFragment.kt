package com.example.turfnow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turfnow.R
import com.example.turfnow.database.entity.User
import com.example.turfnow.databinding.FragmentHomeBinding
import com.example.turfnow.dependency.MyApplication
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private  var user:User?=null
    private lateinit var turfRecyclerview: RecyclerView
    private lateinit var categoryRecyclerview: RecyclerView
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by navGraphViewModels(R.id.homemenu){
        HomeViewModelFactory(MyApplication(requireContext()).appContainer)
    }
    private var locationList = mutableListOf<String>()
    private lateinit var turfAdapter:TurfAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        turfRecyclerview = binding.turfRecyclerView
        turfAdapter = TurfAdapter(context = requireContext()){
            Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
            val action:NavDirections = HomeFragmentDirections.actionHomeFragmentToSingleTurfFragment(it)
            findNavController().navigate(action)
        }
        turfRecyclerview.adapter=turfAdapter
        turfRecyclerview.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        turfRecyclerview.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        homeViewModel.turfList.observe(viewLifecycleOwner){
            turfAdapter.submitList(it)
        }
        categoryRecyclerview = binding.categoriesRecyclerView
        categoryRecyclerview.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        val categoryAdapter = CategoryAdapter(requireContext()){category ->
            homeViewModel.viewModelScope.launch {
                val action: NavDirections =
                    HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(homeViewModel.getTurfForCategory(category).toTypedArray())
                findNavController().navigate(action)
            }
        }
        categoryRecyclerview.adapter = categoryAdapter
        categoryRecyclerview.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        homeViewModel.categoryList.observe(viewLifecycleOwner){
            categoryAdapter.submitList(it)
        }
        val userId :String?= requireActivity().intent.extras?.getString("user")
        if(userId!=null){
            homeViewModel.viewModelScope.launch {
                user = homeViewModel.getUser(userId)
                binding.textUserName.text = user?.name
                binding.emailIdText.text = user?.email_id
            }
        }
        binding.searchBarTextView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               homeViewModel.viewModelScope.launch {
                   if (query != null) {
                     val turflist =  homeViewModel.getSearchResult(query)
                       val action:NavDirections = HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(turflist.toTypedArray())
                       findNavController().navigate(action)
                   }
               }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        homeViewModel.selectedLocation.observe(viewLifecycleOwner) { location ->
            binding.locationTextView.text = location
        }
        binding.locationTextView.setOnClickListener {
            showLocationPopup(binding.locationTextView)
        }
        binding.locationIcon.setOnClickListener {
            showLocationPopup(binding.locationTextView)
        }
        homeViewModel.locationList.observe(viewLifecycleOwner){
            locationList = it.toMutableList()
        }

    }
    private fun showLocationPopup(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.location, popupMenu.menu)
        popupMenu.menu.add(0,0,0,resources.getString(R.string.all_location))
        for (i in locationList.indices) {
            popupMenu.menu?.add(0, i+1, i+1, locationList[i])
        }
        popupMenu.setOnMenuItemClickListener { menuItem ->
            homeViewModel.setSelectedLocation(menuItem.title.toString())
            turfFilterOfLocation()
            true
        }
        popupMenu.show()
    }

    private fun turfFilterOfLocation(){
            homeViewModel.viewModelScope.launch {
                if(homeViewModel.selectedLocation.value!=null && homeViewModel.selectedLocation.value!=resources.getString(R.string.all_location)) {
                    homeViewModel.updateTurfList(homeViewModel.getLocationResult(homeViewModel.selectedLocation.value!!))
                }
                else{
                    homeViewModel.getAllTurfAndCategoryAndLocationList()
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}