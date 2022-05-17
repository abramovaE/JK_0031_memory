package com.template

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.template.databinding.ActivityMainBinding
import android.widget.Toast

import android.R
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.View


class MainActivity : AppCompatActivity() {

    private lateinit var bining: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bining = ActivityMainBinding.inflate(layoutInflater)
        var selectedPosition = 0
        var sharedPreferences = getSharedPreferences("memory_sp", Context.MODE_PRIVATE)
        var spSelectedPosition = sharedPreferences.getInt("selected_position", selectedPosition)
        var itemsString = sharedPreferences.getString("itemsString", "")
        var visibilityString = sharedPreferences.getString("visibilityString", "")

        val modes = arrayOf("2x2", "4x4", "6x6", "8x8")
        bining.apply {
            var spinnerAdapter = ArrayAdapter(this@MainActivity, R.layout.simple_spinner_item, modes)
            spinner.setSelection(spSelectedPosition)
            spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            spinner.adapter = spinnerAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemSelected: View?,
                    selectedItemPosition: Int,
                    selectedId: Long) {
                    selectedPosition = selectedItemPosition
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

            playImgBtn.setOnClickListener({
                var intent = Intent(this@MainActivity, MemoryActivity::class.java)
                intent.putExtra("selectedMode", selectedPosition)
                startActivity(intent)
            })

            if(!itemsString.isNullOrBlank() && !visibilityString.isNullOrBlank()){
                continueImgBtn.visibility = View.VISIBLE
                continueImgBtn.setOnClickListener({
                    var intent = Intent(this@MainActivity, MemoryActivity::class.java)
                    intent.putExtra("itemsString", itemsString)
                    intent.putExtra("visibilityString", visibilityString)
                    startActivity(intent)
                })
            } else {
                continueImgBtn.visibility = View.GONE
            }


        }

        setContentView(bining.root)
    }
}