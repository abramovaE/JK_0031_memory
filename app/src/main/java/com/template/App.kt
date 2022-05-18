package com.template

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPreferences =
        getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        spSelectedPosition = sharedPreferences.getInt(SELECTED_POSITION_TAG, 0)
        itemsString = sharedPreferences.getString(ITEMS_STRING, "")!!
        visibilityString = sharedPreferences.getString(VISIBILITY_STRING, "")!!
        time = sharedPreferences.getLong(TIME, 0L)
    }

    companion object {
        private const val SHARED_PREF_NAME = "memory_sp"
        private const val SELECTED_POSITION_TAG = "selected_position"
        private const val ITEMS_STRING = "itemsString"
        private const val VISIBILITY_STRING = "visibilityString"
        private const val TIME = "time"

        private var spSelectedPosition = 0
        private var itemsString = ""
        private var visibilityString = ""
        private lateinit var sharedPreferences: SharedPreferences
        private var time = 0L


        lateinit var instance: App
            private set

        fun getTime(): Long{
            return time
        }

        fun setTime(time: Long){
            this.time = time
            sharedPreferences.edit().putLong(TIME, time).apply()
        }

        fun getItemsString(): String{
            return itemsString
        }

        fun setItemsString(itemsString: String){
            this.itemsString = itemsString
            sharedPreferences.edit().putString(ITEMS_STRING, itemsString).apply()
        }

        fun getVisibilityString(): String{
            return visibilityString
        }

        fun setVisibilityString(visibilityString: String){
            this.visibilityString = visibilityString
            sharedPreferences.edit().putString(VISIBILITY_STRING, visibilityString).apply()
        }

        fun getSelectedPosition(): Int{
            return spSelectedPosition
        }

        fun setSelectedPosition(position: Int){
            this.spSelectedPosition = position
            sharedPreferences.edit().putInt(SELECTED_POSITION_TAG, position).apply()
        }

        fun clearSharedPreferences(){
            itemsString = ""
            visibilityString = ""
            spSelectedPosition = 0
            time = 0L
            Log.d("TAG", "clear sp")
            this.sharedPreferences.edit()
                .remove(ITEMS_STRING)
                .remove(VISIBILITY_STRING)
                .remove(SELECTED_POSITION_TAG)
                .remove(TIME)
                .apply()
        }

    }
}