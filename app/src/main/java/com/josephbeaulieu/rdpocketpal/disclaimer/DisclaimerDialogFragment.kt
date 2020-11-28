package com.josephbeaulieu.rdpocketpal.disclaimer

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.josephbeaulieu.rdpocketpal.R
import com.josephbeaulieu.rdpocketpal.model.PreferenceRepository


class DisclaimerDialogFragment : DialogFragment() {

    private var mContext: Context? = null

    companion object {
        private const val TAG = "DisclaimerDialogFragment"

        fun newInstance(): DisclaimerDialogFragment {
            val frag = DisclaimerDialogFragment()
            frag.isCancelable = false
            return frag
        }

        fun getTag(): String {
            return TAG
        }
    }

    override fun onStart() {
        super.onStart()

        // make disclaimer take up the entire screen
        val dialog: Dialog? = dialog
        if (dialog != null) {
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        mContext = null
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater
                              , container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_disclaimer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners(view)
    }

    private fun setUpClickListeners(view: View) {
        view.findViewById<Button>(R.id.disc_dialog_continue).setOnClickListener { onAgreeClicked() }
        view.findViewById<Button>(R.id.disc_dialog_exit).setOnClickListener { onDeclineClicked() }
    }

    private fun onAgreeClicked() {
        // set the flag for the User passing through the Disclaimer into the app
        if (mContext != null) {
            val repo = PreferenceRepository()
            repo.setIfDisclaimerAccepted(mContext!!, true)
        }
        dismiss()
    }

    private fun onDeclineClicked() {
        // kill app
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> activity?.finishAndRemoveTask()
            else -> activity?.finishAffinity()
        }
    }
}