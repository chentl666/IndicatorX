package com.ctl.indicator.demo.lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * created by : chentl
 * Date: 2020/07/01
 */
class CustomLifecycle : LifecycleObserver {

    private val TAG: String = this::class.java.simpleName

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateMethod() {
        Log.i(TAG, "onCreate生命周期函数执行了...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyMethod() {
        Log.i(TAG, "onDestroy生命周期函数执行了...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPauseMethod() {
        Log.i(TAG, "onPause生命周期函数执行了...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumeMethod() {
        Log.i(TAG, "onResume生命周期函数执行了...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartMethod() {
        Log.i(TAG, "onStart生命周期函数执行了...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopMethod() {
        Log.i(TAG, "onStop生命周期函数执行了...")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAnyMethod() {
//        Log.i(TAG, "onAny")
    }
}