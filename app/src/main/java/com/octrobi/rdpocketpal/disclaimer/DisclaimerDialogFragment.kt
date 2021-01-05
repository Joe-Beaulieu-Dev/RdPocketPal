package com.octrobi.rdpocketpal.disclaimer

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.octrobi.rdpocketpal.R


class DisclaimerDialogFragment : DialogFragment() {

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
        view.findViewById<Button>(R.id.disc_dialog_close).setOnClickListener { onCloseClicked() }
    }

    private fun onCloseClicked() {
        dismiss()
    }
}