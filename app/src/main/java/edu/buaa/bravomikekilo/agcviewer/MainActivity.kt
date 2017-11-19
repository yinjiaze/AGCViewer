package edu.buaa.bravomikekilo.agcviewer

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
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
        clickMe.setOnClickListener {clicked(it)}
        showButton.setOnClickListener { jump2Show(it) }
        toggleService.setOnClickListener { toggleService(it) }

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

    private var serviceConn = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TickService.LocalBinder
            val mService = binder.getService()
            mService.addTickListener { UpdateOnTick(it) }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceState = false
            return Unit // Do nothing by now, because no one care.
        }
    }

    private fun toggleService(view: View) {
        val intent = Intent(this, TickService::class.java)
        if(!serviceState){
            startService(intent)
            bindService(intent, serviceConn, Context.BIND_AUTO_CREATE)
            serviceState = true
        } else {
            unbindService(serviceConn)
            stopService(intent)
            serviceState = false
        }
    }

    private fun UpdateOnTick(tick: Int) {
        serviceLog.text = tick.toString()
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


}

