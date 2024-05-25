package com.example.weatherapp.view.viewholder

import android.annotation.SuppressLint
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemViewCitiesBinding
import com.example.weatherapp.domain.model.city.CityVO
import com.example.weatherapp.show
import com.example.weatherapp.view.delegate.CityDelegate

class CityViewHolder(private val binding : ItemViewCitiesBinding , private val delegate : CityDelegate) :
    BaseViewHolder<CityVO>(binding.root){
    @SuppressLint("SetTextI18n")
    override fun setData(mData: CityVO) {
        binding.tvName.text = mData.name
        binding.tvLocation.text = mData.region+", "+mData.country
        binding.root.setOnClickListener {
            delegate.onClickMap(mData.lat, mData.lon)
        }
        binding.ivCity.show("https://source.unsplash.com/1600x900/?${mData.name}", R.drawable.city_placeholder)
    }
}