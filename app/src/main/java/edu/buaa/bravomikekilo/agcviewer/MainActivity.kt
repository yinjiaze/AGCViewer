package edu.buaa.bravomikekilo.agcviewer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState != null){
            clicked = savedInstanceState.getBoolean("clicked")
        }
        setContentView(R.layout.activity_main)
        title = "Main"
        renderCentral()
        clickMe.setOnClickListener {view -> clicked(view)}
        showButton.setOnClickListener { view -> jump2Show(view) }

    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putBoolean("clicked", clicked)
        outState.putBoolean("serviceState", serviceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        clicked = savedInstanceState!!.getBoolean("clicked")
        serviceState = savedInstanceState.getBoolean("serviceState")
        renderCentral()
    }



    private var clicked = false
    private var serviceState = false


    private fun toggleService(view: View) {
        val intent = Intent(this, TickService::class.java)
        if(!serviceState){
            startService(intent)
        } else {
            stopService(intent)
        }
    }

    private fun clicked(view: View) {
        clicked = !clicked
        renderCentral()
    }


    private fun renderCentral(){
        if(!clicked){
            centralText.text = "click button"
        } else {
            centralText.text = "clicked"
        }
    }

    private fun jump2Show(view: View){
        val intent = Intent(this, ShowActivity::class.java)
        startActivity(intent)
    }

    inner class handleMessage: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }

    }

}

