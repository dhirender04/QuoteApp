package com.app.qutoes.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import com.app.qutoes.model.QutesModel
import com.google.gson.Gson

class MainViewModel(val context: Context) : ViewModel() {
    var quotesArray: Array<QutesModel> = emptyArray()
    var pos = 0

    init {
        quotesArray = loadQueryAssets()
    }

    private fun loadQueryAssets(): Array<QutesModel> {
        val inputStream = context.assets.open("qutoes.json") // open file
        val size = inputStream.available() // get size of file
        val buffer = ByteArray(size) // deigne byte array base on file size
        inputStream.read(buffer) // store file data in buffer
        inputStream.close() // close file
        val json = String(buffer, Charsets.UTF_8) //its return string array of json
        val gson = Gson()
        return gson.fromJson(json, Array<QutesModel>::class.java) // its return the object of arrays
    }
    fun getQuotes() = quotesArray[pos]
    //      fun nextQuotes() = quotesArray[++pos]
    fun nextQuotes() {
        if (pos < quotesArray.size - 1) {
            quotesArray[++pos]
        }
    }
    //      fun preQuotes() = quotesArray[--pos]
    fun preQuotes() {
        if (pos > 0) {
            quotesArray[--pos]
        }
    }

    fun copytext() {
        Toast.makeText(context, "Copied Quotes", Toast.LENGTH_SHORT).show()
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData =
            ClipData.newPlainText("text", quotesArray[pos].text + "\n-" + quotesArray[pos].author)
        clipboardManager.setPrimaryClip(clipData)
    }
}