package com.example.android.politicalpreparedness.representative.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.representative.RepresentativeViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class RepresentativeViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepresentativeViewModel::class.java)){
            return RepresentativeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown Model Class")
    }
}