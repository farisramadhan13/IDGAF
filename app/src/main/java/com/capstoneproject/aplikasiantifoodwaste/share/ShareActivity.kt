package com.capstoneproject.aplikasiantifoodwaste. share

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.camera.CameraActivity
import com.capstoneproject.aplikasiantifoodwaste.camera.rotateBitmap
import com.capstoneproject.aplikasiantifoodwaste.camera.uriToFile
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityShareBinding
import com.capstoneproject.aplikasiantifoodwaste.profile.AddAddressActivity
import com.capstoneproject.aplikasiantifoodwaste.profile.AddressActivity
import com.capstoneproject.aplikasiantifoodwaste.scan.FoodScanActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ShareActivity : AppCompatActivity() {

    private lateinit var binding : ActivityShareBinding
    private var everTaken = false
    private lateinit var database : DatabaseReference
    private var b64 = ""
    private var getFile: File? = null

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.titleBarShare.bringToFront()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                ShareActivity.REQUIRED_PERMISSIONS,
                ShareActivity.REQUEST_CODE_PERMISSIONS
            )
        }

        setButton(1)

        binding.btnCamera.setOnClickListener {
            startCameraX()
            setButton(2)
        }
        binding.btnGallery.setOnClickListener {
            startGallery()
            setButton(2)
        }
        binding.btnKonfirmasiYes.setOnClickListener {
            setButton(3)
        }
        binding.btnKonfirmasiUlangi.setOnClickListener {
            setButton(1)
        }
        binding.ivGambarMakananShare.setOnClickListener {
            if(everTaken) setButton(2)
        }
        binding.btnAlamat.setOnClickListener {
            startActivity(Intent(this@ShareActivity, AddressActivity::class.java))
        }
        binding.btnBagiMakanan.setOnClickListener {
            //kirim data makanan
            //val gambarMakanan = binding.ivGambarMakananShare.text.toString()
            if(binding.tiNamaMakananShare.text?.trim()?.length != 0 && binding.tiDeskripsiMakananShare.text?.trim()?.length != 0 && binding.tiStokUserShare.text?.trim()?.length != 0 && getFile!=null){
                val namaMakanan = binding.tiNamaMakananShare.text.toString()
                val deskripsi = binding.tiDeskripsiMakananShare.text.toString()
                val stok = binding.tiStokUserShare.text.toString()
                b64 = convertBitmapToBase64(BitmapFactory.decodeFile(reduceFileImage(getFile as File).path))

                database = FirebaseDatabase.getInstance().getReference("Share")
                val Share = Share(namaMakanan, deskripsi, stok, b64)
                database.child(namaMakanan).setValue(Share).addOnSuccessListener {
                    binding.tiNamaMakananShare.text?.clear()
                    binding.tiDeskripsiMakananShare.text?.clear()
                    binding.tiStokUserShare.text?.clear()
                    getFile = null
                    Toast.makeText(this, "Makanan berhasil dibagikan", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Data Belum Lengkap", Toast.LENGTH_SHORT).show()
            }

//            val nama = binding.tv
//            val nomorTelepon
//            val alamatLengkap
            //Bisa toast, bisa bikin halaman lagi
            //Toast.makeText(this, "Makanan berhasil dibagikan", Toast.LENGTH_SHORT).show()
            //finish()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }
    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCameraX = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )
            getFile = myFile
            binding.ivGambarMakananShare.setImageBitmap(result)
            everTaken = true
        }
    }
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@ShareActivity)
            getFile = myFile
            binding.ivGambarMakananShare.setImageURI(selectedImg)
        }
    }

    private fun reduceFileImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 300000)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }

    private fun convertBitmapToBase64(bitmap: Bitmap): String{
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val image = stream.toByteArray()
        return Base64.encodeToString(image, Base64.DEFAULT)
    }
    private fun setButton(int: Int){
        when (int) {
            1 -> {
                binding.btnCamera.visibility = View.VISIBLE
                binding.space1.visibility = View.VISIBLE
                binding.btnGallery.visibility = View.VISIBLE
                binding.btnKonfirmasiYes.visibility = View.GONE
                binding.space2.visibility = View.GONE
                binding.btnKonfirmasiUlangi.visibility = View.GONE
            }
            2 -> {
                binding.btnCamera.visibility = View.GONE
                binding.space1.visibility = View.GONE
                binding.btnGallery.visibility = View.GONE
                binding.btnKonfirmasiYes.visibility = View.VISIBLE
                binding.space2.visibility = View.VISIBLE
                binding.btnKonfirmasiUlangi.visibility = View.VISIBLE
            }
            3 -> {
                binding.btnCamera.visibility = View.GONE
                binding.space1.visibility = View.GONE
                binding.btnGallery.visibility = View.GONE
                binding.btnKonfirmasiYes.visibility = View.GONE
                binding.space2.visibility = View.GONE
                binding.btnKonfirmasiUlangi.visibility = View.GONE
            }
        }
    }
}