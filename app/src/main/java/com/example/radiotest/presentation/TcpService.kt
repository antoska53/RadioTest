package com.example.radiotest.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.radiotest.R
import com.example.radiotest.data.CoordinateDto
import com.example.radiotest.domain.Coordinate
import com.example.radiotest.domain.SaveCoordinateUseCase
import com.example.radiotest.presentation.map.MapFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import javax.inject.Inject


class TcpService : Service() {
    private var tcpThread: Thread? = null
    private var serverAddress: String? = null
    private var serverPort = 0
    @Inject
    lateinit var saveCoordinateUseCase: SaveCoordinateUseCase
    override fun onCreate() {
        (application as App).appComponent.getTcpComponent().inject(this)
        super.onCreate()
        tcpThread = Thread(TcpConnectionRunnable())
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        serverAddress = intent.getStringExtra(MapFragment.SERVER_ADDRESS_KEY)
        serverPort = intent.getIntExtra(MapFragment.SERVER_PORT_KEY, 0)
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
        tcpThread?.let {
            if(!it.isAlive) it.start()
        }
        return START_STICKY
    }

    private fun createNotificationChannel(){
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification(): Notification{
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("TCP Service")
            .setContentText("TCP Service is running")
            .setSmallIcon(R.drawable.ic_location)
            .build()
    }

    override fun onDestroy() {
        stopForeground(STOP_FOREGROUND_DETACH)
        tcpThread!!.interrupt()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private inner class TcpConnectionRunnable : Runnable {
        private var socket: Socket? = null
        private var input: BufferedReader? = null
        private var output: PrintWriter? = null
        override fun run() {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    socket = Socket(serverAddress, serverPort)
                    input = BufferedReader(InputStreamReader(socket!!.getInputStream()))
                    output = PrintWriter(socket!!.getOutputStream(), true)
                    while (!Thread.currentThread().isInterrupted) {
                        val message = input?.readLine()
                        if (message != null) {
                            val coordinate = Json.decodeFromString<CoordinateDto>(message)
                            GlobalScope.launch(Dispatchers.IO) {
                                saveCoordinateUseCase.saveCoordinate(
                                    Coordinate(
                                        coordinate.point.lat,
                                        coordinate.point.long
                                    )
                                )
                            }
                        }
                    }
                } catch (e: IOException) {
                    try {
                        Thread.sleep(5000)
                    } catch (ex: InterruptedException) {
                        Thread.currentThread().interrupt()
                    }
                }
            }
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"
    }
}