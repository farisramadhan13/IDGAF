package com.capstoneproject.aplikasiantifoodwaste.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomButtonLogin
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomEditTextEmail
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomEditTextPassword
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityLoginBinding
import com.capstoneproject.aplikasiantifoodwaste.home.HomeActivity
import com.capstoneproject.aplikasiantifoodwaste.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    private lateinit var customButtonLogin: CustomButtonLogin
    private lateinit var customEditTextEmailLogin: CustomEditTextEmail
    private lateinit var customEditTextPasswordLogin: CustomEditTextPassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        customButtonLogin = binding.customButtonLogin
        customEditTextEmailLogin = binding.customEditTextEmailLogin
        customEditTextPasswordLogin = binding.customEditTextPasswordLogin

        setMyButtonEnable()
        customEditTextEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
        customEditTextPasswordLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
        binding.labelNewAccount2.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        customButtonLogin.setOnClickListener {
            val email = customEditTextEmailLogin.text.toString().trim()
            val password = customEditTextPasswordLogin.text.toString().trim()

            loginUser(email, password)
        }
    }
    private fun setMyButtonEnable() {
        val resultEmail = customEditTextEmailLogin.text
        val resultPassword = customEditTextPasswordLogin.text
        customButtonLogin.isEnabled = (resultEmail != null && resultEmail.toString().isNotEmpty()) && (resultPassword != null && resultPassword.toString().isNotEmpty())
    }
    private fun loginUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                }else{
                    Toast.makeText(this, "${it.exception?.message}",Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }
    }
}