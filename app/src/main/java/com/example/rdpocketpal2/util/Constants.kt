@file:JvmName("ConstantsKotlin")

package com.example.rdpocketpal2.util

import androidx.annotation.IntDef

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

/*
 **************
 ** Elements **
 **************
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(CALCIUM, CHLORINE, MAGNESIUM, PHOSPHORUS, POTASSIUM, SODIUM)
annotation class Element
const val CALCIUM = 0
const val CHLORINE = 1
const val MAGNESIUM = 2
const val PHOSPHORUS = 3
const val POTASSIUM = 4
const val SODIUM = 5

// Atomic weights
const val CALCIUM_ATOMIC_WEIGHT = 40.078
const val CHLORINE_ATOMIC_WEIGHT = 35.45
const val MAGNESIUM_ATOMIC_WEIGHT = 24.305
const val PHOSPHORUS_ATOMIC_WEIGHT = 30.974
const val POTASSIUM_ATOMIC_WEIGHT = 39.098
const val SODIUM_ATOMIC_WEIGHT = 22.99

// Valences
const val CALCIUM_VALENCE = 2
const val CHLORINE_VALENCE = 1
const val MAGNESIUM_VALENCE = 2
// can be 3 or 5
const val PHOSPHORUS_VALENCE = 3
const val POTASSIUM_VALENCE = 1
const val SODIUM_VALENCE = 1