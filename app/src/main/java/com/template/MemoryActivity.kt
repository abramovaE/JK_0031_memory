package com.template

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.template.databinding.ActivityMemoryBinding
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.sqrt


class MemoryActivity: AppCompatActivity(), OnClickPairListener {

    private lateinit var binding: ActivityMemoryBinding
    private var clickedPositions = mutableSetOf<Int>()
    private lateinit var contentArray: Array<String>
    private var visibility = emptyArray<Int>()

    private lateinit var memoryAdapter: MemoryAdapter
    private var hiddenCount = 0
    private var cardsCount = 0
    private var spanCount = 0

    private val question = "❓"
    private var isClickable = false
    private var selectedMode = 0
    private var startTime = 0L

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

    override fun onResume() {
        super.onResume()
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryBinding.inflate(layoutInflater)

        val isContinue = intent.extras?.getBoolean("isContinue", false)
        selectedMode = App.getSelectedPosition()

        val itemsString = App.getItemsString()
        val visibilityString = App.getVisibilityString()

        if (isContinue == true) {
            continueGame(itemsString, visibilityString)
        } else{
            createNewGame(selectedMode)
        }

        memoryAdapter = MemoryAdapter(contentArray, this)
        val manager = binding.recyclerView.layoutManager as GridLayoutManager
        manager.spanCount = spanCount
        binding.recyclerView.adapter = memoryAdapter

        isClickable = false

        showAllCards()
        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
            }
            override fun onFinish() {
                hideAllCards()
                isClickable = true
            }
        }
        timer.start()
        setContentView(binding.root)
    }





    private fun continueGame(itemsString: String,
                             visibilityString: String){

        contentArray = itemsString.split("***").toTypedArray()
        cardsCount = contentArray.size
        val visibilityArray = visibilityString.split("***").toTypedArray()
        for(item in visibilityArray){
            visibility += item.toInt()
        }
        spanCount = sqrt(cardsCount * 1.0).toInt()
    }

    private fun createNewGame(selectedMode: Int){
        val mode = arrayOf(2, 4, 6, 8)
        cardsCount = mode[selectedMode] * mode[selectedMode]
        val cardsPair = cardsCount / 2

        for(index in 0 until cardsCount){
            visibility += View.VISIBLE
        }

        val content = mutableListOf<String>()
        content.addAll(memoryContent.copyOfRange(0, cardsPair))
        content.addAll(memoryContent.copyOfRange(0, cardsPair))
        content.shuffle()

        contentArray = content.toTypedArray()
        spanCount = mode[selectedMode]
    }


    private fun showAllCards(){
        val count = memoryAdapter.itemCount
        for(i in 0 until count){
            binding.recyclerView
                .findViewHolderForAdapterPosition(i)?.
                itemView?.findViewById<TextView>(R.id.textView)?.text = contentArray[i]
        }
    }

    fun hideAllCards(){
        for(i in 0 until memoryAdapter.itemCount){
            if(visibility[i] != View.INVISIBLE) {
                setQuestion(i)
            } else {
                setInvisible(i)
            }
        }
    }

    override fun onClickItem(position: Int) {
        if(isClickable) {

            if(startTime == 0L){
                startTime = Calendar.getInstance().time.time
            }
            clickedPositions.add(position)
            binding.recyclerView
                .findViewHolderForAdapterPosition(position)?.itemView?.
                findViewById<TextView>(R.id.textView)?.text =
                contentArray[position]

            if (clickedPositions.size == 2) {
                isClickable = false
            }

            val timer = object: CountDownTimer(1000, 1000){
                override fun onTick(p0: Long) {}
                override fun onFinish() {
                    if (clickedPositions.size == 2) {
                        val position1 = clickedPositions.first()
                        val position2 = clickedPositions.last()
                        val item1 = contentArray[position1]
                        val item2 = contentArray[position2]
                        if (item1 == item2) {
                            setInvisible(position1)
                            setInvisible(position2)
                            hiddenCount += 2
                            if (isGameFinished()) {

                                val formatDate = getFinalTimeString()
                                startTime = 0L
                                App.clearSharedPreferences()
                                val intent = Intent(this@MemoryActivity, FinalActivity::class.java)
                                intent.putExtra("date", formatDate)
                                App.setSelectedPosition(selectedMode)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            setQuestion(position1)
                            setQuestion(position2)
                        }
                        clickedPositions.clear()
                        isClickable = true
                    }
                }
            }
            timer.start()

        }
    }

    private fun getFinalTimeString(): String{
        val prefTime = App.getTime()
        val date = getFinalTime(prefTime)
        val sdf = SimpleDateFormat("mm:ss", Locale.US)
        return sdf.format(date)
    }

    private fun getFinalTime(prefTime: Long): Date {
        val endTime = Calendar.getInstance().time.time
        val time = endTime - startTime
        return Date(time + prefTime)
    }

    private fun isGameFinished(): Boolean{
        val count = visibility.filter{ it == View.INVISIBLE }.count()
        return count == cardsCount
    }

    private fun setQuestion(position: Int){
        binding.recyclerView
            .findViewHolderForAdapterPosition(position)?.
            itemView?.findViewById<TextView>(R.id.textView)?.text = question
    }

    private fun setInvisible(position: Int){
        binding.recyclerView
            .findViewHolderForAdapterPosition(position)?.
            itemView?.visibility = View.INVISIBLE
        visibility[position] = View.INVISIBLE
    }

    override fun onPause() {
        super.onPause()

        val items = contentArray
        val itemsString  = StringBuilder()
        val visibilityString = StringBuilder()

        for((index, item) in items.withIndex()){
            val visible = binding.recyclerView
                .findViewHolderForAdapterPosition(index)?.
                itemView?.visibility

            itemsString.append(item)
            visibilityString.append(visible)
            if(index != items.size - 1){
                itemsString.append("***")
                visibilityString.append("***")
            }
        }

        val date = getFinalTime(0L)
        startTime = 0L

        App.setTime(date.time)
        App.setItemsString(itemsString.toString())
        App.setVisibilityString(visibilityString.toString())
        App.setSelectedPosition(selectedMode)
    }
}


interface OnClickPairListener{
    fun onClickItem(position: Int)
}