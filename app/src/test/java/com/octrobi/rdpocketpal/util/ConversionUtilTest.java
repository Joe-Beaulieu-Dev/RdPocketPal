package com.octrobi.rdpocketpal.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConversionUtilTest {
    //region Test data
    // general values
    private static final double DELTA_SEVEN = 0.0000001;

    // pounds to kilograms values
    private static final double LB_TO_KG_INPUT = 100;
    private static final double LB_TO_KG_ANSWER = 45.359237;
    private static final double KG_TO_LB_INPUT = 45.359237;
    private static final double KG_TO_LB_ANSWER = 100;

    // inches to centimeters values
    private static final double IN_TO_CM_INPUT = 100;
    private static final double IN_TO_CM_ANSWER = 254;
    private static final double CM_TO_IN_INPUT = 254;
    private static final double CM_TO_IN_ANSWER = 100;

    // fahrenheit to celsius values
    private static final double FAHRENHEIT_TO_CELSIUS_INPUT = 212;
    private static final double FAHRENHEIT_TO_CELSIUS_ANSWER = 100;
    private static final double CELSIUS_TO_FAHRENHEIT_INPUT = 100;
    private static final double CELSIUS_TO_FAHRENHEIT_ANSWER = 212;

    // gallons to liters values
    private static final double GALLONS_TO_LITERS_INPUT = 100;
    private static final double GALLONS_TO_LITERS_ANSWER = 378.541;
    private static final double LITERS_TO_GALLONS_INPUT = 378.541;
    private static final double LITERS_TO_GALLONS_ANSWER = 100;
    //endregion

    //region Weight
    @Test
    public void poundsToKilograms_isCorrect() {
        assertEquals(LB_TO_KG_ANSWER
                , ConversionUtil.poundsToKilograms(LB_TO_KG_INPUT)
                , DELTA_SEVEN
        );
    }

    @Test
    public void kilogramsToPounds_isCorrect() {
        assertEquals(KG_TO_LB_ANSWER
                , ConversionUtil.kilogramsToPounds(KG_TO_LB_INPUT)
                , DELTA_SEVEN
        );
    }
    //endregion

    //region Length
    @Test
    public void inchesToCentimeters_isCorrect() {
        assertEquals(IN_TO_CM_ANSWER
                , ConversionUtil.inchesToCentimeters(IN_TO_CM_INPUT)
                , DELTA_SEVEN
        );
    }

    @Test
    public void centimetersToInches_isCorrect() {
        assertEquals(CM_TO_IN_ANSWER
                , ConversionUtil.centimetersToInches(CM_TO_IN_INPUT)
                , DELTA_SEVEN
        );
    }
    //endregion

    //region Temperature
    @Test
    public void fahrenheitToCelsius_isCorrect() {
        assertEquals(FAHRENHEIT_TO_CELSIUS_ANSWER
                , ConversionUtil.fahrenheitToCelsius(FAHRENHEIT_TO_CELSIUS_INPUT)
                , DELTA_SEVEN
        );
    }

    @Test
    public void celsiusToFahrenheit_isCorrect() {
        assertEquals(CELSIUS_TO_FAHRENHEIT_ANSWER
                , ConversionUtil.celsiusToFahrenheit(CELSIUS_TO_FAHRENHEIT_INPUT)
                , DELTA_SEVEN
        );
    }
    //endregion

    //region Fluid
    @Test
    public void gallonsToLiters_isCorrect() {
        assertEquals(GALLONS_TO_LITERS_ANSWER
                , ConversionUtil.gallonsToLiters(GALLONS_TO_LITERS_INPUT)
                , DELTA_SEVEN
        );
    }

    @Test
    public void litersToGallons_isCorrect() {
        assertEquals(LITERS_TO_GALLONS_ANSWER
                , ConversionUtil.litersToGallons(LITERS_TO_GALLONS_INPUT)
                , DELTA_SEVEN
        );
    }
    //endregion
}
