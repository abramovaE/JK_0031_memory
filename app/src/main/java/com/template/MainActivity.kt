package com.template

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.template.databinding.ActivityMainBinding

import android.content.Context
import android.content.Intent
import android.view.View


class MainActivity : AppCompatActivity() {

    private val SHARED_PREF_NAME = "memory_sp"
    private val SELECTED_POSITION_TAG = "selected_position"
    private val ITEMS_STRING = "itemsString"
    private val VISIBILITY_STRING = "visibilityString"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        var selectedPosition = 0
        val sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val spSelectedPosition = sharedPreferences.getInt(SELECTED_POSITION_TAG, selectedPosition)
        val itemsString = sharedPreferences.getString(ITEMS_STRING, "")
        val visibilityString = sharedPreferences.getString(VISIBILITY_STRING, "")


        val modes = arrayOf("2x2", "4x4", "6x6", "8x8")
        binding.apply {
            val spinnerAdapter = ArrayAdapter(this@MainActivity,
                android.R.layout.simple_spinner_item, modes)
            spinner.setSelection(spSelectedPosition)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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

            playBtn.setOnClickListener {
                val intent = Intent(this@MainActivity, MemoryActivity::class.java)
                intent.putExtra(SELECTED_POSITION_TAG, selectedPosition)
                startActivity(intent)
            }

            if(!itemsString.isNullOrEmpty() && !visibilityString.isNullOrEmpty()){
                continueBtn.visibility = View.VISIBLE
                continueBtn.setOnClickListener {
                    val intent = Intent(this@MainActivity, MemoryActivity::class.java)
                    intent.putExtra(ITEMS_STRING, itemsString)
                    intent.putExtra(VISIBILITY_STRING, visibilityString)
                    startActivity(intent)
                }
            } else {
                continueBtn.visibility = View.GONE
            }
        }

        setContentView(binding.root)
    }
}

class Tags{

}