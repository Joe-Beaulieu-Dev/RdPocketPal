package com.example.rdpocketpal2.util

import androidx.lifecycle.MutableLiveData

data class FieldErrorPair(val data: MutableLiveData<String>, val error: MutableLiveData<String>)