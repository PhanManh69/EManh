package com.emanh.emanh.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.emanh.emanh.R
import com.emanh.emanh.databinding.ActivityResetPasswordBinding
import com.emanh.emanh.viewModel.LoginViewModel

class ResetPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        initButton()
        initViewModel()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initButton() {
        binding.editTextPassword.setOnTouchListener { _, event ->
            val drawableEnd = binding.editTextPassword.compoundDrawables[2]
            if (event.action == MotionEvent.ACTION_UP && drawableEnd != null) {
                if (event.rawX >= (binding.editTextPassword.right - drawableEnd.bounds.width())) {
                    loginViewModel.togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }

        binding.buttonConfirm.setOnClickListener {
            loginViewModel.validateResetPassword(
                binding.editTextPassword.text.toString(),
                binding.editTextConfirmPassword.text.toString()
            )
        }

        binding.textViewBack.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
    }

    private fun initViewModel() {
        loginViewModel.isFormValid.observe(this) { isValid ->
            if (isValid) {
                startActivity(Intent(this, LoginActivity::class.java))
                Toast.makeText(this, "Thay đổi mật khẩu thành công", Toast.LENGTH_LONG).show()
            }
        }
    }
}