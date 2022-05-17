package com.template

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.template.databinding.ActivityMemoryBinding
import java.lang.StringBuilder
import java.util.*


class MemoryActivity: AppCompatActivity(), OnClickPairListener {
    private lateinit var binding: ActivityMemoryBinding
    private var clickedPositions = mutableSetOf<Int>()
    private lateinit var contentArray: Array<String>
    private lateinit var memoryAdapter: MemoryAdapter
    private var hiddenCount = 0
    private var cardsCount = 0;

    private var memoryContent = arrayOf(
        "\uD83D\uDE97", "\uD83D\uDE95", "\uD83D\uDE99", "\uD83D\uDE8C",
        "\uD83D\uDE8E", "\uD83C\uDFCE", "\uD83D\uDE93", "\uD83D\uDE91",
        "\uD83D\uDE92", "\uD83D\uDE90", "\uD83D\uDEFB", "\uD83D\uDE9A",
        "\uD83D\uDE9B", "\uD83D\uDE9C", "\uD83D\uDEB2", "\uD83D\uDEF5",
        "\uD83C\uDFCD", "\uD83D\uDEFA", "\uD83D\uDEA8", "\uD83D\uDE94",
        "\uD83D\uDE8D", "\uD83D\uDE98", "\uD83D\uDE96", "\uD83D\uDEA1",
        "\uD83D\uDEA0", "\uD83D\uDE9F", "\uD83D\uDE83", "\uD83D\uDE8B",
        "\uD83D\uDEE5", "\uD83D\uDEF3", "\uD83C\uDFD7", "\uD83D\uDEA2",
        "\uD83D\uDEF8", "\uD83D\uDE81", "\uD83D\uDEF6", "\uD83C\uDFA0",
        "\uD83D\uDEA4", "\uD83D\uDEF4", "\uD83D\uDE80", "\uD83D\uDEF0")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryBinding.inflate(layoutInflater)

        var selectedMode = intent.getIntExtra("selectedMode", 0)
        var itemsString = intent.getStringExtra("itemsString")
        var visibilityString = intent.getStringExtra("visibilityString")
        if (!itemsString.isNullOrBlank() && !visibilityString.isNullOrBlank()) {
            continueGame(itemsString, visibilityString)
        } else{
            createNewGame(selectedMode)
        }
        setContentView(binding.root)
    }

    private fun continueGame(itemsString: String,
                             visibilityString: String){
        cardsCount = itemsString.length
        Log.d("TAG", "cardsCount: $cardsCount")
        val cards = memoryContent.copyOfRange(0, cardsCount)
        Log.d("TAG", "cards: ${cards.size}")
        contentArray = itemsString.split("***").toTypedArray()
        var visibilityArray = visibilityString.split("***").toTypedArray()

        memoryAdapter = MemoryAdapter(contentArray, visibilityArray, this)
        val manager = binding.recyclerView.layoutManager as GridLayoutManager
        manager.spanCount = Math.sqrt(cardsCount * 1.0).toInt()
        binding.recyclerView.adapter = memoryAdapter
    }

    private fun createNewGame(selectedMode: Int){
        Log.d("TAG", "createNewGame")
        val mode = arrayOf(2, 4, 6, 8)
        cardsCount = mode[selectedMode] * mode[selectedMode] / 2
        Log.d("TAG", "cardsCount: $cardsCount")
        val cards = memoryContent.copyOfRange(0, cardsCount)
        var visibilityArray = emptyArray<String>()
        for(index in 0 until (mode[selectedMode] * mode[selectedMode])){
            visibilityArray += "${View.VISIBLE}"
        }
        Log.d("TAG", "cards: ${cards.size}")
        val content = mutableListOf<String>()
        content.addAll(cards)
        content.addAll(cards)
        content.shuffle()
        contentArray = content.toTypedArray()
        memoryAdapter = MemoryAdapter(contentArray, visibilityArray, this)
        val manager = binding.recyclerView.layoutManager as GridLayoutManager
        manager.spanCount = mode[selectedMode]
        binding.recyclerView.adapter = memoryAdapter

        Log.d("TAG", "visibility: ${Arrays.toString(visibilityArray)}")
        Log.d("TAG", "content: ${Arrays.toString(contentArray)}")

    }

    override fun onClickItem(position: Int) {
        clickedPositions.add(position)
        Log.d("TAG", "positions: ${clickedPositions}")


        binding.recyclerView
            .findViewHolderForAdapterPosition(position)?.
            itemView?.findViewById<TextView>(R.id.textView)?.text = contentArray[position]

        if(clickedPositions.size == 2){
            val position1 = clickedPositions.first()
            val position2 = clickedPositions.last()
            var item1 = contentArray[position1]
            var item2 = contentArray[position2]
            if(item1.equals(item2)){
                binding.recyclerView
                    .findViewHolderForAdapterPosition(position1)?.
                    itemView?.visibility = View.INVISIBLE
                binding.recyclerView
                    .findViewHolderForAdapterPosition(position2)?.
                    itemView?.visibility = View.INVISIBLE
                hiddenCount += 2
                if(hiddenCount == cardsCount * 2){
                    val intent = Intent(this, FinalActivity::class.java)
                    startActivity(intent)
                }
            } else {
                binding.recyclerView
                    .findViewHolderForAdapterPosition(position1)?.
                    itemView?.findViewById<TextView>(R.id.textView)?.text = "?"
                binding.recyclerView
                    .findViewHolderForAdapterPosition(position2)?.
                    itemView?.findViewById<TextView>(R.id.textView)?.text = "?"
            }
            clickedPositions.clear()
        }
    }


    override fun onStop() {
        super.onStop()
        var items = contentArray
//        var visibility: Array<Boolean> = emptyArray()

        var itemsString  = StringBuilder()
        var visibilityString = StringBuilder()

        for((index, item) in items.withIndex()){
            var visible = binding.recyclerView
                .findViewHolderForAdapterPosition(index)?.
                itemView?.visibility == View.VISIBLE
            itemsString.append(item).append("***")
            visibilityString.append(visible).append("***")
        }

        var sharedPreferences = getSharedPreferences("memory_sp", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString("itemsString", itemsString.toString())
            .putString("visibilityString", visibilityString.toString())
            .apply()
    }

}


interface OnClickPairListener{
    fun onClickItem(position: Int)
}