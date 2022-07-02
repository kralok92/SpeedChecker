package com.ak.speedchecker.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class PeriodicTimeWorker(private val mCtx: Context,
                         private val params: WorkerParameters) : Worker(mCtx, params) {


    override fun doWork(): Result {

    }


}