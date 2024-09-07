package com.emanh.emanh.view.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.emanh.emanh.R
import com.emanh.emanh.databinding.ActivitySetAvataBinding
import com.emanh.emanh.viewModel.LoginViewModel
import com.yalantis.ucrop.UCrop
import java.io.File

class SetAvatarActivity : BaseActivity() {
    private lateinit var binding: ActivitySetAvataBinding
    private val loginViewModel: LoginViewModel by viewModels()

    private val pickImageLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                openCropActivity(it)
            }
        }

    private val cropImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    loginViewModel.setAvatarUri(resultUri)
                }
            } else if (result.resultCode == UCrop.RESULT_ERROR) {
                val cropError = UCrop.getError(result.data!!)
                cropError?.printStackTrace()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_avata)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        initViewModel()
        initButton()
    }

    private fun initButton() {
        binding.buttonConfirm.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.textViewSkip.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.textViewBack.setOnClickListener { finish() }
    }

    private fun initViewModel() {
        loginViewModel.selectAvatarEvent.observe(this) {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        pickImageLauncher.launch("image/*")
    }

    private fun openCropActivity(sourceUri: Uri) {
        val timestamp = System.currentTimeMillis()
        val destinationUri = Uri.fromFile(File(cacheDir, "croppedAvatar_$timestamp.jpg"))
        val options = UCrop.Options().apply {
            setCompressionQuality(100)
            setCompressionFormat(Bitmap.CompressFormat.JPEG)
        }
        val uCrop = UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(500, 500)
            .withOptions(options)

        cropImageLauncher.launch(uCrop.getIntent(this))
    }
}
