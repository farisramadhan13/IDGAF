package com.capstoneproject.aplikasiantifoodwaste.share

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import java.io.File

class ShareActivity : AppCompatActivity() {

    private lateinit var binding : ActivityShareBinding

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
            setButton(2)
        }
        binding.btnAlamat.setOnClickListener {
            startActivity(Intent(this@ShareActivity, AddressActivity::class.java))
        }
        binding.btnBagiMakanan.setOnClickListener {
            //kirim data makanan

            //Bisa toast, bisa bikin halaman lagi
            Toast.makeText(this, "Makanan berhasil dibagikan", Toast.LENGTH_SHORT).show()
            finish()
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
            binding.ivGambarMakananShare.setImageBitmap(result)
        }
    }
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@ShareActivity)
            binding.ivGambarMakananShare.setImageURI(selectedImg)
        }
    }

    private fun setButton(int: Int){
        if(int==1){
            binding.btnCamera.visibility = View.VISIBLE
            binding.space1.visibility = View.VISIBLE
            binding.btnGallery.visibility = View.VISIBLE
            binding.btnKonfirmasiYes.visibility = View.GONE
            binding.space2.visibility = View.GONE
            binding.btnKonfirmasiUlangi.visibility = View.GONE
        }
        else if(int==2){
            binding.btnCamera.visibility = View.GONE
            binding.space1.visibility = View.GONE
            binding.btnGallery.visibility = View.GONE
            binding.btnKonfirmasiYes.visibility = View.VISIBLE
            binding.space2.visibility = View.VISIBLE
            binding.btnKonfirmasiUlangi.visibility = View.VISIBLE
        }
        else if(int==3){
            binding.btnCamera.visibility = View.GONE
            binding.space1.visibility = View.GONE
            binding.btnGallery.visibility = View.GONE
            binding.btnKonfirmasiYes.visibility = View.GONE
            binding.space2.visibility = View.GONE
            binding.btnKonfirmasiUlangi.visibility = View.GONE
        }
    }
}