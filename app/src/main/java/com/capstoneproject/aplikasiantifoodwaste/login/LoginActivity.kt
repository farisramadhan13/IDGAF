package com.capstoneproject.aplikasiantifoodwaste.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomButtonLogin
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomEditTextEmail
import com.capstoneproject.aplikasiantifoodwaste.custom.CustomEditTextPassword

class LoginActivity : AppCompatActivity() {
    private lateinit var customButtonLogin: CustomButtonLogin
    private lateinit var customEditTextEmailLogin: CustomEditTextEmail
    private lateinit var customEditTextPasswordLogin: CustomEditTextPassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        customButtonLogin = findViewById(R.id.custom_button_login)
        customEditTextEmailLogin = findViewById(R.id.custom_edit_text_email_login)
        customEditTextPasswordLogin = findViewById(R.id.custom_edit_text_password_login)


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
//        R.id.label_new_account2.setOnClickListener
    }
    private fun setMyButtonEnable() {
        val resultEmail = customEditTextEmailLogin.text
        val resultPassword = customEditTextPasswordLogin.text
        customButtonLogin.isEnabled = (resultEmail != null && resultEmail.toString().isNotEmpty()) && (resultPassword != null && resultPassword.toString().isNotEmpty())
    }
}