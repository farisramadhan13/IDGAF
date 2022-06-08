package com.capstoneproject.aplikasiantifoodwaste.custom

import android.content.Context
import android.content.Intent

class CustomEditTextUtils {
    companion object {
        fun startActivity(context: Context, cls: Class<*>) {
            val intent = Intent(context, cls)
            context.startActivity(intent)
        }
    }
}