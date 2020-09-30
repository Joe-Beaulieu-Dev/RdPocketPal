package com.josephbeaulieu.rdpocketpal.conversions

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.josephbeaulieu.rdpocketpal.settings.withSettingsRobot
import com.josephbeaulieu.rdpocketpal.testutil.EMPTY_STRING
import com.josephbeaulieu.rdpocketpal.testutil.INVALID_ENTRY_NOT_A_NUMBER
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//region Test data
// general
private const val TEN_GRAMS = "10"
private const val TEN_THOUSAND_MILLIGRAMS = "10000"

// in to cm
private const val IN_TO_CM_INCHES = "1"
private const val IN_TO_CM_CENTIMETERS = "2.54"

// lb to kg
private const val LB_TO_KG_POUNDS = "1"
private const val LB_TO_GK_R2L_POUNDS_OUTPUT = "0.99999" // 6th decimal is a 4 so it doesn't round to 1
private const val LB_TO_KG_KILOGRAMS = "0.45359"

// gm to meq
private const val GM_TO_MEQ_CALCIUM_MEQ = "499.0269"
private const val GM_TO_MEQ_CHLORINE_MEQ = "282.08745"
private const val GM_TO_MEQ_MAGNESIUM_MEQ = "822.87595"
private const val GM_TO_MEQ_POTASSIUM_MEQ = "255.76756"
private const val GM_TO_MEQ_SODIUM_MEQ = "434.97173"

// mg to meq input
private const val MG_TO_MEQ_CALCIUM_MEQ_INPUT = "499.0268975498"
private const val MG_TO_MEQ_CHLORINE_MEQ_INPUT = "282.0874471086"
private const val MG_TO_MEQ_MAGNESIUM_MEQ_INPUT = "822.8759514503"
private const val MG_TO_MEQ_POTASSIUM_MEQ_INPUT = "255.7675584429"
private const val MG_TO_MEQ_SODIUM_MEQ_INPUT = "434.9717268378"

// mg to meq output
// values rounded to 5 decimal places. Conversions screen locked to Rounding 5 for preferences
private const val MG_TO_MEQ_CALCIUM_MEQ_OUTPUT = "499.0269"
private const val MG_TO_MEQ_CHLORINE_MEQ_OUTPUT = "282.08745"
private const val MG_TO_MEQ_MAGNESIUM_MEQ_OUTPUT = "822.87595"
private const val MG_TO_MEQ_POTASSIUM_MEQ_OUTPUT = "255.76756"
private const val MG_TO_MEQ_SODIUM_MEQ_OUTPUT = "434.97173"

// gm to mmol
private const val GM_TO_MMOL_PHOSPHORUS_MMOL = "322.85142"

// mg to mmol input
private const val MG_TO_MMOL_PHOSPHORUS_MMOL_INPUT = "322.85142377477884677471427648996"

// mg to mmol output
private const val MG_TO_MMOL_PHOSPHORUS_MMOL_OUTPUT = "322.85142"
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

    //region gm to mEq
    @Test
    fun gmToMeq_calcium_leftToRight_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectCalcium(activityRule)
                enterGrams(TEN_GRAMS)
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
                checkGrams(TEN_GRAMS)
            }
        }
    }

    @Test
    fun gmToMeq_chlorine_leftToRight_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectChlorine(activityRule)
                enterGrams(TEN_GRAMS)
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
                checkGrams(TEN_GRAMS)
            }
        }
    }

    @Test
    fun gmToMeq_magnesium_leftToRight_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectMagnesium(activityRule)
                enterGrams(TEN_GRAMS)
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
                checkGrams(TEN_GRAMS)
            }
        }
    }

    @Test
    fun gmToMeq_potassium_leftToRight_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectPotassium(activityRule)
                enterGrams(TEN_GRAMS)
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
                checkGrams(TEN_GRAMS)
            }
        }
    }

    @Test
    fun gmToMeq_sodium_leftToRight_isCorrect() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectSodium(activityRule)
                enterGrams(TEN_GRAMS)
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
                checkGrams(TEN_GRAMS)
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
                enterMilligrams(TEN_THOUSAND_MILLIGRAMS)
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
                checkMilligrams(TEN_THOUSAND_MILLIGRAMS)
            }
        }
    }

    @Test
    fun mgToMeq_chlorine_leftToRight_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectChlorine(activityRule)
                enterMilligrams(TEN_THOUSAND_MILLIGRAMS)
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
                checkMilligrams(TEN_THOUSAND_MILLIGRAMS)
            }
        }
    }

    @Test
    fun mgToMeq_magnesium_leftToRight_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectMagnesium(activityRule)
                enterMilligrams(TEN_THOUSAND_MILLIGRAMS)
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
                checkMilligrams(TEN_THOUSAND_MILLIGRAMS)
            }
        }
    }

    @Test
    fun mgToMeq_potassium_leftToRight_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectPotassium(activityRule)
                enterMilligrams(TEN_THOUSAND_MILLIGRAMS)
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
                checkMilligrams(TEN_THOUSAND_MILLIGRAMS)
            }
        }
    }

    @Test
    fun mgToMeq_sodium_leftToRight_isCorrect() {
        withConversionsRobot {
            inMgMeq {
                navigateToMgMeq(activityRule)
                selectSodium(activityRule)
                enterMilligrams(TEN_THOUSAND_MILLIGRAMS)
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
                checkMilligrams(TEN_THOUSAND_MILLIGRAMS)
            }
        }
    }
    //endregion

    //region gm to mmol
    @Test
    fun gmToMmol_phosphorus_leftToRight_isCorrect() {
        withConversionsRobot {
            inGmMmol {
                navigateToGmMmol(activityRule)
                selectPhosphorus(activityRule)
                enterGrams(TEN_GRAMS)
                checkMillimoles(GM_TO_MMOL_PHOSPHORUS_MMOL)
            }
        }
    }

    @Test
    fun gmToMmol_phosphorus_rightToLeft_isCorrect() {
        withConversionsRobot {
            inGmMmol {
                navigateToGmMmol(activityRule)
                selectPhosphorus(activityRule)
                enterMillimoles(GM_TO_MMOL_PHOSPHORUS_MMOL)
                checkGrams(TEN_GRAMS)
            }
        }
    }
    //endregion

    //region mg to mmol
    @Test
    fun mgToMmol_phosphorus_leftToRight_isCorrect() {
        withConversionsRobot {
            inMgMmol {
                navigateToMgMmol(activityRule)
                selectPhosphorus(activityRule)
                enterMilligrams(TEN_THOUSAND_MILLIGRAMS)
                checkMillimoles(MG_TO_MMOL_PHOSPHORUS_MMOL_OUTPUT)
            }
        }
    }

    @Test
    fun mgToMmol_phosphorus_rightToLeft_isCorrect() {
        withConversionsRobot {
            inMgMmol {
                navigateToMgMmol(activityRule)
                selectPhosphorus(activityRule)
                enterMillimoles(MG_TO_MMOL_PHOSPHORUS_MMOL_INPUT)
                checkMilligrams(TEN_THOUSAND_MILLIGRAMS)
            }
        }
    }
    //endregion

    //region Clear fields
    @Test
    fun clearFields_clearBtn() {
        withConversionsRobot {
            inInCm {
                navigateToInCm(activityRule)
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
    fun clearFields_elementMeqSpinnerSelection() {
        withConversionsRobot {
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectChlorine(activityRule)
                enterGrams(TEN_GRAMS)
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
    fun fieldPersistence_orientationChange() {
        withConversionsRobot {
            // input
            inInCm {
                navigateToInCm(activityRule)
                enterInches(IN_TO_CM_INCHES)
                checkCentimeters(IN_TO_CM_CENTIMETERS)
            }
            // rotate screen
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            // validate field persistence
            inInCm {
                checkInches(IN_TO_CM_INCHES)
                checkCentimeters(IN_TO_CM_CENTIMETERS)
            }

            // input
            inGmMeq {
                navigateToGmMeq(activityRule)
                selectCalcium(activityRule)
                enterGrams(TEN_GRAMS)
                checkMilliequivalents(GM_TO_MEQ_CALCIUM_MEQ)
            }
            // rotate screen
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            // validate field persistence
            inGmMeq {
                checkGrams(TEN_GRAMS)
                checkMilliequivalents(GM_TO_MEQ_CALCIUM_MEQ)
            }

            // input
            inGmMmol {
                navigateToGmMmol(activityRule)
                selectPhosphorus(activityRule)
                enterGrams(TEN_GRAMS)
                checkMillimoles(GM_TO_MMOL_PHOSPHORUS_MMOL)
            }
            // rotate screen
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            // validate field persistence
            inGmMmol {
                checkGrams(TEN_GRAMS)
                checkMillimoles(GM_TO_MMOL_PHOSPHORUS_MMOL)
            }
        }
    }

    @Test
    fun fieldPersistence_repeatConversionTypeSpinnerSelection() {
        withConversionsRobot {
            inInCm {
                // entry
                navigateToInCm(activityRule)
                enterInches(IN_TO_CM_INCHES)
                checkCentimeters(IN_TO_CM_CENTIMETERS)
                navigateToInCm(activityRule)
                // validation
                checkInches(IN_TO_CM_INCHES)
                checkCentimeters(IN_TO_CM_CENTIMETERS)
            }
        }
    }

    @Test
    fun fieldPersistence_repeatElementMeqSpinnerSelection() {
        withConversionsRobot {
            inGmMeq {
                // entry
                navigateToGmMeq(activityRule)
                selectCalcium(activityRule)
                enterGrams(TEN_GRAMS)
                checkMilliequivalents(GM_TO_MEQ_CALCIUM_MEQ)
                selectCalcium(activityRule)
                // validation
                checkGrams(TEN_GRAMS)
                checkMilliequivalents(GM_TO_MEQ_CALCIUM_MEQ)
            }
        }
    }

    @Test
    fun fieldPersistence_repeatElementMmolSpinnerSelection() {
        withConversionsRobot {
            inGmMmol {
                // entry
                navigateToGmMmol(activityRule)
                selectPhosphorus(activityRule)
                enterGrams(TEN_GRAMS)
                checkMillimoles(GM_TO_MMOL_PHOSPHORUS_MMOL)
                selectPhosphorus(activityRule)
                // validation
                checkGrams(TEN_GRAMS)
                checkMillimoles(GM_TO_MMOL_PHOSPHORUS_MMOL)
            }
        }
    }
    //endregion

    //region Preferences
    @Test
    fun checkPreferences_areAccessible() {
        Intents.init()
        withConversionsRobot {
            openPreferences()
        }
        withSettingsRobot {
            checkSettingsActivityIsDisplayed()
        }
        Intents.release()
    }
    //endregion
}