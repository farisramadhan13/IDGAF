package com.capstoneproject.aplikasiantifoodwaste.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomButtonRegister
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomEditTextEmail
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomEditTextName
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomEditTextPassword
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityRegisterBinding
import com.capstoneproject.aplikasiantifoodwaste.home.HomeActivity
import com.capstoneproject.aplikasiantifoodwaste.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var customButtonRegister: CustomButtonRegister
    private lateinit var customEditTextNameRegister: CustomEditTextName
    private lateinit var customEditTextEmailRegister: CustomEditTextEmail
    private lateinit var customEditTextPasswordRegister: CustomEditTextPassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        customButtonRegister = binding.customButtonRegister
        customEditTextNameRegister = binding.customEditTextNameRegister
        customEditTextEmailRegister = binding.customEditTextEmailRegister
        customEditTextPasswordRegister = binding.customEditTextPasswordRegister

        setMyButtonEnable()
        customEditTextEmailRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
        customEditTextPasswordRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
        binding.labelLoginAccount2.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        customButtonRegister.setOnClickListener {
            //val name = binding.customEditTextNameRegister.text.toString().trim()
            val email = customEditTextEmailRegister.text.toString().trim()
            val password = customEditTextPasswordRegister.text.toString().trim()
            registerUser(email, password)
        }
    }
    private fun setMyButtonEnable() {
        val resultEmail = customEditTextEmailRegister.text
        val resultPassword = customEditTextPasswordRegister.text
        customButtonRegister.isEnabled = (resultEmail != null && resultEmail.toString().isNotEmpty()) && (resultPassword != null && resultPassword.toString().isNotEmpty())
    }
    private fun registerUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
        }
    }
}