package com.ak.speedchecker.viewModel

import android.app.Activity
import android.content.Context
import android.text.TextUtils.concat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ak.speedchecker.repository.SpeedRepository
import com.ak.speedchecker.speedBuilder.InternetSpeedBuilder
import com.ak.speedchecker.speedBuilder.ProgressionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class SpeedViewModel@Inject constructor(private val repository: SpeedRepository) : ViewModel(){

    /**
     * download speed
     */
    private val _downloadLiveData = MutableLiveData<String>()
    val downloadLiveData : LiveData<String>
     get() = _downloadLiveData

    /**
     * upload speed
     */
    private val _uploadLiveData = MutableLiveData<String>()
    val uploadLiveData : LiveData<String>
        get() = _uploadLiveData

    /**
     * total speed
     */
    private val _totalLiveData = MutableLiveData<String>()
    val totalLiveData : LiveData<String>
        get() = _totalLiveData

    fun getSpeed(mCtx:Context) = viewModelScope.launch {
        val builder = InternetSpeedBuilder(mCtx as Activity)
        builder.start("https://storage.googleapis.com/yogpath_vendor_data/vid_6/20220524_ecd67108040.PNG", 1)
        builder.setOnEventInternetSpeedListener(object :InternetSpeedBuilder.OnEventInternetSpeedListener{
            override fun onDownloadProgress(count: Int, progressModel: ProgressionModel) {


                val bigDecimal = BigDecimal("" + progressModel.downloadSpeed)
                val finalDownload = (bigDecimal.toLong() / 1000000).toFloat()
                val bd = progressModel.downloadSpeed
                val d = bd.toDouble()
                val download = "Download Speed: " + formatFileSize(d)
                _downloadLiveData.postValue(download)

            }

            override fun onUploadProgress(count: Int, progressModel: ProgressionModel) {
                //double speed = progressModel.getUploadSpeed()/((Double)1000000);
                val bigDecimal = BigDecimal("" + progressModel.uploadSpeed)
                val finalDownload = (bigDecimal.toLong() / 1000000).toFloat()
                val bd = progressModel.uploadSpeed
                val d = bd.toDouble()
                val upload = "Upload Speed: " + formatFileSize(d)
                _uploadLiveData.postValue(upload)
            }

            override fun onTotalProgress(count: Int, progressModel: ProgressionModel) {
                val downloadDecimal = progressModel.downloadSpeed
                val downloadFinal = downloadDecimal.toDouble()
                val uploadDecimal = progressModel.uploadSpeed
                val uploadFinal = uploadDecimal.toDouble()
                val totalSpeedCount = (downloadFinal + uploadFinal) / 2
                val finalDownload = (downloadDecimal.toLong() / 1000000).toFloat()
                val finalUpload = (uploadDecimal.toLong() / 1000000).toFloat()
                val totalassumtionSpeed = (finalDownload + finalUpload) / 2
                val total = "Total Speed: " + formatFileSize(totalSpeedCount)
                _totalLiveData.postValue(total)
            }

        })
    }


    fun formatFileSize(size: Double): String {
        val hrSize: String
        val k = size / 1024.0
        val m = size / 1024.0 / 1024.0
        val g = size / 1024.0 / 1024.0 / 1024.0
        val t = size / 1024.0 / 1024.0 / 1024.0 / 1024.0

        val dec  = DecimalFormat("0.00")
        hrSize = when {
            t > 1 -> {
                dec.format(t)
            }
            g > 1 -> {
                dec.format(g)
            }
            m > 1 -> {
                dec.format(m)+" mb/s"
            }
            k > 1 -> {
                dec.format(k)+" kb/s"
            }
            else -> {
                dec.format(size)
            }
        }
        return hrSize
    }




}