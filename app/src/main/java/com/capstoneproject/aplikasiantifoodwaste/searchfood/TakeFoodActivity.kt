package com.capstoneproject.aplikasiantifoodwaste.searchfood

import android.R
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityTakeFoodBinding
import com.capstoneproject.aplikasiantifoodwaste.home.HomeActivity
import com.capstoneproject.aplikasiantifoodwaste.profile.AddAddressActivity
import com.capstoneproject.aplikasiantifoodwaste.profile.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class TakeFoodActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    lateinit var previewSelectedTimeTextView: TextView
    private lateinit var binding : ActivityTakeFoodBinding
    private lateinit var databaseRef : DatabaseReference
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

        val uidUser = FirebaseAuth.getInstance().currentUser?.uid
        var searchFood: SearchFood? = null
        var stok: String = ""
        val idMakanan = intent.getStringExtra("EXTRA_ID_MAKANAN")!!

        databaseRef = FirebaseDatabase.getInstance().getReference("Share").child(idMakanan)
        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                searchFood = snapshot.getValue(SearchFood::class.java)
                if (searchFood != null) {
                    stok = searchFood!!.stok.toString()
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

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

        binding.btnAmbilMakanan.setOnClickListener {
            if(uidUser.equals(searchFood?.id)){
                Toast.makeText(this, "Tidak bisa mengambil makanan yang dibagikan sendiri", Toast.LENGTH_SHORT).show()
            }
            else{
                if(binding.tiStokUserShare.text?.trim()?.length != 0){
                    if(stok.toInt()<binding.tiStokUserShare.text.toString().toInt()){
                        Toast.makeText(this, "Jumlah yang diinginkan melebihi stok. Stok saat ini: ${stok}", Toast.LENGTH_SHORT).show()
                    }
                    else if(previewSelectedTimeTextView.text.toString().equals("Belum Dipilih")){
                        Toast.makeText(this, "Waktu pengambilan belum dipilih", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val updateStok = (stok.toInt() - binding.tiStokUserShare.text.toString().toInt())
                        val updateFood = SearchFood(searchFood?.idMakanan,
                            searchFood?.b64,
                            searchFood?.namaMakanan, updateStok.toString(),
                            searchFood?.deskripsi, searchFood?.id
                        )

                        databaseRef.setValue(updateFood).addOnSuccessListener {
                            Toast.makeText(this, "Permintaan Pengambilan Makanan Berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@TakeFoodActivity, TakeFoodConfirmationActivity::class.java))
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this, "Jumlah makanan yang ingin diambil belum diisi", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }
}