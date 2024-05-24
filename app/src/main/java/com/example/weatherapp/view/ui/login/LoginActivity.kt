package com.example.weatherapp.view.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityLoginBinding
import com.example.weatherapp.navigate
import com.example.weatherapp.network.showLogD
import com.example.weatherapp.network.showLogW
import com.example.weatherapp.view.BaseActivity
import com.example.weatherapp.view.ui.main.MainActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginViewModel>() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override val viewModel: LoginViewModel by viewModels()

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
        listenLiveData()
    }

    private fun listenLiveData(){
        viewModel.loginSuccess.observe(this){
            if (it){
                MainActivity.newIntent(this).navigate(this)
                finish()
            }else{
                Toast.makeText(this,"Username or password is incorrect",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                showLogD("firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                showLogW( "Google sign in failed $e")
            }
        }
    }

    override fun initUI() {
        binding.btnLogin.setOnClickListener {
           manualLogin()
        }
        binding.btnLoginGoogle.setOnClickListener {
            //firebaseAuthWithGoogle()
            signIn()
        }
    }

    private fun manualLogin(){
        if (binding.edtUserName.text.isNullOrBlank() || binding.edtPassword.text.isNullOrBlank()){

        }else{
            viewModel.login(binding.edtUserName.text.toString(), binding.edtPassword.text.toString())
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    showLogD("signInWithCredential:success")
                    val user = auth.currentUser
                    //updateUI(user)
                    MainActivity.newIntent(this).navigate(this)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    showLogW("signInWithCredential:failure $task.exception")
                    //updateUI(null)
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
}