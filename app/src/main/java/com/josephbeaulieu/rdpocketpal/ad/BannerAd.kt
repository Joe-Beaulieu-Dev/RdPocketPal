package com.josephbeaulieu.rdpocketpal.ad

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.josephbeaulieu.rdpocketpal.BuildConfig

class BannerAd(val context: Context, val adContainerView: ViewGroup) : LifecycleObserver {
    private val adView = AdView(context)
    private lateinit var adSize: AdSize

    fun loadBanner() {
        adSize = getAdSize()
        adView.adUnitId = BuildConfig.HOME_BANNER_TEST_ID
        adView.adSize = adSize

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                // resize ad container
                val params = adContainerView.layoutParams as ConstraintLayout.LayoutParams
                params.height = adSize.getHeightInPixels(context)
                adContainerView.layoutParams = params

                // add ad to container and make visible
                adContainerView.removeAllViews()
                adContainerView.addView(adView)
                adContainerView.visibility = View.VISIBLE
            }
        }
    }

    private fun getAdSize(): AdSize {
        val display = checkApiAndGetDisplay()
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        val density = outMetrics.density
        val adWidthPixels = outMetrics.widthPixels.toFloat()
        val adWidth = (adWidthPixels / density).toInt()

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
    }

    private fun checkApiAndGetDisplay(): Display {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.display!!
        } else  {
            (context as AppCompatActivity).windowManager.defaultDisplay
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        adView.pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        adView.resume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        adView.destroy()
    }
}