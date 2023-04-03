package com.example.turfnow.ui.availability_screen

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.turfnow.database.dao.AvailabilityDao
import com.example.turfnow.database.entity.Bookings
import com.example.turfnow.databinding.FragmentAvailabilityBinding
import com.example.turfnow.dependency.MyApplication
import com.example.turfnow.selection.MyItemDetailsLookup
import com.example.turfnow.selection.MyKeyProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
@RequiresApi(Build.VERSION_CODES.O)
class AvailabilityScreenFragment :Fragment() {
    val inputDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    private var dateselectionTracker: SelectionTracker<String>? = null
    private var timeselectionTracker: SelectionTracker<String>? = null
    private val args: AvailabilityScreenFragmentArgs by navArgs()
    private var _binding: FragmentAvailabilityBinding? = null
    private val binding get() = _binding!!
    private lateinit var dateRecyclerview: RecyclerView
    private lateinit var timeRecyclerview: RecyclerView
    private val availabilityViewModel: AvailabilityScreenViewModel by viewModels {
        AvailabilityScreenViewModelFactory(MyApplication(requireContext()).appContainer)
    }
    private lateinit var dateAdapter: DateAdapter
    private lateinit var timeAdapter: TimeAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAvailabilityBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var totalPrice:Double = 0.0
        var availableTimeSlotList:List<AvailabilityDao.TimeSlot>? = null
        val selectedground:Long = args.groundTypeId
        var selectedDate:String? = null
        var seletedTimeSlots:List<String>? = null
        binding.nextButton.isVisible = false
        dateRecyclerview = binding.dateRecyclerView
        val currentDate = LocalDate.now()
        val endDate = currentDate.plusDays(30)
        val dateList = mutableListOf<LocalDate>()
        var date = currentDate
        while (date <= endDate) {
            dateList.add(date)
            date = date.plusDays(1)
        }
        dateAdapter = DateAdapter({  }, currentDate)
        dateAdapter.submitList(dateList)
        dateRecyclerview.adapter = dateAdapter
        dateRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        timeRecyclerview = binding.timeRecyclerView
        timeRecyclerview.layoutManager = GridLayoutManager(requireContext(),3)
        timeAdapter = TimeAdapter {
        }
        timeRecyclerview.adapter = timeAdapter
        dateselectionTracker = SelectionTracker.Builder(
            "dateSelection",
            dateRecyclerview,
            MyKeyProvider(dateAdapter),
            MyItemDetailsLookup(dateRecyclerview),
            StorageStrategy.createStringStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything()
        ).build()

        dateselectionTracker?.addObserver(
            object : SelectionTracker.SelectionObserver<String>() {
                override fun onSelectionRestored() {
                    super.onSelectionRestored()
                    onSelectionChanged()
                }
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    timeselectionTracker?.clearSelection()
                    val items =dateselectionTracker?.selection!!
                    if(items.size()>0){
                        binding.timeRecyclerView.isVisible = true
                        binding.noDateWarnMsg.isVisible = false
                        availabilityViewModel.viewModelScope.launch(Dispatchers.IO){
                            availabilityViewModel.getallAvailableTimeSlot(args.groundTypeId,
                                with(items.first()){
                                    val inputdate = LocalDate.parse(this, inputDateFormat)
                                    val formattedDate = inputdate.format(outputDateFormat)
                                    selectedDate=formattedDate
                                    formattedDate
                                }).also{
                                if(it.isNotEmpty()) {
                                    availableTimeSlotList = it
                                    withContext(Dispatchers.Main){
                                    binding.notimeWarnMsg.isVisible = false
                                    binding.timeRecyclerView.isVisible = true}
                                    if(items.first()==currentDate.toString()) {
                                            timeAdapter.submitList(it.filter {time->
                                                val currentTime = LocalTime.now()
                                                val slotStart =
                                                    LocalTime.parse(time.slot_time.split("-")[0])
                                                slotStart.isAfter(currentTime)
                                            })
                                        }
                                    else{
                                        timeAdapter.submitList(it)
                                    }
                                }
                                else{
                                    withContext(Dispatchers.Main){
                                    binding.timeRecyclerView.isVisible = false
                                    binding.notimeWarnMsg.isVisible = true}
                                }
                            }
                        }
                    }
                    else{
                        binding.notimeWarnMsg.isVisible = false
                        binding.timeRecyclerView.isVisible = false
                        binding.noDateWarnMsg.isVisible = true
                    }
                }
            })
        dateAdapter.tracker = dateselectionTracker
        dateRecyclerview.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        timeselectionTracker = SelectionTracker.Builder(
            "timeSelection",
            timeRecyclerview,
            MyKeyProvider(timeAdapter),
            MyItemDetailsLookup(timeRecyclerview),
            StorageStrategy.createStringStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        timeselectionTracker?.addObserver(
            object : SelectionTracker.SelectionObserver<String>() {
                override fun onSelectionRestored() {
                    super.onSelectionRestored()
                    onSelectionChanged()
                }

                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    val items = timeselectionTracker?.selection!!
                    binding.nextButton.isVisible = items.size()>0
                    if(items.size()>0){
                        availabilityViewModel.viewModelScope.launch(Dispatchers.IO){
                            totalPrice = selectedDate?.let {
                                availabilityViewModel.getallPrice(selectedground,
                                    it,items.toList())
                            }?.reduce { acc, d -> acc + d }!!
                            withContext(Dispatchers.Main) {
                                if (totalPrice != 0.0) {
                                    binding.priceText.text = "Total Price:${totalPrice}"
                                } else {
                                    binding.priceText.text = "Total Price:0"
                                }
                            }
                            seletedTimeSlots = items.toList()
                        }
                    }
                    else{
                        binding.priceText.text = "Total Price:0"
                    }
                }


        })
        timeAdapter.tracker = timeselectionTracker
        binding.nextButton.setOnClickListener {
            availabilityViewModel.viewModelScope.launch (Dispatchers.IO) {
                val availabilityList = availabilityViewModel.checkAvailability(selectedground, selectedDate!!,seletedTimeSlots!!)
              if (availabilityList.all { !it.booked }){
                  availabilityViewModel.markSlotAsBooked(selectedground, selectedDate!!,seletedTimeSlots!!)
                  availabilityViewModel.insertBookingWithSlots(Bookings(0,selectedDate!!,args.user.id,selectedground,totalPrice),availableTimeSlotList!!.filter {
                      seletedTimeSlots!!.contains(it.slot_time)
                  })
                  withContext(Dispatchers.Main){
                  val action:NavDirections = AvailabilityScreenFragmentDirections.actionAvailabilityScreenFragmentToBookingConfirmationFragment()
                  findNavController().navigate(action)}

              }
              else{
                  val bookedErrorList:MutableList<String> = mutableListOf()
                  availabilityList.forEach{
                      if(it.booked){
                          bookedErrorList.add(it.slot_time)
                      }
                  }
                  withContext(Dispatchers.Main){
                  Toast.makeText(requireContext(),"${bookedErrorList}slots are unavailable",Toast.LENGTH_SHORT).show()
                  dateselectionTracker?.clearSelection()
                  timeselectionTracker?.clearSelection()}
              }


            }

        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        dateselectionTracker?.onSaveInstanceState(outState)
        timeselectionTracker?.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        dateselectionTracker?.onRestoreInstanceState(savedInstanceState)
        timeselectionTracker?.onRestoreInstanceState(savedInstanceState)
    }
}