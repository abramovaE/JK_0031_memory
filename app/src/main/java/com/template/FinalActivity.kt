package com.template

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.template.databinding.ActivityFinalBinding

class FinalActivity: AppCompatActivity() {

    private val SHARED_PREF_NAME = "memory_sp"
    private val SELECTED_POSITION_TAG = "selected_position"
    private val ITEMS_STRING = "itemsString"
    private val VISIBILITY_STRING = "visibilityString"


    private lateinit var binding: ActivityFinalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalBinding.inflate(layoutInflater)

        binding.apply {
            var homeIntent = Intent(this@FinalActivity, MainActivity::class.java)
            var restartIntent = Intent(this@FinalActivity, MemoryActivity::class.java)
            restartIntent.putExtra(SELECTED_POSITION_TAG, intent.extras?.getInt(SELECTED_POSITION_TAG))

            homeBtn.setOnClickListener({v->
                clearSharedPreferences()
                startActivity(homeIntent)})
            restartBtn.setOnClickListener({v->
                clearSharedPreferences()
                startActivity(restartIntent)})


        }

        setContentView(binding.root)
    }

    private fun clearSharedPreferences(){
        val sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences
            .edit()
            .remove(ITEMS_STRING)
            .remove(VISIBILITY_STRING)
            .remove(SELECTED_POSITION_TAG)
            .apply()
    }
}