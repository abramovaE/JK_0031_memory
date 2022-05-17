package com.template

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.template.databinding.ActivityFinalBinding

class FinalActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFinalBinding

    override fun onResume() {
        super.onResume()
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalBinding.inflate(layoutInflater)

        binding.apply {
            homeBtn.setOnClickListener {
                val homeIntent = Intent(this@FinalActivity, MainActivity::class.java)
                App.clearSharedPreferences()
                startActivity(homeIntent)
            }
            restartBtn.setOnClickListener {
                val restartIntent = Intent(this@FinalActivity, MemoryActivity::class.java)
                App.clearSharedPreferences()
                startActivity(restartIntent)
            }
        }
        setContentView(binding.root)
    }
}