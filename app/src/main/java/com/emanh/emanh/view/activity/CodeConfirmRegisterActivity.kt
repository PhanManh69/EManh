package com.emanh.emanh.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.emanh.emanh.R
import com.emanh.emanh.databinding.ActivityCodeConfirmRegisterBinding
import com.emanh.emanh.viewModel.LoginViewModel

class CodeConfirmRegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityCodeConfirmRegisterBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private var emailRegister: String? = null
    private var codeSendEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code_confirm_register)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        initUI()
        initButton()
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        emailRegister = intent.getStringExtra("emailRegister")
        emailRegister?.let {
            binding.textViewEmail.text = "Nhập mã xác nhận của bạn đã được gửi đến email $it"
        } ?: run {
            binding.textViewEmail.text = "Không thể tìm thấy địa chỉ email"
        }
    }

    private fun initButton() {
        codeSendEmail = intent.getStringExtra("codeConfirmRegister")
        binding.buttonConfirm.setOnClickListener {
            val codeConfirm = binding.editTextEnterCode.text.toString().replace(" ", "").take(6)

            if (codeConfirm == codeSendEmail) {
                startActivity(Intent(this, EnterUserInformationActivity::class.java))
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

        binding.textViewRegister.setOnClickListener { finish() }
    }
}