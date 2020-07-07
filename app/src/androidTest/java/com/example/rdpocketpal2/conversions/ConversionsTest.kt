package com.example.rdpocketpal2.conversions

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestUtil
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//region Test data
// in to cm
const val IN_TO_CM_INCHES: String = "1"
const val IN_TO_CM_CENTIMETERS: String = "2.54"
// lb to kg
const val LB_TO_KG_POUNDS: String = "1"
const val LB_TO_GK_R2L_POUNDS_OUTPUT = "0.99999" // 6th decimal is a 4 so it doesn't round to 1
const val LB_TO_KG_KILOGRAMS: String = "0.45359"
//gm to meq
const val GM_TO_MEQ_GRAMS: String = "10"
const val GM_TO_MEQ_CALCIUM_MEQ: String = "499.0269"
const val GM_TO_MEQ_CHLORINE_MEQ: String = "282.08745"
const val GM_TO_MEQ_MAGNESIUM_MEQ: String = "822.87595"
const val GM_TO_MEQ_PHOSPHORUS_MEQ: String = "968.55427"
const val GM_TO_MEQ_POTASSIUM_MEQ: String = "255.76756"
const val GM_TO_MEQ_SODIUM_MEQ: String = "434.97173"
// mg to meq
const val MG_TO_MEQ_MILLIGRAMS: String = "10000"
const val MG_TO_MEQ_CALCIUM_MEQ: String = "499.0269"
const val MG_TO_MEQ_CHLORINE_MEQ: String = "282.08745"
const val MG_TO_MEQ_MAGNESIUM_MEQ: String = "822.87595"
const val MG_TO_MEQ_PHOSPHORUS_MEQ: String = "968.55427"
const val MG_TO_MEQ_POTASSIUM_MEQ: String = "255.76756"
const val MG_TO_MEQ_SODIUM_MEQ: String = "434.97173"
//endregion

@RunWith(AndroidJUnit4::class)
@LargeTest
class ConversionsTest {

    @get:Rule
    val activityRule: ActivityTestRule<ConversionActivity> =
            ActivityTestRule(ConversionActivity::class.java)

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            // set preferences
            TestUtil.setDecimalReductionMethodPref(
                    InstrumentationRegistry.getInstrumentation().targetContext
                    , R.string.key_rounding)
            TestUtil.setNumericScalePref(
                    InstrumentationRegistry.getInstrumentation().targetContext, 2)
        }
    }

    //region in to cm
    @Test
    fun inToCm_leftToRight_isCorrect() {
        withConversionsRobot {
            inInCm {
                navigateToInCm(activityRule)
                enterInches(IN_TO_CM_INCHES)
                checkCentimeters(IN_TO_CM_CENTIMETERS)
            }
        }
    }

    @Test
    fun inToCm_rightToLeft_isCorrect() {
        withConversionsRobot {
            inInCm {
                navigateToInCm(activityRule)
                enterCentimeters(IN_TO_CM_CENTIMETERS)
                checkInches(IN_TO_CM_INCHES)
            }
        }
    }
    //endregion

    //region lb to kg
    @Test
    fun lbToKg_leftToRight_isCorrect() {
        withConversionsRobot {
            inLbKg {
                navigateToLbKg(activityRule)
                enterPounds(LB_TO_KG_POUNDS)
                checkKilograms(LB_TO_KG_KILOGRAMS)
            }
        }
    }

    @Test
    fun lbToKg_rightToLeft_isCorrect() {
        withConversionsRobot {
            inLbKg {
                navigateToLbKg(activityRule)
                enterKilograms(LB_TO_KG_KILOGRAMS)
                checkPounds(LB_TO_GK_R2L_POUNDS_OUTPUT)
            }
        }
    }
    //endregion

    //region lb to kg
    @Test
    fun gmToMeq_calcium_leftToRight_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectCalcium(activityRule)
                enterGrams(GM_TO_MEQ_GRAMS)
                checkMilliequivalents(GM_TO_MEQ_CALCIUM_MEQ)
            }
        }
    }

    @Test
    fun gmToMeq_calcium_rightToLeft_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectCalcium(activityRule)
                enterMilliequivalents(GM_TO_MEQ_CALCIUM_MEQ)
                checkGrams(GM_TO_MEQ_GRAMS)
            }
        }
    }
    //endregion

    //region gm to mEq
    @Test
    fun gmToMeq_chlorine_leftToRight_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectChlorine(activityRule)
                enterGrams(GM_TO_MEQ_GRAMS)
                checkMilliequivalents(GM_TO_MEQ_CHLORINE_MEQ)
            }
        }
    }

    @Test
    fun gmToMeq_chlorine_rightToLeft_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectChlorine(activityRule)
                enterMilliequivalents(GM_TO_MEQ_CHLORINE_MEQ)
                checkGrams(GM_TO_MEQ_GRAMS)
            }
        }
    }

    @Test
    fun gmToMeq_magnesium_leftToRight_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectMagnesium(activityRule)
                enterGrams(GM_TO_MEQ_GRAMS)
                checkMilliequivalents(GM_TO_MEQ_MAGNESIUM_MEQ)
            }
        }
    }

    @Test
    fun gmToMeq_magnesium_rightToLeft_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectMagnesium(activityRule)
                enterMilliequivalents(GM_TO_MEQ_MAGNESIUM_MEQ)
                checkGrams(GM_TO_MEQ_GRAMS)
            }
        }
    }

    @Test
    fun gmToMeq_phosphorus_leftToRight_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectPhosphorus(activityRule)
                enterGrams(GM_TO_MEQ_GRAMS)
                checkMilliequivalents(GM_TO_MEQ_PHOSPHORUS_MEQ)
            }
        }
    }

    @Test
    fun gmToMeq_phosphorus_rightToLeft_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectPhosphorus(activityRule)
                enterMilliequivalents(GM_TO_MEQ_PHOSPHORUS_MEQ)
                checkGrams(GM_TO_MEQ_GRAMS)
            }
        }
    }

    @Test
    fun gmToMeq_potassium_leftToRight_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectPotassium(activityRule)
                enterGrams(GM_TO_MEQ_GRAMS)
                checkMilliequivalents(GM_TO_MEQ_POTASSIUM_MEQ)
            }
        }
    }

    @Test
    fun gmToMeq_potassium_rightToLeft_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectPotassium(activityRule)
                enterMilliequivalents(GM_TO_MEQ_POTASSIUM_MEQ)
                checkGrams(GM_TO_MEQ_GRAMS)
            }
        }
    }

    @Test
    fun gmToMeq_sodium_leftToRight_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectSodium(activityRule)
                enterGrams(GM_TO_MEQ_GRAMS)
                checkMilliequivalents(GM_TO_MEQ_SODIUM_MEQ)
            }
        }
    }

    @Test
    fun gmToMeq_sodium_rightToLeft_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectSodium(activityRule)
                enterMilliequivalents(GM_TO_MEQ_SODIUM_MEQ)
                checkGrams(GM_TO_MEQ_GRAMS)
            }
        }
    }
    //endregion

    //region mg to mEq
    @Test
    fun mgToMeq_chlorine_leftToRight_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectChlorine(activityRule)
                checkMilligrams(MG_TO_MEQ_MILLIGRAMS)
                checkMilliequivalents(MG_TO_MEQ_CHLORINE_MEQ)
            }
        }
    }

    @Test
    fun mgToMeq_chlorine_rightToLeft_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectChlorine(activityRule)
                enterMilliequivalents(MG_TO_MEQ_CHLORINE_MEQ)
                checkMilligrams(MG_TO_MEQ_MILLIGRAMS)
            }
        }
    }

    @Test
    fun mgToMeq_magnesium_leftToRight_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectMagnesium(activityRule)
                checkMilligrams(MG_TO_MEQ_MILLIGRAMS)
                checkMilliequivalents(MG_TO_MEQ_MAGNESIUM_MEQ)
            }
        }
    }

    @Test
    fun mgToMeq_magnesium_rightToLeft_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectMagnesium(activityRule)
                enterMilliequivalents(MG_TO_MEQ_MAGNESIUM_MEQ)
                checkMilligrams(MG_TO_MEQ_MILLIGRAMS)
            }
        }
    }

    @Test
    fun mgToMeq_phosphorus_leftToRight_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectPhosphorus(activityRule)
                checkMilligrams(MG_TO_MEQ_MILLIGRAMS)
                checkMilliequivalents(MG_TO_MEQ_PHOSPHORUS_MEQ)
            }
        }
    }

    @Test
    fun mgToMeq_phosphorus_rightToLeft_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectPhosphorus(activityRule)
                enterMilliequivalents(MG_TO_MEQ_PHOSPHORUS_MEQ)
                checkMilligrams(MG_TO_MEQ_MILLIGRAMS)
            }
        }
    }

    @Test
    fun mgToMeq_potassium_leftToRight_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectPotassium(activityRule)
                checkMilligrams(MG_TO_MEQ_MILLIGRAMS)
                checkMilliequivalents(MG_TO_MEQ_POTASSIUM_MEQ)
            }
        }
    }

    @Test
    fun mgToMeq_potassium_rightToLeft_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectPotassium(activityRule)
                enterMilliequivalents(MG_TO_MEQ_POTASSIUM_MEQ)
                checkMilligrams(MG_TO_MEQ_MILLIGRAMS)
            }
        }
    }

    @Test
    fun mgToMeq_sodium_leftToRight_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectSodium(activityRule)
                checkMilligrams(MG_TO_MEQ_MILLIGRAMS)
                checkMilliequivalents(MG_TO_MEQ_SODIUM_MEQ)
            }
        }
    }

    @Test
    fun mgToMeq_sodium_rightToLeft_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectSodium(activityRule)
                enterMilliequivalents(MG_TO_MEQ_SODIUM_MEQ)
                checkMilligrams(MG_TO_MEQ_MILLIGRAMS)
            }
        }
    }
    //endregion
}