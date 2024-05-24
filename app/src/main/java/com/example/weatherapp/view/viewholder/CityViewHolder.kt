package com.example.weatherapp.view.viewholder

import com.example.weatherapp.databinding.ItemViewCitiesBinding
import com.example.weatherapp.domain.model.city.CityVO
import com.example.weatherapp.view.delegate.CityDelegate

class CityViewHolder(private val binding : ItemViewCitiesBinding , private val delegate : CityDelegate) :
    BaseViewHolder<CityVO>(binding.root){
    override fun setData(mData: CityVO) {
        binding.tvName.text = mData.name
        binding.tvRegion.text = mData.region
        binding.tvCountry.text = mData.country
        binding.ivMap.setOnClickListener {
            delegate.onClickMap(mData.lat, mData.lon)
        }
    }
}