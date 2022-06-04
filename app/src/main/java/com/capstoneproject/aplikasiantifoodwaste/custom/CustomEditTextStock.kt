package com.capstoneproject.aplikasiantifoodwaste.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.capstoneproject.aplikasiantifoodwaste.R

class CustomEditTextStock: CustomEditText {
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
    override fun init(){
        inputType = InputType.TYPE_CLASS_NUMBER
        hint = "Masukkan jumlah makanan"
        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_clear) as Drawable
        setOnTouchListener(this)
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()){
                    showClearButton()
                    when{
                        (s.toString()[0]=='0') -> error = "Jumlah tidak boleh dimulai angka 0"
                    }
                }
                else hideClearButton()
            }
            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }
}