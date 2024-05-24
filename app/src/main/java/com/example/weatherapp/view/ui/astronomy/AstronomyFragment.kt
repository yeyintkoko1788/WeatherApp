package com.example.weatherapp.view.ui.astronomy

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.domain.model.FlowReturnResult
import com.example.weatherapp.domain.model.astronomy.AstronomyVO
import com.example.weatherapp.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.min

@AndroidEntryPoint
class AstronomyFragment : BaseFragment<WeatherViewModel>() {
    override val viewModel: WeatherViewModel by viewModels()

    private lateinit var binding : FragmentHomeBinding

    private var currentSelectedDate : String = ""
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        listenToLiveData()
    }

    private fun listenToLiveData(){
        viewModel.astronomyFlow.observe(viewLifecycleOwner){
            when(it){
                is FlowReturnResult.LoadingRelsult -> {
                    showLoadingDialog()
                }
                is FlowReturnResult.PositiveResult -> {
                    hideLoadingDialog()
                    binding.dataRoot.visibility = View.VISIBLE
                    bindData(it.data)
                }
                is FlowReturnResult.ErrorResult -> {
                    hideLoadingDialog()
                    binding.dataRoot.visibility = View.GONE
                    Toast.makeText(requireContext(), it.errorMsg.toString(), Toast.LENGTH_SHORT).show()
                }
                else ->{
                    hideLoadingDialog()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(data: AstronomyVO){
        binding.tvCityInfo.text = data.name
        binding.tvLocation.text = data.region+", "+data.country
        binding.tvDistance.text = data.distance
        binding.tvLocalTime.text = data.localTime
        binding.tvSunRise.text = data.sunrise
        binding.tvSunSet.text = data.sunset
        binding.tvMoonRise.text = data.moonrise
        binding.tvMoonSet.text = data.moonset
        binding.tvRise.text = data.timeDffSunriseAndMoonrise
        binding.tvSet.text = data.timeDffSunsetAndMoonset
    }

    private fun initUI(){
        //viewModel.getAstronomy("Yangon", "2023-07-20")
        binding.edtCity.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                binding.rlCity.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_focus_search)
            else
                binding.rlCity.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_not_focus_search)
        }
        currentSelectedDate = dateFormat.format(calendar.time)
        binding.tvDate.text = currentSelectedDate
        binding.rlCalendar.setOnClickListener {
            showDatePicker()
        }

        binding.btnSearch.setOnClickListener{
            val city = binding.edtCity.text.toString()
            if (city.isNotEmpty()) {
                viewModel.getAstronomy(city, currentSelectedDate)
            }else{
                binding.edtCity.error = "Please enter city name"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePicker() {
        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            requireContext(), {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Create a new Calendar instance to hold the selected date
                val selectedDate = Calendar.getInstance()
                // Set the selected date using the values received from the DatePicker dialog
                selectedDate.set(year, monthOfYear, dayOfMonth)
                // Format the selected date into a string
                val formattedDate = dateFormat.format(selectedDate.time)
                // Update the TextView to display the selected date with the "Selected Date: " prefix
                currentSelectedDate = formattedDate.toString()
                binding.tvDate.text = currentSelectedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        calendar.set(2015, Calendar.JANUARY, 1)
        val minDate = calendar.timeInMillis
        datePickerDialog.datePicker.minDate = minDate
        datePickerDialog.show()
    }
}