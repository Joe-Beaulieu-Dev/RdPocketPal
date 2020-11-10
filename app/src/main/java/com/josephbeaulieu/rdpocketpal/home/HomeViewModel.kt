package com.josephbeaulieu.rdpocketpal.home

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.*
import com.josephbeaulieu.rdpocketpal.R
import com.josephbeaulieu.rdpocketpal.model.PreferenceRepository
import com.josephbeaulieu.rdpocketpal.model.QueryResult
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application)
        , LifecycleObserver, SharedPreferences.OnSharedPreferenceChangeListener {

    private val mDisclaimerAcceptedThisSession = MutableLiveData<Boolean>()
    private var mApplicationContext: Context = application.applicationContext

    private fun queryIfDisclaimerAcceptedThisSession() {
        viewModelScope.launch {
            when (val pref = PreferenceRepository().getDisclaimerAcceptedThisSession(getApplication())) {
                is QueryResult.Success<Boolean> -> {
                    mDisclaimerAcceptedThisSession.value = pref.data
                }
                else -> {
                    // If there is an issue, set the value to false and return false. The Disclaimer
                    // should always be shown if there is any question as to if the User has
                    // accepted it.
                    PreferenceRepository().setDisclaimerAcceptedThisSession(mApplicationContext, false)
                    mDisclaimerAcceptedThisSession.value = false
                }
            }
        }
    }

    fun getDisclaimerAcceptedThisSession(): LiveData<Boolean> {
        return mDisclaimerAcceptedThisSession
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        queryIfDisclaimerAcceptedThisSession()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == mApplicationContext.getString(R.string.key_disclaimer_accepted_this_session)) {
            queryIfDisclaimerAcceptedThisSession()
        }
    }
}