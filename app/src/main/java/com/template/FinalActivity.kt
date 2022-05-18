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

        App.clearSharedPreferences()

        binding = ActivityFinalBinding.inflate(layoutInflater)

        val date = intent.extras?.getString("date")

        binding.apply {
            val text = "${getString(R.string.your_time)}: $date"
            timeTv.text = text
            homeBtn.setOnClickListener {
                val homeIntent = Intent(this@FinalActivity, MainActivity::class.java)
                startActivity(homeIntent)
            }
            restartBtn.setOnClickListener {
                val restartIntent = Intent(this@FinalActivity, MemoryActivity::class.java)
                startActivity(restartIntent)
            }
        }
        setContentView(binding.root)
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}