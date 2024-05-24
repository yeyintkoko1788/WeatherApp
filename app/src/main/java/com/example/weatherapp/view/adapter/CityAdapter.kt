package com.example.weatherapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.weatherapp.databinding.ItemViewCitiesBinding
import com.example.weatherapp.domain.model.city.CityVO
import com.example.weatherapp.view.delegate.CityDelegate
import com.example.weatherapp.view.viewholder.CityViewHolder

class CityAdapter(private val context : Context, private val delegate : CityDelegate) :
    BaseRecyclerAdapter<CityVO , CityViewHolder>(context)  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemViewCitiesBinding.inflate(LayoutInflater.from(context), parent, false)
        return CityViewHolder(binding, delegate)
    }
}