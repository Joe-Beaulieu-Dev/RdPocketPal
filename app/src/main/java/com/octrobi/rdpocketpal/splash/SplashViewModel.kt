package com.octrobi.rdpocketpal.splash

import android.app.Application
import androidx.lifecycle.*
import com.octrobi.rdpocketpal.BuildConfig
import com.octrobi.rdpocketpal.R

class SplashViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    val mVersionNameStr = MutableLiveData<String>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        mVersionNameStr.value = buildVersionNameStr()
    }

    private fun buildVersionNameStr(): String {
        val versionName = BuildConfig.VERSION_NAME
        val versionLabel = getApplication<Application>()
                .applicationContext.getString(R.string.text_version)

        return "$versionLabel $versionName"
    }
}