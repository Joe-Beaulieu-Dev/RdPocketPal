package com.octrobi.rdpocketpal.action

import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions

object CustomViewActions {

    @JvmStatic
    fun nestedScrollTo(): ViewAction {
        return ViewActions.actionWithAssertions(NestedScrollViewExt())
    }
}