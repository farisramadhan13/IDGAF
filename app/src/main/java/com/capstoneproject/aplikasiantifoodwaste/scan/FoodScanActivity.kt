package com.capstoneproject.aplikasiantifoodwaste.scan

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
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
import com.capstoneproject.aplikasiantifoodwaste.camera.CameraActivity
import com.capstoneproject.aplikasiantifoodwaste.camera.rotateBitmap
import com.capstoneproject.aplikasiantifoodwaste.camera.uriToFile
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityFoodScanBinding
import java.io.File

class FoodScanActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFoodScanBinding

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

        binding = ActivityFoodScanBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.hide()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnCamera.setOnClickListener { startCameraX() }
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnKonfirmasiYes.setOnClickListener {
            Toast.makeText(this,"Fitur belum dibuat", Toast.LENGTH_SHORT).show()
        }
        binding.btnKonfirmasiUlangi.setOnClickListener {
            binding.tvScanFood.visibility = View.VISIBLE
            binding.btnCamera.visibility = View.VISIBLE
            binding.btnGallery.visibility = View.VISIBLE
            binding.tvKonfirmasi.visibility = View.GONE
            binding.btnKonfirmasiYes.visibility = View.GONE
            binding.btnKonfirmasiUlangi.visibility = View.GONE
        }
    }
    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }
    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
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
            binding.ivPreview.setImageBitmap(result)
            binding.tvScanFood.visibility = View.GONE
            binding.btnCamera.visibility = View.GONE
            binding.btnGallery.visibility = View.GONE
            binding.tvKonfirmasi.visibility = View.VISIBLE
            binding.btnKonfirmasiYes.visibility = View.VISIBLE
            binding.btnKonfirmasiUlangi.visibility = View.VISIBLE
        }
    }
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@FoodScanActivity)
            binding.ivPreview.setImageURI(selectedImg)
        }
    }
}