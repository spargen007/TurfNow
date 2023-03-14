package com.example.turfnow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turfnow.BottomNavigationActivity
import com.example.turfnow.database.AppDatabase
import com.example.turfnow.databinding.FragmentHomeBinding
import com.example.turfnow.repository.CategoryRepositoryImpl
import com.example.turfnow.repository.TurfRepositoryImpl

class HomeFragment : Fragment() {
    private val args: HomeFragmentArgs by navArgs()
    private lateinit var turfRecyclerview: RecyclerView
    private lateinit var categoryRecyclerview: RecyclerView
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            TurfRepositoryImpl(AppDatabase.getDatabase(requireContext()).turfDao()),
            CategoryRepositoryImpl(AppDatabase.getDatabase(requireContext()).categoryDao())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainActivity = requireActivity() as BottomNavigationActivity
        mainActivity.setBottomNavigationViewVisibility(true)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        turfRecyclerview = binding.turfRecyclerView
        val turfAdapter = TurfAdapter{
            Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
        }
        turfRecyclerview.adapter=turfAdapter
        turfRecyclerview.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        turfRecyclerview.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        homeViewModel.turfList.observe(viewLifecycleOwner){
            turfAdapter.submitList(it)
        }
        categoryRecyclerview = binding.categoriesRecyclerView
        categoryRecyclerview.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        val categoryAdapter = CategoryAdapter(requireContext()) {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        }
        categoryRecyclerview.adapter = categoryAdapter
        categoryRecyclerview.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        homeViewModel.categoryList.observe(viewLifecycleOwner){
            categoryAdapter.submitList(it)
        }

        binding.textUserName.text = args.user.name
        binding.emailIdText.text = args.user.email_id

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}