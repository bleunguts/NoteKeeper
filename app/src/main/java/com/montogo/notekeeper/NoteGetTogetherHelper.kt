package com.montogo.notekeeper

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class NoteGetTogetherHelper(val context: Context, val lifecycle: Lifecycle) : LifecycleObserver {
    init {
        lifecycle.addObserver(this)
    }
    val tag = this::class.simpleName
    var currentLat = 0.0
    var currentLon = 0.0

    val locationManager = PseudoLocationManager(context, {lat, lon ->
        currentLat = lat
        currentLon = lon
        Log.d(tag, "Location Callback lat:$lat, lon:$lon")
    })

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startHandler() {
        locationManager.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopHandler() {
        locationManager.stop()
    }
}