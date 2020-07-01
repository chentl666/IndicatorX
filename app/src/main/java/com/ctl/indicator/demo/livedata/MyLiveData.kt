package com.ctl.indicator.demo.livedata

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * created by : chentl
 * Date: 2020/07/01
 */
class MyLiveData<T> : MutableLiveData<T>() {

    private val TAG: String = this::class.java.simpleName

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        val liveDataClass: Class<LiveData<*>> = LiveData::class.java
        try {
            val mObserversField: Field = liveDataClass.getDeclaredField("mObservers")
            mObserversField.isAccessible = true

            val observers: Any = mObserversField.get(this) as Any
            val observersClass = observers.javaClass
            val methodGet: Method = observersClass.getDeclaredMethod("get", Object::class.java)
            methodGet.isAccessible = true

            val objectWrapperEntry: Any = methodGet.invoke(observers, observer) as Any
            var objectWrapper: Any? = null
            if (objectWrapperEntry is Map.Entry<*, *>) {
                objectWrapper = objectWrapperEntry.value!!
            }
            if (objectWrapper == null) {
                throw NullPointerException("ObjectWrapper can not be null")
            }
            val wrapperClass = objectWrapper.javaClass.superclass
            val mLastVersion: Field = wrapperClass?.getDeclaredField("mLastVersion") as Field
            mLastVersion.isAccessible = true

            val mVersion: Field = liveDataClass.getDeclaredField("mVersion")
            mVersion.isAccessible = true
            val mV: Any = mVersion.get(this) as Any
            mLastVersion.set(objectWrapper, mV)

            mObserversField.isAccessible = false
            methodGet.isAccessible = false
            mLastVersion.isAccessible = false
            mVersion.isAccessible = false

        } catch (e: Exception) {
            Log.e(TAG, "observer：HOOK发生异常：" + e.message)
            e.printStackTrace()
        }
    }
}