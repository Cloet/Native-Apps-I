package com.example.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.watchlist.model.SavedSerie
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object TestUtils {


    /**
     * Used to check the value inside a LiveData object
     * (c) https://github.com/hdeweirdt/TVShows/blob/foreign_keys/app/src/androidTest/java/com/example/hwei214/tvshows/TestUtils.kt
     */
    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T?) {
                data[0] = t
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        return data[0] as T
    }

    fun createMockSeries(): List<SavedSerie> {
        val series = ArrayList<SavedSerie>()

        series.add(SavedSerie("1","Test serie 1", "This is an overview", "Test-Series","Continuing","","Disney+","",1.5F))
        series.add(SavedSerie("2","Test serie 2", "This is an overview", "Test-Series","Continuing","","Disney+","",5F))
        series.add(SavedSerie("3","Test serie 3", "This is an overview", "Test-Series","Continuing","","Disney+","",3.5F))

        return series
    }

}