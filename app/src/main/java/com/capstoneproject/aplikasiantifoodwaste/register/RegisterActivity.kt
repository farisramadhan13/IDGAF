package com.capstoneproject.aplikasiantifoodwaste.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomButtonRegister
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomEditTextEmail
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomEditTextPassword
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityRegisterBinding
import com.capstoneproject.aplikasiantifoodwaste.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var customButtonRegister: CustomButtonRegister
    private lateinit var customEditTextEmailRegister: CustomEditTextEmail
    private lateinit var customEditTextPasswordRegister: CustomEditTextPassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        customButtonRegister = binding.customButtonRegister
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
    }
    private fun setMyButtonEnable() {
        val resultEmail = customEditTextEmailRegister.text
        val resultPassword = customEditTextPasswordRegister.text
        customButtonRegister.isEnabled = (resultEmail != null && resultEmail.toString().isNotEmpty()) && (resultPassword != null && resultPassword.toString().isNotEmpty())
    }
}