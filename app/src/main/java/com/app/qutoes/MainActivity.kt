package com.app.qutoes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.qutoes.model.QutesModel
import com.app.qutoes.viewmodel.MainViewModel
import com.app.qutoes.viewmodelfactory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //send application context because if screen rotated the activty relaunch and data reset thats why send application context
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this,MainViewModelFactory(application)).get(MainViewModel::class.java)
        setQuotes(mainViewModel.getQuotes())
    }

    private fun setQuotes(qutesModel: QutesModel) {
        tvqutoesTitle.setText("-"+qutesModel.author)
        tvqutoesSubTitle.setText(qutesModel.text)
    }


    fun onPrevious(view: View) {
            mainViewModel.preQuotes()
            setQuotes(mainViewModel.getQuotes())
    }
  fun onCopy(view: View) {
            mainViewModel.copytext()

    }

    fun onNext(view: View) {
        mainViewModel.nextQuotes()
        setQuotes(mainViewModel.getQuotes())
    }
    fun onShare(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT,mainViewModel.getQuotes().text +"\n-"+mainViewModel.getQuotes().author)
        startActivity(intent)
    }
}