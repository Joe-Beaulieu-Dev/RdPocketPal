package com.example.rdpocketpal2.conversions

import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestRobot

class ConversionsRobot : TestRobot() {

    fun clickClear() {
        clickViewId(R.id.conv_clear_btn)
    }

    //region Robot builders
    fun inInCm(fn: InCmRobot.() -> Unit) = InCmRobot().apply(fn)

    fun inLbKg(fn: LbKgRobot.() -> Unit) = LbKgRobot().apply(fn)

    fun inGmMeq(fn: GmMeqRobot.() -> Unit) = GmMeqRobot().apply(fn)

    fun inMgMeq(fn: MgMeqRobot.() -> Unit) = MgMeqRobot().apply(fn)
    //endregion
}

fun withConversionsRobot(fn: ConversionsRobot.() -> Unit) = ConversionsRobot().apply(fn)