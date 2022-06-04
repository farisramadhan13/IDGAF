package com.capstoneproject.aplikasiantifoodwaste.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.capstoneproject.aplikasiantifoodwaste.R

class CustomEditTextSearch: CustomEditText {
    private lateinit var searchButtonImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
    override fun init() {
        searchButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_search) as Drawable
        setOnTouchListener(this)
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) showSearchButton() else hideSearchButton()
            }
            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }
    private fun showSearchButton(){
        setButtonDrawables(endOfTheText = searchButtonImage)
    }
    private fun hideSearchButton() {
        setButtonDrawables()
    }
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val searchButtonStart: Float
            val searchButtonEnd: Float
            var isSearchButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                searchButtonEnd = (searchButtonImage.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < searchButtonEnd -> isSearchButtonClicked = true
                }
            } else {
                searchButtonStart = (width - paddingEnd - searchButtonImage.intrinsicWidth).toFloat()
                when {
                    event.x > searchButtonStart -> isSearchButtonClicked = true
                }
            }
            if (isSearchButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        searchButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_search) as Drawable
                        showSearchButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        searchButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_search) as Drawable
                        when {
                            text != null -> text?.clear()
                        }
                        hideSearchButton()
                        return true
                    }
                    else -> return false
                }
            } else return false
        }
        return false
    }
}