package com.octrobi.rdpocketpal.adapter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import com.octrobi.rdpocketpal.R

// This class only exists because of this bug related to filters being triggered on screen rotation
// https://github.com/material-components/material-components-android/issues/1464
class NoFilterAdapter<T>(context: Context, items: List<T>)
    : ArrayAdapter<T>(context, R.layout.spinner_item, items) {

    private val noOpFilter = object : Filter() {
        private val noOpResult = FilterResults()
        override fun performFiltering(constraint: CharSequence?) = noOpResult
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {}
    }

    override fun getFilter() = noOpFilter
}