package com.ak.speedchecker.worker

import android.app.Activity
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ak.speedchecker.speedBuilder.InternetSpeedBuilder

class PeriodicTimeWorker(private val mCtx: Context,
                         private val params: WorkerParameters) : Worker(mCtx, params) {


    override fun doWork(): Result {
        val builder = InternetSpeedBuilder(mCtx as Activity)
        builder.start("https://storage.googleapis.com/yogpath_vendor_data/vid_6/20220524_ecd67108040.PNG", 1)
        return Result.success()
    }


}