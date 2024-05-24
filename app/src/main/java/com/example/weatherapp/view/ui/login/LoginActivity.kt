package com.example.weatherapp.view.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.weatherapp.databinding.ActivityLoginBinding
import com.example.weatherapp.navigate
import com.example.weatherapp.view.BaseActivity
import com.example.weatherapp.view.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginViewModel>() {

    private lateinit var binding : ActivityLoginBinding

    override val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun initUI() {
        binding.btnLogin.setOnClickListener {
            MainActivity.newIntent(this).navigate(this)
            finish()
        }
    }

}