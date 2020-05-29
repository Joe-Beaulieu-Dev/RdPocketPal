@file:JvmName("ConstantsKotlin")

package com.example.rdpocketpal2.util

import androidx.annotation.IntDef

@Retention(AnnotationRetention.SOURCE)
@IntDef(METRIC, STANDARD)
annotation class Unit
const val METRIC = 0
const val STANDARD = 1