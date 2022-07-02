package com.ak.speedchecker.speedBuilder

import java.math.BigDecimal

data class ProgressionModel(var progressTotal: Float = 0f,
                            var progressDownload: Float = 0f,
                            var progressUpload: Float = 0f,
                            var uploadSpeed: BigDecimal = BigDecimal(0),
                            var downloadSpeed: BigDecimal = BigDecimal(0)
)
