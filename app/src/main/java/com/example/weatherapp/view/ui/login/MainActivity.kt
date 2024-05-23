package com.example.weatherapp.view.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.domain.model.FlowReturnResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCrash.setOnClickListener{
            loadData()
        }

        viewModel.liveEventFlow.observe(this) {
            when (it) {
                is FlowReturnResult.LoadingRelsult -> {}
                is FlowReturnResult.PositiveResult -> {
                    Toast.makeText(this, it.data.name, Toast.LENGTH_SHORT).show()
                }
                is FlowReturnResult.ErrorResult -> {
                    Toast.makeText(this, it.errorMsg.toString(), Toast.LENGTH_SHORT).show()
                }
                else ->{

                }
            }
        }
    }

    private fun loadData(){
        viewModel.getWeather()
    }
}