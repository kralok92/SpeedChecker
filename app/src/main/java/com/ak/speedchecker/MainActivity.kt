package com.ak.speedchecker

import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.ak.speedchecker.databinding.LayoutCircleBinding
import com.ak.speedchecker.speedBuilder.InternetSpeedBuilder
import com.ak.speedchecker.speedBuilder.ProgressionModel
import com.github.anastr.speedviewlib.SpeedView
import java.math.BigDecimal
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    private lateinit var binding : LayoutCircleBinding

    var speedView: SpeedView? = null
    var position = 0
    var lastPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutCircleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val builder = InternetSpeedBuilder(this@MainActivity)
        builder.setOnEventInternetSpeedListener(object : InternetSpeedBuilder.OnEventInternetSpeedListener {
            override fun onDownloadProgress(count: Int, progressModel: ProgressionModel) {

                Log.d("SERVER", "" + progressModel.downloadSpeed)
                //double speed = progressModel.getUploadSpeed()/((Double)1000000);
                val bigDecimal = BigDecimal("" + progressModel.downloadSpeed)
                val finalDownload = (bigDecimal.toLong() / 1000000).toFloat()
                Log.d("NET_SPEED", "" + (bigDecimal.toLong() / 1000000).toFloat())
                val bd = progressModel.downloadSpeed
                val d = bd.toDouble()
                Log.d("SHOW_SPEED", "" + formatFileSize(d))
                Log.d("ANGLE", "" + getPositionByRate(finalDownload))
                position = getPositionByRate(finalDownload)
                runOnUiThread {
                    val rotateAnimation: RotateAnimation
                    rotateAnimation = RotateAnimation(
                        lastPosition.toFloat(),
                        position.toFloat(),
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                    )
                    rotateAnimation.interpolator = LinearInterpolator()
                    rotateAnimation.duration = 500
                    binding.barImageView.startAnimation(rotateAnimation)
                    binding.download.setText(
                        "Download Speed: " + formatFileSize(
                            d
                        )
                    )
                }
                lastPosition = position



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
                position = getPositionByRate(totalassumtionSpeed)
                runOnUiThread { /*
                            RotateAnimation rotateAnimation;
                            rotateAnimation = new RotateAnimation(lastPosition, position, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setInterpolator(new LinearInterpolator());
                            rotateAnimation.setDuration(500);
                            barImage.startAnimation(rotateAnimation);
                            */
                    binding.barImageView.setRotation(position.toFloat())
                    binding.totalSpeed.setText(
                        "Total Speed: " + formatFileSize(
                            totalSpeedCount
                        )
                    )
                }
                lastPosition = position



            }

            override fun onUploadProgress(count: Int, progressModel: ProgressionModel) {

                //double speed = progressModel.getUploadSpeed()/((Double)1000000);
                val bigDecimal = BigDecimal("" + progressModel.uploadSpeed)
                val finalDownload = (bigDecimal.toLong() / 1000000).toFloat()
                Log.d("NET_SPEED", "" + (bigDecimal.toLong() / 1000000).toFloat())
                val bd = progressModel.uploadSpeed
                val d = bd.toDouble()
                Log.d("SHOW_SPEED", "" + formatFileSize(d))
                Log.d("ANGLE", "" + getPositionByRate(finalDownload))
                position = getPositionByRate(finalDownload)
                runOnUiThread {
                    val rotateAnimation: RotateAnimation
                    rotateAnimation = RotateAnimation(
                        lastPosition.toFloat(),
                        position.toFloat(),
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                    )
                    rotateAnimation.interpolator = LinearInterpolator()
                    rotateAnimation.duration = 500
                    binding.barImageView.startAnimation(rotateAnimation)
                    binding.uplaod.setText("Upload Speed: " + formatFileSize(d))
                }
                lastPosition = position


            }

        })
        builder.start("https://storage.googleapis.com/yogpath_vendor_data/vid_6/20220524_ecd67108040.PNG", 1)
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
                dec.format(m)
            }
            k > 1 -> {
                dec.format(k)
            }
            else -> {
                dec.format(size)
            }
        }
        return hrSize
    }

    fun getPositionByRate(rate: Float): Int {
        if (rate <= 1) {
            return (rate * 30).toInt()
        } else if (rate <= 10) {
            return (rate * 6).toInt() + 30
        } else if (rate <= 30) {
            return ((rate - 10) * 3).toInt() + 90
        } else if (rate <= 50) {
            return ((rate - 30) * 1.5).toInt() + 150
        } else if (rate <= 100) {
            return ((rate - 50) * 1.2).toInt() + 180
        }
        return 0
    }


}