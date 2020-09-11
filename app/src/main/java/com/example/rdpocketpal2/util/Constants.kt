@file:JvmName("Constants")

package com.example.rdpocketpal2.util

import androidx.annotation.IntDef

/*
 **************
 ** Defaults **
 **************
 */
const val DEFAULT_RADIO_BTN_INDEX = 0

/*
 *********
 ** Sex **
 *********
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(MALE, FEMALE)
annotation class Sex
const val MALE = 0
const val FEMALE = 1

sealed class SexK {
    object Male : SexK()
    object Female : SexK()
}

/*
 ************
 ** Units **
 ************
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(METRIC, STANDARD)
annotation class Unit
const val METRIC = 0
const val STANDARD = 1

sealed class UnitK {
    object Metric : UnitK()
    object Standard : UnitK()
}