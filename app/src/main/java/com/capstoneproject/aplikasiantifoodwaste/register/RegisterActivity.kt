package com.capstoneproject.aplikasiantifoodwaste.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomButtonLogin
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomEditTextEmail
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomEditTextPassword

class RegisterActivity : AppCompatActivity() {
    private lateinit var customButtonRegister: CustomButtonLogin
    private lateinit var customEditTextEmailRegister: CustomEditTextEmail
    private lateinit var customEditTextPasswordRegister: CustomEditTextPassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        customButtonRegister = findViewById(R.id.custom_button_register)
        customEditTextEmailRegister = findViewById(R.id.custom_edit_text_email_register)
        customEditTextPasswordRegister = findViewById(R.id.custom_edit_text_password_register)

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

    }
    private fun setMyButtonEnable() {
        val resultEmail = customEditTextEmailRegister.text
        val resultPassword = customEditTextPasswordRegister.text
        customButtonRegister.isEnabled = (resultEmail != null && resultEmail.toString().isNotEmpty()) && (resultPassword != null && resultPassword.toString().isNotEmpty())
    }
}