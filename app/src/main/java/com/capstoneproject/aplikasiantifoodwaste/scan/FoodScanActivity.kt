package com.capstoneproject.aplikasiantifoodwaste.scan

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.capstoneproject.aplikasiantifoodwaste.api.ApiConfig
import com.capstoneproject.aplikasiantifoodwaste.camera.CameraActivity
import com.capstoneproject.aplikasiantifoodwaste.camera.rotateBitmap
import com.capstoneproject.aplikasiantifoodwaste.camera.uriToFile
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityFoodScanBinding
import com.capstoneproject.aplikasiantifoodwaste.welcome.WelcomeActivity
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
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
        var fruitName: String
        var fruitMaturity: String

        setContentView(binding.root)
        supportActionBar?.hide()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnCamera.setOnClickListener {
            startCameraX()
            setButton(2)
        }
        binding.btnGallery.setOnClickListener {
            startGallery()
            setButton(2)
        }
        binding.btnKonfirmasiYes.setOnClickListener {
            uploadImage()
            setButton(3)
            Handler().postDelayed({
                val service = ApiConfig.getApiService().predict()
                service.enqueue(object: Callback<FoodScanPredictionResponse>{
                    override fun onResponse(
                        call: Call<FoodScanPredictionResponse>,
                        response: Response<FoodScanPredictionResponse>
                    ) {
                        if(response.isSuccessful){
                            val responseBody = response.body()
                            if(responseBody != null){
                                fruitName = setNameFruit(responseBody.predict1)
                                fruitMaturity = setMaturityFruit(responseBody.predict1)
                                binding.outputFruit.text = fruitName
                                binding.outputMaturity.text = fruitMaturity
                                //binding.output.text = responseBody.predict1
                                Log.e("Food Scan Activity GET", "onSuccess")
                            }
                        } else{
                            Log.e("Food Scan Activity GET", "onFailure: ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<FoodScanPredictionResponse>, t: Throwable) {
                        Log.e("Food Scan Activity GET", "onFailure: ${t.message}")
                    }
                })
                setButton(4)
            }, 2000)
        }

        binding.btnKonfirmasiUlangi.setOnClickListener {
            setButton(1)
        }

        binding.btnSimpanNo.setOnClickListener {
            setButton(1)
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

    private fun uploadImage(){
        if (getFile != null) {
            val file = getFile as File
            val result = BitmapFactory.decodeFile(file.path)
            val base64String = convertBitmapToBase64(result)

            val service = ApiConfig.getApiService().scan(base64String)
            service.enqueue(object: Callback<FoodScanResponse> {
                override fun onResponse(
                    call: Call<FoodScanResponse>,
                    response: Response<FoodScanResponse>
                ) {
                    if(response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody != null){
                            Log.e("Food Scan Activity", "onSuccess")
                        }
                    } else{
                        Log.e("Food Scan Activity", "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<FoodScanResponse>, t: Throwable) {
                    Log.e("Food Scan Activity", "onFailure: ${t.message}")
                }
            })

        } else {
            Toast.makeText(this@FoodScanActivity, "Silakan masukkan berkas gambar terlebih dahulu.", Toast.LENGTH_SHORT).show()
        }
    }

    private var getFile: File? = null

    private val launcherIntentCameraX = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )
            binding.ivPreview.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@FoodScanActivity)
            getFile = myFile
            binding.ivPreview.setImageURI(selectedImg)
        }
    }

    private fun convertBitmapToBase64(bitmap: Bitmap): String{
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val image = stream.toByteArray()
        return Base64.encodeToString(image, Base64.DEFAULT)
    }

//    private fun classifyImage(bitmap: Bitmap){
//        val model = ConvertedModel2.newInstance(applicationContext)
//
//// Creates inputs for reference.
//        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//        inputFeature0.loadBuffer(byteBuffer)
//
//// Runs model inference and gets result.
//        val outputs = model.process(inputFeature0)
//        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//
//// Releases model resources if no longer used.
//        model.close()
//    }

    private fun setButton(int: Int){
        when (int) {
            //Scan bahan makananmu
            1 -> {
                binding.tvScanFood.visibility = View.VISIBLE
                binding.btnCamera.visibility = View.VISIBLE
                binding.btnGallery.visibility = View.VISIBLE

                binding.tvKonfirmasi.visibility = View.GONE
                binding.btnKonfirmasiYes.visibility = View.GONE
                binding.btnKonfirmasiUlangi.visibility = View.GONE

                binding.progressCircular.visibility = View.GONE
                binding.tvLoading.visibility = View.GONE

                binding.tvOutput.visibility = View.GONE
                binding.outputFruit.visibility = View.GONE
                binding.tvOutputMaturity.visibility = View.GONE
                binding.outputMaturity.visibility = View.GONE
                binding.btnSimpanYes.visibility = View.GONE
                binding.btnSimpanNo.visibility = View.GONE
            }

            //Yakin menggunakan gambar ini?
            2 -> {
                binding.tvScanFood.visibility = View.GONE
                binding.btnCamera.visibility = View.GONE
                binding.btnGallery.visibility = View.GONE

                binding.tvKonfirmasi.visibility = View.VISIBLE
                binding.btnKonfirmasiYes.visibility = View.VISIBLE
                binding.btnKonfirmasiUlangi.visibility = View.VISIBLE

                binding.progressCircular.visibility = View.GONE
                binding.tvLoading.visibility = View.GONE

                binding.tvOutput.visibility = View.GONE
                binding.outputFruit.visibility = View.GONE
                binding.tvOutputMaturity.visibility = View.GONE
                binding.outputMaturity.visibility = View.GONE
                binding.btnSimpanYes.visibility = View.GONE
                binding.btnSimpanNo.visibility = View.GONE
            }

            //Loading
            3 -> {
                binding.tvScanFood.visibility = View.GONE
                binding.btnCamera.visibility = View.GONE
                binding.btnGallery.visibility = View.GONE

                binding.tvKonfirmasi.visibility = View.GONE
                binding.btnKonfirmasiYes.visibility = View.GONE
                binding.btnKonfirmasiUlangi.visibility = View.GONE

                binding.progressCircular.visibility = View.VISIBLE
                binding.tvLoading.visibility = View.VISIBLE

                binding.tvOutput.visibility = View.GONE
                binding.outputFruit.visibility = View.GONE
                binding.tvOutputMaturity.visibility = View.GONE
                binding.outputMaturity.visibility = View.GONE
                binding.btnSimpanYes.visibility = View.GONE
                binding.btnSimpanNo.visibility = View.GONE
            }

            //Hasil
            4 -> {
                binding.tvScanFood.visibility = View.GONE
                binding.btnCamera.visibility = View.GONE
                binding.btnGallery.visibility = View.GONE

                binding.tvKonfirmasi.visibility = View.GONE
                binding.btnKonfirmasiYes.visibility = View.GONE
                binding.btnKonfirmasiUlangi.visibility = View.GONE

                binding.progressCircular.visibility = View.GONE
                binding.tvLoading.visibility = View.GONE

                binding.tvOutput.visibility = View.VISIBLE
                binding.outputFruit.visibility = View.VISIBLE
                binding.tvOutputMaturity.visibility = View.VISIBLE
                binding.outputMaturity.visibility = View.VISIBLE
                binding.btnSimpanYes.visibility = View.VISIBLE
                binding.btnSimpanNo.visibility = View.VISIBLE
            }
        }
    }

    private fun setNameFruit(s: String): String{
        val name = s.substring(2,s.length)
        if(name.equals("banana",true)){
            return "Pisang"
        }
        else if(name.equals("apple",true)){
            return "Apel"
        }
        else if(name.equals("carrot",true)){
            return "Wortel"
        }
        else if(name.equals("greens",true)){
            return "Sayuran Hijau"
        }
        else if(name.equals("orange",true)){
            return "Jeruk"
        }
        else if(name.equals("tomato",true)){
            return "Tomat"
        }
        else{
            return s
        }
    }

    private fun setMaturityFruit(s: String): String{
        val char = s[0]
        return if(char == 'f'){
            "Masih segar"
        } else if(char == 'm'){
            "Sedang"
        } else if(char == 'r'){
            "Sudah busuk"
        } else{
            "Tidak diketahui"
        }
    }
}