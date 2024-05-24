package com.example.weatherapp.view.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.domain.model.FlowReturnResult
import com.example.weatherapp.view.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    lateinit var binding : ActivityMainBinding

    override val viewModel: MainViewModel by viewModels()
    lateinit var bottomNavView : BottomNavigationView

    val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navHostFragment.navController
    }

    companion object{
        fun newIntent(context : Context) : Intent {
            return Intent(context , MainActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        viewModel.liveEventFlow.observe(this) {
//            when (it) {
//                is FlowReturnResult.LoadingRelsult -> {}
//                is FlowReturnResult.PositiveResult -> {
//                    Toast.makeText(this, it.data.name, Toast.LENGTH_SHORT).show()
//                }
//                is FlowReturnResult.ErrorResult -> {
//                    Toast.makeText(this, it.errorMsg.toString(), Toast.LENGTH_SHORT).show()
//                }
//                else ->{
//
//                }
//            }
//        }
        bottomNavView = binding.bottomNavView
    }

    override fun initUI() {
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        binding.bottomNavView.setupWithNavController(navController)
        binding.bottomNavView.setOnItemReselectedListener {
            if (binding.bottomNavView.selectedItemId == it.itemId) {
                return@setOnItemReselectedListener
            }
        }
    }

    fun navigateToSearchTab() {
        binding.bottomNavView.selectedItemId = R.id.searchMenu
    }

    private fun loadData(){
        viewModel.getWeather()
    }
}