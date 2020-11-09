package com.example.timerinbackground

import android.os.Handler
import android.os.Looper
import android.util.Log
 const val TAG = "ExampleLooperThread"
class ExampleLooperThread : Thread() {
     var looper: Looper? = null
     var handler: Handler?=null
    override fun run() {
        Looper.prepare()
        looper = Looper.myLooper()
        handler = Handler()
        Looper.loop()
        Log.d(TAG, "End of run()")

    }


}