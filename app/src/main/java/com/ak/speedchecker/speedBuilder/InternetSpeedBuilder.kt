package com.ak.speedchecker.speedBuilder

import android.app.Activity
import fr.bmartel.speedtest.SpeedTestReport
import fr.bmartel.speedtest.SpeedTestSocket
import fr.bmartel.speedtest.inter.ISpeedTestListener
import fr.bmartel.speedtest.model.SpeedTestError
import java.util.concurrent.Executors


class InternetSpeedBuilder(var activity: Activity) {

    private var countSpeed = 0
    private var LIMIT = 3
    lateinit var url: String
    lateinit var listener: OnEventInternetSpeedListener
    lateinit var onDownloadProgressListener: ()->Unit
    lateinit var onUploadProgressListener: ()->Unit
    lateinit var onTotalProgressListener: ()->Unit
    private lateinit var progressModel: ProgressionModel

    fun start(url: String, limitCount: Int) {
        this.url = url
        this.LIMIT = limitCount
        startDownload()
    }

    fun setOnEventInternetSpeedListener(listener: OnEventInternetSpeedListener) {
        this.listener = listener
    }

    fun setOnEventInternetSpeedListener(onDownloadProgress: ()->Unit, onUploadProgress: ()->Unit, onTotalProgress: ()->Unit) {
        this.onDownloadProgressListener = onDownloadProgress
        this.onUploadProgressListener = onUploadProgress
        this.onTotalProgressListener = onTotalProgress
    }


    private fun startDownload() {
        progressModel = ProgressionModel()
        speedDownloadTask()
    }

    private fun startUpload() {
        speedUploadTask()

    }

    interface OnEventInternetSpeedListener {
        fun onDownloadProgress(count: Int, progressModel: ProgressionModel)
        fun onUploadProgress(count: Int, progressModel: ProgressionModel)
        fun onTotalProgress(count: Int, progressModel: ProgressionModel)
    }

    private fun speedDownloadTask(){

        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val speedTestSocket = SpeedTestSocket()
            speedTestSocket.startDownload(url)
            speedTestSocket.addSpeedTestListener(object : ISpeedTestListener {

                override fun onCompletion(report: SpeedTestReport) {
                    startUpload()
                }

                override fun onError(speedTestError: SpeedTestError, errorMessage: String) {
                }
                override fun onProgress(percent: Float, report: SpeedTestReport) {
                    progressModel.progressTotal = percent / 2
                    progressModel.progressDownload = percent
                    progressModel.downloadSpeed = report.transferRateBit
                    activity.runOnUiThread {
                        listener.onDownloadProgress(countSpeed, progressModel)
                        listener.onTotalProgress(countSpeed, progressModel)
                    }

                }
            })
        }


    }


    private fun speedUploadTask(){
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {

            val speedTestSocket = SpeedTestSocket()
            speedTestSocket.addSpeedTestListener(object : ISpeedTestListener {

                override fun onCompletion(report: SpeedTestReport) {
                    countSpeed++
                    if (countSpeed < LIMIT) {
                        startDownload()
                    }
                }

                override fun onError(speedTestError: SpeedTestError, errorMessage: String) {
                }

                override fun onProgress(percent: Float, report: SpeedTestReport) {
                    progressModel.progressTotal = percent / 2 + 50
                    progressModel.progressUpload = percent
                    progressModel.uploadSpeed= report.transferRateBit

                    activity.runOnUiThread {

                        if (countSpeed < LIMIT) {
                            listener.onUploadProgress(countSpeed, progressModel)
                            listener.onTotalProgress(countSpeed, progressModel)

                        }
                    }

                }
            })

            speedTestSocket.startDownload(url)


        }


    }


}