package com.capstoneproject.aplikasiantifoodwaste.searchfood

import android.R.layout
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityTakeFoodBinding
import com.capstoneproject.aplikasiantifoodwaste.order.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class TakeFoodActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    lateinit var previewSelectedTimeTextView: TextView
    private lateinit var binding : ActivityTakeFoodBinding
    private lateinit var databaseRef : DatabaseReference
    private lateinit var simpanOrder: DatabaseReference
    var metode = arrayOf("Ambil sendiri", "Dengan Gosend")
    var spinner: Spinner? = null
    private lateinit var uidPenerima: String

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
        var stok = ""
        val idMakanan = intent.getStringExtra("EXTRA_ID_MAKANAN")!!
        val idPembagi = intent.getStringExtra("EXTRA_ID_PEMBAGI")!!

        databaseRef = FirebaseDatabase.getInstance().getReference("Share").child(idMakanan)
        simpanOrder = FirebaseDatabase.getInstance().getReference("Order")
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
            val timePicker = TimePickerDialog(
                this, timePickerDialogListener, 12, 10, false
            )
            timePicker.show()
        }

        spinner = this.binding.spinnerMetode
        spinner!!.setOnItemSelectedListener(this)

        val adapter = ArrayAdapter(this, layout.simple_spinner_item, metode)
        adapter.setDropDownViewResource(layout.simple_spinner_dropdown_item)
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
                        //Update stok
                        val updateStok = (stok.toInt() - binding.tiStokUserShare.text.toString().toInt())
                        val updateFood = SearchFood(searchFood?.idMakanan,
                            searchFood?.b64,
                            searchFood?.namaMakanan, updateStok.toString(),
                            searchFood?.deskripsi, searchFood?.id
                        )

                        //Buat order
                        var uniqueID = UUID.randomUUID().toString()
                        val order = Order(uniqueID,uidUser, idPembagi,binding.tiStokUserShare.text.toString(),previewSelectedTimeTextView.text.toString())
                        simpanOrder.child(uniqueID).setValue(order).addOnSuccessListener {
                            Log.e("berhasil"," berhasil simpan order")
                        }.addOnFailureListener {
                            Log.e("gagal"," gagal simpan order")
                        }

                        //Update stok ke database
                        databaseRef.setValue(updateFood).addOnSuccessListener {
                            Toast.makeText(this, "Permintaan Pengambilan Makanan Berhasil", Toast.LENGTH_SHORT).show()
                            Intent(this@TakeFoodActivity, TakeFoodConfirmationActivity::class.java).also{
                                it.putExtra("EXTRA_ID_MAKANAN", idMakanan )
                                it.putExtra("EXTRA_JAM", previewSelectedTimeTextView.text.toString())
                                it.putExtra("EXTRA_ID_PEMBAGI", searchFood?.id)
                                startActivity(it)
                            }
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