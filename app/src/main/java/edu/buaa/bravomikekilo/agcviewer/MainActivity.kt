
/*
author: Mingkun Bao/ bravomikekilo bravomikekilo@buaa.edu.cn

MainActivity
 */

package edu.buaa.bravomikekilo.agcviewer

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Messenger
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

/**
 * [MainActivity] main activity of [AGCViewer]
 */
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


    /**
     * [Boolean] used to indicate central text state
     */
    private var clicked = false


    /**
     * [Handler] of [messenger], used to handle message from [TickService]
     */
    private var handler: Handler = Handler(Handler.Callback { msg ->
        updateOnTick(msg.what)
        true
    })


    /**
     * [Messenger] used to receive message from [TickService]
     */
    private var messenger: Messenger = Messenger(handler)

    /**
     * [serviceState] [Boolean] used to indicate [TickService] status
     * [serviceState] is [false] if [TickService] is down
     *
     */
    private var serviceState = false

    /**
     * [serviceConn] is [ServiceConnection] Object used to bind [TickService]
     */

    private var serviceConn = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TickService.LocalBinder
            val mService = binder.getService()
            mService.addListener(messenger.binder)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceState = false
            return Unit // Do nothing by now, because no one care.
        }
    }

    /**
     * click listener of [toggleService], used to toggle [TickService]
     * turn on [TickService] if not [serviceState]
     * turn off [TickService] if [serviceState]
     *
     * @param view useless
     */

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

    /**
     * callback function used to update [serviceLog] by [tick]
     *
     * @param tick [Int] valued to be rendered on [serviceLog]
     */

    private fun updateOnTick(tick: Int) {
        serviceLog.text = tick.toString()
    }


    /**
     * click callback function of [clickMe]
     */

    private fun clicked(view: View) {
        clicked = !clicked
        renderCentral()
    }

    /**
     * function used to render text to [centralText]
     * [centralText.text] is only related to [MainActivity.clicked]
     *
     */
    private fun renderCentral(){
        if(!clicked){
            centralText.text = "click button"
        } else {
            centralText.text = "clicked"
        }
    }



    /**
     * function used to jump to [ShowActivity]
     *
     * @param view useless
     */
    private fun jump2Show(view: View){
        val intent = Intent(this, ShowActivity::class.java)
        startActivity(intent)
    }


}

