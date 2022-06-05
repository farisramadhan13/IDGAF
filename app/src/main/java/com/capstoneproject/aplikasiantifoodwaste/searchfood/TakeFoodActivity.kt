package com.capstoneproject.aplikasiantifoodwaste.searchfood

import android.R
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityTakeFoodBinding

class TakeFoodActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    lateinit var previewSelectedTimeTextView: TextView
    private lateinit var binding : ActivityTakeFoodBinding
    var metode = arrayOf("Ambil sendiri", "Dengan Gosend")
    var spinner: Spinner? = null

    private val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                val formattedTime: String = when {
                    hourOfDay == 0 -> {
                        if (minute < 10) {
                            "${hourOfDay + 12}:0${minute} AM"
                        } else {
                            "${hourOfDay + 12}:${minute} AM"
                        }
                    }
                    hourOfDay > 12 -> {
                        if (minute < 10) {
                            "${hourOfDay - 12}:0${minute} PM"
                        } else {
                            "${hourOfDay - 12}:${minute} PM"
                        }
                    }
                    hourOfDay == 12 -> {
                        if (minute < 10) {
                            "${hourOfDay}:0${minute} PM"
                        } else {
                            "${hourOfDay}:${minute} PM"
                        }
                    }
                    else -> {
                        if (minute < 10) {
                            "${hourOfDay}:${minute} AM"
                        } else {
                            "${hourOfDay}:${minute} AM"
                        }
                    }
                }
                previewSelectedTimeTextView.text = formattedTime
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val buttonPickTime: Button = binding.btnWaktuAmbil
        previewSelectedTimeTextView = binding.tvWaktuAmbil

        buttonPickTime.setOnClickListener {
            val timePicker: TimePickerDialog = TimePickerDialog(
                this, timePickerDialogListener, 12, 10, false
            )
            timePicker.show()
        }

        spinner = this.binding.spinnerMetode
        spinner!!.setOnItemSelectedListener(this)

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, metode)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = adapter

    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }
}