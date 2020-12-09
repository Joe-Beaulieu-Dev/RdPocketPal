package com.octrobi.rdpocketpal.conversions

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.octrobi.rdpocketpal.settings.withSettingsRobot
import com.octrobi.rdpocketpal.testutil.EMPTY_STRING
import com.octrobi.rdpocketpal.testutil.INVALID_ENTRY_NOT_A_NUMBER
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//region Test data
// in to cm
private const val IN_TO_CM_INCHES = "1"
private const val IN_TO_CM_CENTIMETERS = "2.54"

// lb to kg
private const val LB_TO_KG_POUNDS = "1"
private const val LB_TO_GK_R2L_POUNDS_OUTPUT = "0.99999" // 6th decimal is a 4 so it doesn't round to 1
private const val LB_TO_KG_KILOGRAMS = "0.45359"
//endregion

@RunWith(AndroidJUnit4::class)
@LargeTest
class ConversionsTest {

    @get:Rule
    val activityRule: ActivityTestRule<ConversionActivity> =
            ActivityTestRule(ConversionActivity::class.java)

    //region Visibility
    @Test
    fun settingsNote_isVisible() {
        withConversionsRobot {
            checkSettingsNoteIsShowing()
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            checkSettingsNoteIsShowing()
        }
    }
    //endregion

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