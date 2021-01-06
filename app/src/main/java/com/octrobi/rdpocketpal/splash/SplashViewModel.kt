package com.octrobi.rdpocketpal.splash

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.octrobi.rdpocketpal.BuildConfig
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.model.PreferenceRepository
import com.octrobi.rdpocketpal.model.QueryResult
import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    val mVersionNameStr = MutableLiveData<String>()
    private val mDisclaimerAccepted = MutableLiveData<Boolean>()
    private var mApplicationContext: Context = application.applicationContext

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        mVersionNameStr.value = buildVersionNameStr()
        queryIfDisclaimerAccepted()
    }

    private fun buildVersionNameStr(): String {
        val versionName = BuildConfig.VERSION_NAME
        val versionLabel = getApplication<Application>()
                .applicationContext.getString(R.string.text_version)

        return "$versionLabel $versionName"
    }

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
}