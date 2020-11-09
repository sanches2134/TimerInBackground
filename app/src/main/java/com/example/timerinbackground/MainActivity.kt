package com.example.timerinbackground

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class MainActivity : AppCompatActivity() {
    private val notivicationId=101
    private val CHANNEL_ID="CHANNEL_ID"

   private var stope:Boolean=true
    private var counter=0
    private val looperThread = ExampleLooperThread()
    val textt:TextView
        get()=findViewById(R.id.TextTV)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNorificationChannel()
    }

    fun startThreadd(view: View?) {
        looperThread.start()
    }
    fun taskA(view: View?)
    {
        var handler:Handler= Handler(looperThread.looper!!)
       handler.post(Runnable {
            while (stope) {
                counter++
                this.runOnUiThread {
                    textt.text=counter.toString()
                }
                SystemClock.sleep(1000)
            }
        })
    }
    override fun onPause() {
        super.onPause()
        sendNotification()
    }

    fun stop(view: View) {
        stope=false
        looperThread.looper?.quit()
    }
    private fun createNorificationChannel()
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            val name="УВЕДОМЛЕНИЕ"
            val descriptionText=counter
            val improtance=NotificationManager.IMPORTANCE_DEFAULT
            val channel=NotificationChannel(CHANNEL_ID,name,improtance).apply {
                description=descriptionText.toString()
            }
            val notificationManager:NotificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }
    private fun sendNotification()
    {
        val builder=NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setWhen(3000)
                .setContentText(counter.toString())
                .setContentTitle("ТАЙМЕР ПРОРАБОТАЛ:")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)){
            notify(notivicationId,builder.build())
        }
    }

}