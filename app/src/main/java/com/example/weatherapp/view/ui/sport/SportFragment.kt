package com.example.weatherapp.view.ui.sport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSportBinding
import com.example.weatherapp.domain.model.FlowReturnResult
import com.example.weatherapp.domain.model.sport.SportItemVO
import com.example.weatherapp.domain.model.sport.SportVO
import com.example.weatherapp.hideKeyboard
import com.example.weatherapp.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import fr.haan.resultat.Resultat

@AndroidEntryPoint
class SportFragment : BaseFragment<SportViewModel>() {
    override val viewModel: SportViewModel by viewModels()

    private lateinit var binding : FragmentSportBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        listenToLiveData()
        //viewModel.getSport("Liverpool")
    }

    private fun initUI(){
        binding.btnSearch.setOnClickListener{
            hideKeyboard()
            val city = binding.edtCity.text.toString()
            if (city.isNotEmpty()) {
                viewModel.getSport(city)
            }else{
                binding.edtCity.error = "Please enter city name"
            }
        }
    }

    private fun listenToLiveData(){
        viewModel.sportFlow.observe(viewLifecycleOwner){
            when(it){
                is FlowReturnResult.LoadingRelsult -> {
                    showLoadingDialog()
                }
                is FlowReturnResult.PositiveResult -> {
                    hideLoadingDialog()
                    bindData(it.data)
                }
                is FlowReturnResult.ErrorResult -> {
                    hideLoadingDialog()
                    Toast.makeText(requireContext(), it.errorMsg.toString(), Toast.LENGTH_SHORT).show()
                }
                else ->{
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun bindData(data: SportVO){
        binding.tvFootball.visibility = View.VISIBLE
        binding.tvCricket.visibility = View.VISIBLE
        binding.tvGolf.visibility = View.VISIBLE
        if (data.footBall.isEmpty()){
            binding.rlFootballEmpty.visibility = View.VISIBLE
            binding.tblFootball.visibility = View.GONE
            clearTableData(binding.tblFootball)
        }else{
            binding.rlFootballEmpty.visibility = View.GONE
            binding.tblFootball.visibility = View.VISIBLE
            clearTableData(binding.tblFootball)
            for (i in data.footBall){
                addDataRow(i, binding.tblFootball)
            }
        }

        if (data.cricket.isEmpty()){
            binding.rlCricketEmpty.visibility = View.VISIBLE
            binding.tblCricket.visibility = View.GONE
            clearTableData(binding.tblCricket)
        }else{
            binding.rlCricketEmpty.visibility = View.GONE
            binding.tblCricket.visibility = View.VISIBLE
            clearTableData(binding.tblCricket)
            for (i in data.cricket){
                addDataRow(i, binding.tblCricket)
            }
        }

        if (data.golf.isEmpty()){
            binding.rlGolfEmpty.visibility = View.VISIBLE
            binding.tblGolf.visibility = View.GONE
            clearTableData(binding.tblGolf)
        }else{
            binding.rlGolfEmpty.visibility = View.GONE
            binding.tblGolf.visibility = View.VISIBLE
            clearTableData(binding.tblGolf)
            for (i in data.golf){
                addDataRow(i, binding.tblGolf)
            }
        }
    }

    // Function to clear existing rows except the header
    fun clearTableData(view : TableLayout) {
        val childCount = view.childCount
        // Start from 1 to keep the header row
        if (childCount > 1) {
            view.removeViews(1, childCount - 1)
        }
    }

    // Function to add new data rows
    fun addDataRow(data : SportItemVO, view : TableLayout) {
        val tableRow = TableRow(requireContext())
        tableRow.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primaryTransparent))
        tableRow.weightSum = 6f
        tableRow.setPadding(10, 10, 10, 10)

        val layoutParams = TableRow.LayoutParams(
            0,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        layoutParams.weight = 1f
        val layoutParams2 = TableRow.LayoutParams(
            0,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        layoutParams2.weight = 2f

        val matchTextView = TextView(requireContext())
        matchTextView.text = data.match
        matchTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        matchTextView.setTextAppearance(R.style.MediumXS)
        matchTextView.layoutParams = layoutParams2

        val stadiumTextView = TextView(requireContext())
        stadiumTextView.text = data.stadium
        stadiumTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        stadiumTextView.setTextAppearance(R.style.MediumXS)
        stadiumTextView.layoutParams = layoutParams

        val countryTextView = TextView(requireContext())
        countryTextView.text = data.country
        countryTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        countryTextView.setTextAppearance(R.style.MediumXS)
        countryTextView.layoutParams = layoutParams

        val tourNameTextView = TextView(requireContext())
        tourNameTextView.text = data.tourName
        tourNameTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        tourNameTextView.setTextAppearance(R.style.MediumXS)
        tourNameTextView.layoutParams = layoutParams

        val dateTextView = TextView(requireContext())
        dateTextView.text = data.date
        dateTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        dateTextView.setTextAppearance(R.style.MediumXS)
        dateTextView.layoutParams = layoutParams

        tableRow.addView(matchTextView)
        tableRow.addView(stadiumTextView)
        tableRow.addView(countryTextView)
        tableRow.addView(tourNameTextView)
        tableRow.addView(dateTextView)

        view.addView(tableRow)
    }


}