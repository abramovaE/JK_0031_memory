package com.template

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.template.databinding.ActivityMainBinding

import android.content.Intent
import android.view.View
import android.view.WindowManager


class MainActivity : AppCompatActivity() {

    private var spSelectedPosition = 0
    private var itemsString = ""
    private var visibilityString = ""

    private lateinit var binding: ActivityMainBinding

    private fun load(){
        spSelectedPosition = App.getSelectedPosition()
        itemsString = App.getItemsString()
        visibilityString = App.getVisibilityString()
    }

    override fun onResume() {
        super.onResume()
        load()
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        var selectedPosition = 0

        load()
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
                App.setSelectedPosition(selectedPosition)
                startActivity(intent)
            }

            if(itemsString.isNotEmpty() && visibilityString.isNotEmpty()){
                continueBtn.visibility = View.VISIBLE
                continueBtn.setOnClickListener {
                    val intent = Intent(this@MainActivity, MemoryActivity::class.java)
                    startActivity(intent)
                }
            } else {
                continueBtn.visibility = View.GONE
            }
        }

        setContentView(binding.root)
    }
}
