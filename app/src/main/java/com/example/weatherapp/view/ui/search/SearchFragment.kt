package com.example.weatherapp.view.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.domain.model.FlowReturnResult
import com.example.weatherapp.view.BaseFragment
import com.example.weatherapp.view.adapter.CityAdapter
import com.example.weatherapp.view.delegate.CityDelegate
import com.example.weatherapp.view.ui.home.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<WeatherViewModel>(), CityDelegate {
    override val viewModel: WeatherViewModel by viewModels()

    private lateinit var binding: FragmentSearchBinding

    private var adapter : CityAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.searchFlow.observe(viewLifecycleOwner){
            when(it){
                is FlowReturnResult.LoadingRelsult -> {
                    showLoadingDialog()
                }
                is FlowReturnResult.PositiveResult -> {
                    hideLoadingDialog()
                    binding.tipsRoot.visibility = View.GONE
                    binding.itemsRoot.visibility = View.VISIBLE
                    if (it.data.isEmpty()){
                        binding.animView.visibility = View.VISIBLE
                        binding.rvCities.visibility = View.GONE
                        adapter?.clearData()
                    }else{
                        binding.animView.visibility = View.GONE
                        binding.rvCities.visibility = View.VISIBLE
                        adapter?.setNewDataList(it.data)
                    }
                }
                is FlowReturnResult.ErrorResult -> {
                    hideLoadingDialog()
                    binding.tipsRoot.visibility = View.VISIBLE
                    binding.itemsRoot.visibility = View.GONE
                    Toast.makeText(requireContext(), it.errorMsg.toString(), Toast.LENGTH_SHORT).show()
                }
                else ->{
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun initUI() {
        binding.searchView.requestFocus()
        binding.searchView.setOnSearchClickListener{
            binding.tvSearchTitle.visibility = View.GONE
        }

        binding.searchView.setOnCloseListener {
            binding.tvSearchTitle.visibility = View.VISIBLE
            false
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)
                    viewModel.getSearchResult(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        adapter = CityAdapter(requireContext(), this)
        binding.rvCities.adapter = adapter
        binding.rvCities.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onClickMap(lat: Double, lng: Double) {
        val url = "https://www.google.com/maps/search/?api=1&query=$lat,$lng"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}