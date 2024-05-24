package com.example.weatherapp.view.ui.login

import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.domain.model.UserVO
import com.example.weatherapp.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel(){
    val loginSuccess : MutableLiveData<Boolean> = MutableLiveData()

    val dummyUserList : ArrayList<UserVO> = arrayListOf(
        UserVO("user1","1234"),
        UserVO("user2","1234"),
        UserVO("user3","1234"),
        UserVO("user4","1234")
    )
    fun login(userName : String, password : String) {
        var isLoginSuccess = false
        for (user in dummyUserList) {
            if (user.username == userName && user.password == password){
                isLoginSuccess = true
                break
            }
        }
        loginSuccess.postValue(isLoginSuccess)
    }

}