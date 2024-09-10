package com.emanh.emanh.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.emanh.emanh.R
import com.emanh.emanh.databinding.ActivityRegisterBinding
import com.emanh.emanh.viewModel.LoginViewModel

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
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

        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.buttonRegister.setOnClickListener {
            loginViewModel.validateRegister(
                binding.editTextUsername.text.toString(),
                binding.editTextPhoneNumber.text.toString(),
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString(),
                binding.editTextConfirmPassword.text.toString()
            )
        }

        binding.editTextConfirmPassword.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.validateRegister(
                    binding.editTextUsername.text.toString(),
                    binding.editTextPhoneNumber.text.toString(),
                    binding.editTextEmail.text.toString(),
                    binding.editTextPassword.text.toString(),
                    binding.editTextConfirmPassword.text.toString()
                )
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun initViewModel() {
        loginViewModel.isFormValid.observe(this) { isValid ->
            if (isValid) {
                val emailRegister = binding.editTextEmail.text.toString().trim()
                val generatedCode = loginViewModel.createCodeConfirm()

                loginViewModel.sendEmail(emailRegister, generatedCode)

                val intent = Intent(this, CodeConfirmRegisterActivity::class.java).apply {
                    putExtra("emailRegister", emailRegister)
                    putExtra("codeConfirmRegister", generatedCode)
                }
                startActivity(intent)
            }
        }
    }
}