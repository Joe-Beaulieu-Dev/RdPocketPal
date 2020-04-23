package com.example.rdpocketpal2.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConversionUtilTest {
    // general values
    private static final double DELTA = 0.000001;

    // pounds to kilograms values
    private static final double LB_TO_KG_INPUT = 100;
    private static final double LB_TO_KG_ANSWER = 45.359237;

    // inches to centimeters values
    private static final double IN_TO_CM_INPUT = 100;
    private static final double IN_TO_CM_ANSWER = 254;

    // fahrenheit to celsius values
    private static final double FAHRENHEIT_TO_CELSIUS_INPUT = 212;
    private static final double FAHRENHEIT_TO_CELSIUS_ANSWER = 100;

    // gallons to liters values
    private static final double GALLONS_TO_LITERS_INPUT = 100;
    private static final double GALLONS_TO_LITERS_ANSWER = 378.541;

    @Test
    public void poundsToKilograms_isCorrect() {
        assertEquals(LB_TO_KG_ANSWER
                , ConversionUtil.poundsToKilograms(LB_TO_KG_INPUT)
                , DELTA
        );
    }

    @Test
    public void inchesToCentimeters_isCorrect() {
        assertEquals(IN_TO_CM_ANSWER
                , ConversionUtil.inchesToCentimeters(IN_TO_CM_INPUT)
                , DELTA
        );
    }

    @Test
    public void fahrenheitToCelsius_isCorrect() {
        assertEquals(FAHRENHEIT_TO_CELSIUS_ANSWER
                , ConversionUtil.fahrenheitToCelsius(FAHRENHEIT_TO_CELSIUS_INPUT)
                , DELTA
        );
    }

    @Test
    public void gallonsToLiters_isCorrect() {
        assertEquals(GALLONS_TO_LITERS_ANSWER
                , ConversionUtil.gallonsToLiters(GALLONS_TO_LITERS_INPUT)
                , DELTA
        );
    }
}
