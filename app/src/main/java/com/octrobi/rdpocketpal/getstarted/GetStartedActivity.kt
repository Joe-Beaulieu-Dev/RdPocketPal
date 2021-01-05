package com.octrobi.rdpocketpal.getstarted

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.disclaimer.DisclaimerDialogFragment
import com.octrobi.rdpocketpal.home.HomeActivity
import com.octrobi.rdpocketpal.util.setUpBtnRippleRectangle

class GetStartedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        linkifyDisclaimerText()
        setUpBtnRipples()
    }

    fun onContinueClicked(btn: View) {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun linkifyDisclaimerText() {
        val textView = findViewById<TextView>(R.id.start_read_and_understand)
        val str = SpannableString(textView.text)

        val linkSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                showDisclaimer()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }

        str.setSpan(linkSpan, 79, 89, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = str
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = Color.TRANSPARENT
    }

    private fun showDisclaimer() {
        DisclaimerDialogFragment.newInstance()
                .show(supportFragmentManager, DisclaimerDialogFragment.getTag())
    }

    private fun setUpBtnRipples() {
        setUpBtnRippleRectangle(resources, theme, findViewById(R.id.start_continue))
    }
}