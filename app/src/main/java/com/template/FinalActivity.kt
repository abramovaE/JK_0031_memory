package com.template

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.template.databinding.ActivityFinalBinding

class FinalActivity: AppCompatActivity() {


    private lateinit var binding: ActivityFinalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalBinding.inflate(layoutInflater)


        setContentView(binding.root)
    }
}