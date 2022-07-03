package com.ak.speedchecker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.ak.speedchecker.databinding.LayoutCircleBinding
import com.ak.speedchecker.speedBuilder.InternetSpeedBuilder
import com.ak.speedchecker.speedBuilder.ProgressionModel
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ak.speedchecker.viewModel.SpeedViewModel
import com.ak.speedchecker.worker.PeriodicTimeWorker
import com.github.anastr.speedviewlib.SpeedView
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel : SpeedViewModel by viewModels()
    private lateinit var binding : LayoutCircleBinding
    private lateinit var mCtx:Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.layout_circle)
        binding.lifecycleOwner = this
        binding.data = viewModel
        mCtx = this
        viewModel.getSpeed(this)
    }







}