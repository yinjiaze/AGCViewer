package edu.buaa.bravomikekilo.agcviewer

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.LocalServerSocket
import android.os.Binder
import android.os.IBinder
import android.os.Messenger
import android.widget.Toast
import kotlin.collections.ArrayList
import java.util.*
import kotlin.concurrent.schedule


class TickService : Service() {

    private fun callback() {
        for(callback in callbacks){
            callback(tick)
        }
        ++tick
    }

    private val tim: Timer = Timer()
    private var tick: Int = 0
    private var callbacks: ArrayList<(Int) -> Unit> =  ArrayList()


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(
                application ,
                "service on start" ,
                Toast.LENGTH_LONG
        ).show()
        tim.schedule(1000, 1000){callback()}
        return super.onStartCommand(intent, flags, startId)
    }

    override fun stopService(name: Intent?): Boolean {
        tim.cancel()
        return super.stopService(name)
    }


    override fun onBind(intent: Intent?): IBinder {

        return LocalBinder()

    }

    override fun onUnbind(intent: Intent?): Boolean {

        return super.onUnbind(intent)
    }

    fun addTickListener(f: (Int)->Unit) {

    }

    inner class LocalBinder: Binder() {
        fun getService(): TickService {
            return this@TickService
        }
    }
}
