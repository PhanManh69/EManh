package com.emanh.emanh.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.emanh.emanh.R
import com.emanh.emanh.databinding.ActivityCodeConfirmForgetPassBinding
import com.emanh.emanh.viewModel.LoginViewModel

class CodeConfirmForgetPassActivity : BaseActivity() {
    private lateinit var binding: ActivityCodeConfirmForgetPassBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private var emailRegister: String? = null
    private var codeSendEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code_confirm_forget_pass)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        initUI()
        initButton()
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        emailRegister = intent.getStringExtra("emailForgetPassword")
        emailRegister?.let {
            binding.textViewEmail.text = "Nhập mã xác nhận của bạn đã được gửi đến email $it"
        } ?: run {
            binding.textViewEmail.text = "Không thể tìm thấy địa chỉ email"
        }
    }

    private fun initButton() {
        codeSendEmail = intent.getStringExtra("codeConfirmForgetPassword")
        binding.buttonConfirm.setOnClickListener {
            val codeConfirm = binding.editTextEnterCode.text.toString().replace(" ", "").take(6)

            if (codeConfirm == codeSendEmail) {
                startActivity(Intent(this, ResetPasswordActivity::class.java))
            } else {
                Toast.makeText(this, "Mã code bạn vừa nhập không chính xác", Toast.LENGTH_LONG).show()
            }
        }

        binding.textViewSendCode.setOnClickListener {
            val newCode = loginViewModel.createCodeConfirm()
            loginViewModel.sendEmail(emailRegister.toString(), newCode)
            codeSendEmail = newCode
            Toast.makeText(this, "Mã code mới đã được gửi đến bạn", Toast.LENGTH_LONG).show()
        }

        binding.textViewForgetPassword.setOnClickListener { finish() }
    }
}