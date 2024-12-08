package com.emanh.emanh.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.emanh.emanh.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BeginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_begin)

        initIntro()
    }

    private fun initIntro() {
        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@BeginActivity, LoginActivity::class.java))
            finish()
        }
    }
}