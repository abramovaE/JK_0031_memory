package com.template

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.template.databinding.ActivityMemoryBinding


class MemoryActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMemoryBinding
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


        val mode = arrayOf(2, 4, 6, 8)

        val cardsCount = mode[selectedMode] * mode[selectedMode] / 2

        Log.d("TAG", "cardsCount: $cardsCount")
        val cards = memoryContent.copyOfRange(0, cardsCount)

        Log.d("TAG", "cards: ${cards.size}")



        setContentView(binding.root)    }

}