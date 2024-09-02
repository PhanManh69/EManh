package com.emanh.emanh.viewModel

import android.text.InputType
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emanh.emanh.R

class LoginViewModel : ViewModel() {
    private val _passwordIconStart = MutableLiveData<Int>()
    val passwordIconStart: LiveData<Int> get() = _passwordIconStart

    private val _passwordIconEnd = MutableLiveData<Int>()
    val passwordIconEnd: LiveData<Int> get() = _passwordIconEnd

    private val _inputType = MutableLiveData<Int>()
    val inputType: LiveData<Int> get() = _inputType

    init {
        _passwordIconStart.value = R.drawable.icon_password
        _passwordIconEnd.value = R.drawable.icon_hidden_pass
        _inputType.value = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }

    fun togglePasswordVisibility() {
        if (_inputType.value == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            _inputType.value = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            _passwordIconEnd.value = R.drawable.icon_show_pass
        } else {
            _inputType.value = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            _passwordIconEnd.value = R.drawable.icon_hidden_pass
        }
    }
}