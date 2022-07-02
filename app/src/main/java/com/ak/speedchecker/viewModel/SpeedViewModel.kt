package com.ak.speedchecker.viewModel

import androidx.lifecycle.ViewModel
import com.ak.speedchecker.repository.SpeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpeedViewModel@Inject constructor(private val repository: SpeedRepository) : ViewModel(){




}