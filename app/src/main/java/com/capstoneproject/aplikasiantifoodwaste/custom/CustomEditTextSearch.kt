package com.capstoneproject.aplikasiantifoodwaste.custom

import android.content.Context
import android.util.AttributeSet

class CustomEditTextSearch: CustomEditText {
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
        setOnTouchListener(this)
    }
}