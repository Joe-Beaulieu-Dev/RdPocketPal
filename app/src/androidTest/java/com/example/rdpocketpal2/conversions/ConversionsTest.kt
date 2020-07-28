package com.example.rdpocketpal2.conversions

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.testutil.EMPTY_STRING
import com.example.rdpocketpal2.testutil.INVALID_ENTRY_NOT_A_NUMBER
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
// mg to meq input
const val MG_TO_MEQ_MILLIGRAMS: String = "10000"
const val MG_TO_MEQ_CALCIUM_MEQ_INPUT: String = "499.0268975498"
const val MG_TO_MEQ_CHLORINE_MEQ_INPUT: String = "282.0874471086"
const val MG_TO_MEQ_MAGNESIUM_MEQ_INPUT: String = "822.8759514503"
const val MG_TO_MEQ_PHOSPHORUS_MEQ_INPUT: String = "968.5542713243"
const val MG_TO_MEQ_POTASSIUM_MEQ_INPUT: String = "255.7675584429"
const val MG_TO_MEQ_SODIUM_MEQ_INPUT: String = "434.9717268378"
// mg to meq output
// values rounded to 5 decimal places. Conversions screen locked to Rounding 5 for preferences
const val MG_TO_MEQ_CALCIUM_MEQ_OUTPUT: String = "499.0269"
const val MG_TO_MEQ_CHLORINE_MEQ_OUTPUT: String = "282.08745"
const val MG_TO_MEQ_MAGNESIUM_MEQ_OUTPUT: String = "822.87595"
const val MG_TO_MEQ_PHOSPHORUS_MEQ_OUTPUT: String = "968.55427"
const val MG_TO_MEQ_POTASSIUM_MEQ_OUTPUT: String = "255.76756"
const val MG_TO_MEQ_SODIUM_MEQ_OUTPUT: String = "434.97173"
//endregion

@RunWith(AndroidJUnit4::class)
@LargeTest
class ConversionsTest {

    @get:Rule
    val activityRule: ActivityTestRule<ConversionActivity> =
            ActivityTestRule(ConversionActivity::class.java)

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
    fun mgToMeq_calcium_leftToRight_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectCalcium(activityRule)
                enterMilligrams(MG_TO_MEQ_MILLIGRAMS)
                checkMilliequivalents(MG_TO_MEQ_CALCIUM_MEQ_OUTPUT)
            }
        }
    }

    @Test
    fun mgToMeq_calcium_rightToLeft_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectCalcium(activityRule)
                enterMilliequivalents(MG_TO_MEQ_CALCIUM_MEQ_INPUT)
                checkMilligrams(MG_TO_MEQ_MILLIGRAMS)
            }
        }
    }

    @Test
    fun mgToMeq_chlorine_leftToRight_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectChlorine(activityRule)
                enterMilligrams(MG_TO_MEQ_MILLIGRAMS)
                checkMilliequivalents(MG_TO_MEQ_CHLORINE_MEQ_OUTPUT)
            }
        }
    }

    @Test
    fun mgToMeq_chlorine_rightToLeft_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectChlorine(activityRule)
                enterMilliequivalents(MG_TO_MEQ_CHLORINE_MEQ_INPUT)
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
                enterMilligrams(MG_TO_MEQ_MILLIGRAMS)
                checkMilliequivalents(MG_TO_MEQ_MAGNESIUM_MEQ_OUTPUT)
            }
        }
    }

    @Test
    fun mgToMeq_magnesium_rightToLeft_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectMagnesium(activityRule)
                enterMilliequivalents(MG_TO_MEQ_MAGNESIUM_MEQ_INPUT)
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
                enterMilligrams(MG_TO_MEQ_MILLIGRAMS)
                checkMilliequivalents(MG_TO_MEQ_PHOSPHORUS_MEQ_OUTPUT)
            }
        }
    }

    @Test
    fun mgToMeq_phosphorus_rightToLeft_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectPhosphorus(activityRule)
                enterMilliequivalents(MG_TO_MEQ_PHOSPHORUS_MEQ_INPUT)
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
                enterMilligrams(MG_TO_MEQ_MILLIGRAMS)
                checkMilliequivalents(MG_TO_MEQ_POTASSIUM_MEQ_OUTPUT)
            }
        }
    }

    @Test
    fun mgToMeq_potassium_rightToLeft_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectPotassium(activityRule)
                enterMilliequivalents(MG_TO_MEQ_POTASSIUM_MEQ_INPUT)
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
                enterMilligrams(MG_TO_MEQ_MILLIGRAMS)
                checkMilliequivalents(MG_TO_MEQ_SODIUM_MEQ_OUTPUT)
            }
        }
    }

    @Test
    fun mgToMeq_sodium_rightToLeft_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectSodium(activityRule)
                enterMilliequivalents(MG_TO_MEQ_SODIUM_MEQ_INPUT)
                checkMilligrams(MG_TO_MEQ_MILLIGRAMS)
            }
        }
    }
    //endregion

    //region Clear fields
    @Test
    fun clearFields_clearBtn() {
        withConversionsRobot {
            inInCm {
                enterInches(IN_TO_CM_INCHES)
                checkCentimeters(IN_TO_CM_CENTIMETERS)
            }
            clickClear()
            inInCm {
                checkInches(EMPTY_STRING)
                checkCentimeters(EMPTY_STRING)
            }
        }
    }

    @Test
    fun clearFields_conversionTypeSpinnerSelection() {
        withConversionsRobot {
            inInCm {
                navigateToInCm(activityRule)
                enterInches(IN_TO_CM_INCHES)
                checkCentimeters(IN_TO_CM_CENTIMETERS)
            }
            inLbKg {
                navigateToLbKg(activityRule)
                checkPounds(EMPTY_STRING)
                checkKilograms(EMPTY_STRING)
            }
        }
    }

    @Test
    fun clearFields_elementSpinnerSelection() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectChlorine(activityRule)
                enterGrams(GM_TO_MEQ_GRAMS)
                checkMilliequivalents(GM_TO_MEQ_CHLORINE_MEQ)
            }
            selectRandomElement(activityRule)
            inGmMeq {
                checkGrams(EMPTY_STRING)
                checkMilliequivalents(EMPTY_STRING)
            }
        }
    }
    //endregion

    //region Errors
    @Test
    fun checkError_leftField_notANumber_displays() {
        withConversionsRobot {
            inInCm {
                navigateToInCm(activityRule)
                enterInches(INVALID_ENTRY_NOT_A_NUMBER)
            }
            checkLeftFieldNanError(activityRule)
        }
    }

    @Test
    fun checkError_rightField_notANumber_displays() {
        withConversionsRobot {
            inInCm {
                navigateToInCm(activityRule)
                enterCentimeters(INVALID_ENTRY_NOT_A_NUMBER)
            }
            checkRightFieldNanError(activityRule)
        }
    }

    @Test
    fun checkError_leftField_noError_displays() {
        withConversionsRobot {
            inInCm {
                navigateToInCm(activityRule)
                enterInches(IN_TO_CM_INCHES)
            }
            checkLeftFieldNoError()
        }
    }

    @Test
    fun checkError_rightField_noError_displays() {
        withConversionsRobot {
            inInCm {
                navigateToInCm(activityRule)
                enterCentimeters(IN_TO_CM_CENTIMETERS)
            }
            checkRightFieldNoError()
        }
    }
    //endregion

    //region Field persistence
    @Test
    fun orientationChange_fieldPersistence() {
        withConversionsRobot {
            // input
            inInCm {
                enterInches(IN_TO_CM_INCHES)
            }
            // rotate screen
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            // validate field persistence
            inInCm {
                checkInches(IN_TO_CM_INCHES)
                checkCentimeters(IN_TO_CM_CENTIMETERS)
            }
        }
    }
    //endregion
}