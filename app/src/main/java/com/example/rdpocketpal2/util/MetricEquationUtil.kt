package com.example.rdpocketpal2.util

import kotlin.math.pow

object MetricEquationUtil {

    //region Predictive Equations
    /**
     * Calculates BMR using the Mifflin-St. Jeor equation.
     *
     * @param sex    Male or Female
     * @param weight weight in kg
     * @param height height in cm
     * @param age    age in years
     * @return BMR in kcals
     */
    @JvmStatic
    fun calculateBmrMifflin(sex: Sex, weight: Double, height: Double, age: Int): Double {
        return when (sex) {
            Sex.MALE -> (10 * weight) + (6.25 * height) - (5 * age) + 5
            Sex.FEMALE -> (10 * weight) + (6.25 * height) - (5 * age) - 161
        }
    }

    /**
     * Calculates BMR using the Harris-Benedict equation.
     *
     * @param sex    Male or Female
     * @param weight weight in kg
     * @param height height in cm
     * @param age    age in years
     * @return BMR in kcals
     */
    @JvmStatic
    fun calculateBmrBenedict(sex: Sex, weight: Double, height: Double, age: Int): Double {
        return when (sex) {
            Sex.MALE -> 66 + (13.75 * weight) + (5 * height) - (6.8 * age)
            Sex.FEMALE -> 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age)
        }
    }

    /**
     * Calculates BMR using the Penn State 2003b equation.
     *
     * @param mifflinBmr BMR in kcals calculated via the Mifflin-St. Jeor equation
     * @param tmax       weight in Celsius
     * @param ve         Ve in L/min
     * @return BMR in kcals
     */
    @JvmStatic
    fun calculateBmrPennState2003b(mifflinBmr: Double, tmax: Double, ve: Double): Double {
        return (mifflinBmr * 0.96) + (tmax * 167) + (ve * 31) - 6212
    }

    /**
     * Calculates BMR using the Penn State 2010 equation.
     *
     * @param mifflinBmr BMR in kcals calculated via the Mifflin-St. Jeor equation
     * @param tmax       weight in Celsius
     * @param ve         Ve in L/min
     * @return BMR in kcals
     */
    @JvmStatic
    fun calculateBmrPennState2010(mifflinBmr: Double, tmax: Double, ve: Double): Double {
        return (mifflinBmr * 0.71) + (tmax * 85) + (ve * 64) - 3085
    }

    /**
     * Calculates BMR using the Penn State 2010 equation.
     *
     * @param benedictBmr BMR in kcals calculated via the Mifflin-St. Jeor equation
     * @param heartRate   heart rate in bpm
     * @param ve          Ve in L/min
     * @return BMR in kcals
     */
    @JvmStatic
    fun calculateBmrBrandi(benedictBmr: Double, heartRate: Double, ve: Double): Double {
        return (benedictBmr * 0.96) + (heartRate * 7) + (ve * 48) - 702
    }
    //endregion

    //region Quick Method
    /**
     * Used to calculate kcals/day, protein g/day, and fluid ml/day using the Quick Method.
     * The Quick Method simply takes takes a given [factor] (kcal/kg, g/kg, ml/kg where kg
     * represents kg of body weight) and multiplies it by a given weight in kg.
     *
     * @param weight weight in kg
     * @param factor the factor which will be multiplied by the given [weight]
     * @return kcals/day, protein g/day, or fluid ml/day
     */
    @JvmStatic
    fun calculateQuickMethod(weight: Double, factor: Double): Double {
        return weight * factor
    }
    //endregion

    //region Anthropometrics
    /**
     * Calculates a person's BMI.
     *
     * @param weight weight in kg
     * @param height height in cm
     * @return BMI in kg/m^2
     */
    @JvmStatic
    fun calculateBmi(weight: Double, height: Double): Double {
        // check for division by zero
        return if (height == 0.0) 0.0 else weight / (height / 100).pow(2.0)
    }

    /**
     * Calculates a person's BMI using the Hamwi equation.
     *
     * @param sex    Male or Female
     * @param height height in inches
     * @return IBW in lbs
     */
    @JvmStatic
    fun calculateIbwHamwi(sex: Sex, height: Double): Double {
        return when (sex) {
            Sex.MALE -> calculateIbwMale(height)
            Sex.FEMALE -> calculateIbwFemale(height)
        }
    }

    @JvmStatic
    private fun calculateIbwMale(height: Double): Double {
        return when {
            height >= 60 -> 106 + 6 * (height - 60)
            else -> 106 - (6 * (60 - height) / 2)
        }
    }

    @JvmStatic
    private fun calculateIbwFemale(height: Double): Double {
        return when {
            height >= 60 -> 100 + 5 * (height - 60)
            else -> 100 - (5 * (60 - height) / 2)
        }
    }

    /**
     * Given an IBW (kg or lbs) and a weight (kg or lbs) where both inputs share the same unit,
     * calculated the percentage the weight is of the IBW.
     *
     * @param ibw    IBW in kg or lbs (unit must match [weight])
     * @param weight weight in kg or lbs (unit must match [ibw])
     * @return the percentage the [weight] is of the [ibw]. Returns 0.0 if the given [ibw]
     *         is 0.0 to avoid division by zero
     */
    @JvmStatic
    fun calculatePercentIbw(ibw: Double, weight: Double): Double {
        // check to avoid division by zero
        return if (ibw == 0.0) 0.0 else (weight / ibw) * 100
    }

    /**
     * Calculates a person's Adjusted Body Weight. Used if a person's BMI > 30.
     *
     * @param ibw    IBW in kg or lbs (unit must match [weight])
     * @param weight weight in kg or lbs (unit must match [ibw])
     * @return Adjusted Body Weight in kg or lbs (based on input)
     */
    @JvmStatic
    fun calculateAdjustedIbw(ibw: Double, weight: Double): Double {
        return ((weight - ibw) / 4) + ibw
    }
    //endregion
}