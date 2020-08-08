package com.example.rdpocketpal2.anthropometrics

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class AnthropometricsViewModel(application: Application) : AndroidViewModel(application) {

    //region LiveData
    val mSelectedSex =  MutableLiveData<String>()
    val mSelectedUnit =  MutableLiveData<String>()
    //endregion

    private var mApplicationContext: Context = application.applicationContext

}