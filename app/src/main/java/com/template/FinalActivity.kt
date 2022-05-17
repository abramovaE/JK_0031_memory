package com.template

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.template.databinding.ActivityFinalBinding

class FinalActivity: AppCompatActivity() {

    private val SELECTED_POSITION_TAG = "selected_position"

    private lateinit var binding: ActivityFinalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalBinding.inflate(layoutInflater)

        binding.apply {
            var homeIntent = Intent(this@FinalActivity, MainActivity::class.java)
            var restartIntent = Intent(this@FinalActivity, MemoryActivity::class.java)
            restartIntent.putExtra(SELECTED_POSITION_TAG, intent.extras?.getInt(SELECTED_POSITION_TAG))

            homeBtn.setOnClickListener({v-> startActivity(homeIntent)})
            restartBtn.setOnClickListener({v->startActivity(restartIntent)})


        }

        setContentView(binding.root)
    }
}