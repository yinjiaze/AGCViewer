/*
 author: Mingkun Bao / bravomikekilo bravomikekilo@buaa.edu.cn
 */
package edu.buaa.bravomikekilo.agcviewer

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule


class TickService : Service() {

    /**
     * callback function for [tim]
     * plus 1 to [tick] and send [tick] by Messenger
     */
    private fun callback() {
        val msg = Message.obtain(null, tick, 0, 0)
        mMessenger?.send(msg)
        ++tick
    }

    private val tim: Timer = Timer()
    private var tick: Int = 0
    private var mMessenger: Messenger? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(
                application ,
                "service on start" ,
                Toast.LENGTH_LONG
        ).show()
        tim.schedule(1000, 1000){callback()}
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        Toast.makeText(
                application,
                "service on bind",
                Toast.LENGTH_LONG
        ).show()
        return LocalBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Toast.makeText(
                application,
                "service on unbind",
                Toast.LENGTH_LONG
        ).show()
        tim.cancel()
        return super.onUnbind(intent)
    }

    /**
     * addListener by construct Messenger
     */
    fun addListener(binder: IBinder){
        mMessenger = Messenger(binder)
    }

    /**
     * Binder Object returned in onBind
     *
     * @method [getService] return reference of [TickService]
     */
    inner class LocalBinder: Binder() {
        fun getService(): TickService {
            return this@TickService
        }
    }
}
