package com.emanh.emanh.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.emanh.emanh.R
import com.emanh.emanh.databinding.ActivityForgetPasswordBinding
import com.emanh.emanh.viewModel.LoginViewModel

class ForgetPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        initButton()
        initViewModel()
    }

    private fun initButton() {
        binding.buttonForgetPassword.setOnClickListener {
            loginViewModel.validateForgetPassword(
                binding.editTextPhoneNumber.text.toString(),
                binding.editTextEmail.text.toString()
            )
        }

        binding.editTextEmail.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.validateForgetPassword(
                    binding.editTextPhoneNumber.text.toString(),
                    binding.editTextEmail.text.toString()
                )
                return@OnEditorActionListener true
            }
            false
        })

        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun initViewModel() {
        loginViewModel.isFormValid.observe(this) { isValid ->
            if (isValid) {
                val emailForgetPassword = binding.editTextEmail.text.toString().trim()
                val generatedCode = loginViewModel.createCodeConfirm()

                loginViewModel.sendEmail(emailForgetPassword, generatedCode)

                val intent = Intent(this, CodeConfirmForgetPassActivity::class.java).apply {
                    putExtra("emailForgetPassword", emailForgetPassword)
                    putExtra("codeConfirmForgetPassword", generatedCode)
                }
                startActivity(intent)
            }
        }
    }
}