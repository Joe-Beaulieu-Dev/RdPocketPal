package com.octrobi.rdpocketpal.home

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.*
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.model.PreferenceRepository
import com.octrobi.rdpocketpal.model.QueryResult
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application)
        , LifecycleObserver, SharedPreferences.OnSharedPreferenceChangeListener {

    private val mDisclaimerAccepted = MutableLiveData<Boolean>()
    private var mApplicationContext: Context = application.applicationContext

    private fun queryIfDisclaimerAccepted() {
        viewModelScope.launch {
            when (val pref = PreferenceRepository().getIfDisclaimerAccepted(getApplication())) {
                is QueryResult.Success<Boolean> -> {
                    mDisclaimerAccepted.value = pref.data
                }
                else -> {
                    // If there is an issue, set the value to false and return false. The Disclaimer
                    // should always be shown if there is any question as to if the User has
                    // accepted it.
                    PreferenceRepository().setIfDisclaimerAccepted(mApplicationContext, false)
                    mDisclaimerAccepted.value = false
                }
            }
        }
    }

    fun getIfDisclaimerAccepted(): LiveData<Boolean> {
        return mDisclaimerAccepted
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        queryIfDisclaimerAccepted()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == mApplicationContext.getString(R.string.key_disclaimer_accepted)) {
            queryIfDisclaimerAccepted()
        }
    }
}