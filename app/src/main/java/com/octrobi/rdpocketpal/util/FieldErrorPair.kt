package com.octrobi.rdpocketpal.util

import androidx.lifecycle.MutableLiveData

data class FieldErrorPair(val data: MutableLiveData<String>, val error: MutableLiveData<String>)