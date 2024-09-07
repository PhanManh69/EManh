package com.emanh.emanh.view.activity

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.emanh.emanh.R
import com.emanh.emanh.databinding.ActivityMainBinding
import com.emanh.emanh.databinding.DesignButtonBarItemBinding
import com.emanh.emanh.model.ButtonBarItemModel
import com.emanh.emanh.view.fragment.HomeFragment
import com.emanh.emanh.view.fragment.MessageFragment
import com.emanh.emanh.view.fragment.NotificationFragment
import com.emanh.emanh.view.fragment.ProfileFragment

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtonMenuBar()
    }

    private fun initButtonMenuBar() {
        val buttonBarItems = listOf(
            ButtonBarItemModel(binding.buttonHome, R.drawable.button_home, "Trang chủ", 0),
            ButtonBarItemModel(binding.buttonMessage, R.drawable.button_message, "Tin nhắn", 1),
            ButtonBarItemModel(binding.buttonNotification, R.drawable.button_notification, "Thông báo", 2),
            ButtonBarItemModel(binding.buttonProfile, R.drawable.button_user, "Hồ sơ", 3)
        )

        var selectedPosition = 0

        buttonBarItems.forEachIndexed { index, item ->
            setupButtonBarItem(item.binding, item.iconRes, item.text, selectedPosition)
            replaceFragment(HomeFragment())
            item.binding.root.setOnClickListener {
                selectedPosition = index
                buttonBarItems.forEach { otherItem ->
                    setupButtonBarItem(otherItem.binding, otherItem.iconRes, otherItem.text, selectedPosition)
                }

                when (index) {
                    0 -> replaceFragment(HomeFragment())
                    1 -> replaceFragment(MessageFragment())
                    2 -> replaceFragment(NotificationFragment())
                    3 -> replaceFragment(ProfileFragment())
                }
            }
        }
    }

    private fun setupButtonBarItem(itemBinding: DesignButtonBarItemBinding, iconRes: Int, text: String, selectedPosition: Int) {
        val position = when (itemBinding) {
            binding.buttonHome -> 0
            binding.buttonMessage -> 1
            binding.buttonNotification -> 2
            binding.buttonProfile -> 3
            else -> -1
        }

        val isSelected = position == selectedPosition
        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.grey),
            ContextCompat.getColor(this, R.color.darkBlue)
        )

        itemBinding.apply {
            icon.setImageResource(iconRes)
            icon.setColorFilter(colors[if (isSelected) 1 else 0])
            title.text = text
            title.setTextColor(colors[if (isSelected) 1 else 0])
            indicator.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
        fragmentTransaction.commit()
    }
}