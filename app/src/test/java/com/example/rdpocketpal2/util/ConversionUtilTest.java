package com.example.rdpocketpal2.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConversionUtilTest {
    //region Test data
    // general values
    private static final double DELTA_SIX = 0.000001;
    private static final double DELTA_EIGHT = 0.00000001;
    private static final double TEN_GRAMS = 10;
    private static final double TEN_THOUSAND_MILLIGRAMS = 10000;

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

    // grams/milligrams to milliequivalents values
    private static final double GM_TO_MEQ_CALCIUM = 499.0268975498;
    private static final double GM_TO_MEQ_CHLORINE = 282.0874471086;
    private static final double GM_TO_MEQ_MAGNESIUM = 822.8759514503;
    private static final double GM_TO_MEQ_POTASSIUM = 255.7675584429;
    private static final double GM_TO_MEQ_SODIUM = 434.9717268378;

    // grams/milligrams to millimoles values
    private static final double GM_TO_MMOL_PHOSPHORUS = 322.85142377477884677471427648996;
    //endregion

    //region Weight
    @Test
    public void poundsToKilograms_isCorrect() {
        assertEquals(LB_TO_KG_ANSWER
                , ConversionUtil.poundsToKilograms(LB_TO_KG_INPUT)
                , DELTA_SIX
        );
    }

    @Test
    public void kilogramsToPounds_isCorrect() {
        assertEquals(KG_TO_LB_ANSWER
                , ConversionUtil.kilogramsToPounds(KG_TO_LB_INPUT)
                , DELTA_SIX
        );
    }
    //endregion

    //region Length
    @Test
    public void inchesToCentimeters_isCorrect() {
        assertEquals(IN_TO_CM_ANSWER
                , ConversionUtil.inchesToCentimeters(IN_TO_CM_INPUT)
                , DELTA_SIX
        );
    }

    @Test
    public void centimetersToInches_isCorrect() {
        assertEquals(CM_TO_IN_ANSWER
                , ConversionUtil.centimetersToInches(CM_TO_IN_INPUT)
                , DELTA_SIX
        );
    }
    //endregion

    //region Temperature
    @Test
    public void fahrenheitToCelsius_isCorrect() {
        assertEquals(FAHRENHEIT_TO_CELSIUS_ANSWER
                , ConversionUtil.fahrenheitToCelsius(FAHRENHEIT_TO_CELSIUS_INPUT)
                , DELTA_SIX
        );
    }

    @Test
    public void celsiusToFahrenheit_isCorrect() {
        assertEquals(CELSIUS_TO_FAHRENHEIT_ANSWER
                , ConversionUtil.celsiusToFahrenheit(CELSIUS_TO_FAHRENHEIT_INPUT)
                , DELTA_SIX
        );
    }
    //endregion

    //region Fluid
    @Test
    public void gallonsToLiters_isCorrect() {
        assertEquals(GALLONS_TO_LITERS_ANSWER
                , ConversionUtil.gallonsToLiters(GALLONS_TO_LITERS_INPUT)
                , DELTA_SIX
        );
    }

    @Test
    public void litersToGallons_isCorrect() {
        assertEquals(LITERS_TO_GALLONS_ANSWER
                , ConversionUtil.litersToGallons(LITERS_TO_GALLONS_INPUT)
                , DELTA_SIX
        );
    }
    //endregion

    //region Chemistry
    @Test
    public void gramsToMilliequivalents_isCorrect() {
        // calcium
        assertEquals(GM_TO_MEQ_CALCIUM
                , ConversionUtil.gramsToMilliequivalents(Constants.CALCIUM, TEN_GRAMS)
                , DELTA_EIGHT
        );

        // chlorine
        assertEquals(GM_TO_MEQ_CHLORINE
                , ConversionUtil.gramsToMilliequivalents(Constants.CHLORINE, TEN_GRAMS)
                , DELTA_EIGHT
        );

        // magnesium
        assertEquals(GM_TO_MEQ_MAGNESIUM
                , ConversionUtil.gramsToMilliequivalents(Constants.MAGNESIUM, TEN_GRAMS)
                , DELTA_EIGHT
        );

        // potassium
        assertEquals(GM_TO_MEQ_POTASSIUM
                , ConversionUtil.gramsToMilliequivalents(Constants.POTASSIUM, TEN_GRAMS)
                , DELTA_EIGHT
        );

        // sodium
        assertEquals(GM_TO_MEQ_SODIUM
                , ConversionUtil.gramsToMilliequivalents(Constants.SODIUM, TEN_GRAMS)
                , DELTA_EIGHT
        );
    }

    @Test
    public void milliequivalentsToGrams_isCorrect() {
        // calcium
        assertEquals(TEN_GRAMS
                , ConversionUtil.milliequivalentsToGrams(Constants.CALCIUM, GM_TO_MEQ_CALCIUM)
                , DELTA_EIGHT
        );

        // chlorine
        assertEquals(TEN_GRAMS
                , ConversionUtil.milliequivalentsToGrams(Constants.CHLORINE, GM_TO_MEQ_CHLORINE)
                , DELTA_EIGHT
        );

        // magnesium
        assertEquals(TEN_GRAMS
                , ConversionUtil.milliequivalentsToGrams(Constants.MAGNESIUM, GM_TO_MEQ_MAGNESIUM)
                , DELTA_EIGHT
        );

        // potassium
        assertEquals(TEN_GRAMS
                , ConversionUtil.milliequivalentsToGrams(Constants.POTASSIUM, GM_TO_MEQ_POTASSIUM)
                , DELTA_EIGHT
        );

        // sodium
        assertEquals(TEN_GRAMS
                , ConversionUtil.milliequivalentsToGrams(Constants.SODIUM, GM_TO_MEQ_SODIUM)
                , DELTA_EIGHT
        );
    }

    @Test
    public void milligramsToMilliequivalents_isCorrect() {
        // calcium
        assertEquals(GM_TO_MEQ_CALCIUM
                , ConversionUtil.milligramsToMilliequivalents(Constants.CALCIUM, TEN_THOUSAND_MILLIGRAMS)
                , DELTA_EIGHT
        );

        // chlorine
        assertEquals(GM_TO_MEQ_CHLORINE
                , ConversionUtil.milligramsToMilliequivalents(Constants.CHLORINE, TEN_THOUSAND_MILLIGRAMS)
                , DELTA_EIGHT
        );

        // magnesium
        assertEquals(GM_TO_MEQ_MAGNESIUM
                , ConversionUtil.milligramsToMilliequivalents(Constants.MAGNESIUM, TEN_THOUSAND_MILLIGRAMS)
                , DELTA_EIGHT
        );

        // potassium
        assertEquals(GM_TO_MEQ_POTASSIUM
                , ConversionUtil.milligramsToMilliequivalents(Constants.POTASSIUM, TEN_THOUSAND_MILLIGRAMS)
                , DELTA_EIGHT
        );

        // sodium
        assertEquals(GM_TO_MEQ_SODIUM
                , ConversionUtil.milligramsToMilliequivalents(Constants.SODIUM, TEN_THOUSAND_MILLIGRAMS)
                , DELTA_EIGHT
        );
    }

    @Test
    public void milliequivalentsToMilligrams_isCorrect() {
        // calcium
        assertEquals(TEN_THOUSAND_MILLIGRAMS
                , ConversionUtil.milliequivalentsToMilligrams(Constants.CALCIUM, GM_TO_MEQ_CALCIUM)
                , DELTA_EIGHT
        );

        // chlorine
        assertEquals(TEN_THOUSAND_MILLIGRAMS
                , ConversionUtil.milliequivalentsToMilligrams(Constants.CHLORINE, GM_TO_MEQ_CHLORINE)
                , DELTA_EIGHT
        );

        // magnesium
        assertEquals(TEN_THOUSAND_MILLIGRAMS
                , ConversionUtil.milliequivalentsToMilligrams(Constants.MAGNESIUM, GM_TO_MEQ_MAGNESIUM)
                , DELTA_EIGHT
        );

        // potassium
        assertEquals(TEN_THOUSAND_MILLIGRAMS
                , ConversionUtil.milliequivalentsToMilligrams(Constants.POTASSIUM, GM_TO_MEQ_POTASSIUM)
                , DELTA_EIGHT
        );

        // sodium
        assertEquals(TEN_THOUSAND_MILLIGRAMS
                , ConversionUtil.milliequivalentsToMilligrams(Constants.SODIUM, GM_TO_MEQ_SODIUM)
                , DELTA_EIGHT
        );
    }

    @Test
    public void gramsToMillimoles_isCorrect() {
        // phosphorus
        assertEquals(GM_TO_MMOL_PHOSPHORUS
                , ConversionUtil.gramsToMillimoles(Constants.PHOSPHORUS, TEN_GRAMS)
                , DELTA_EIGHT
        );
    }

    @Test
    public void millimolesToGrams_isCorrect() {
        // phosphorus
        assertEquals(TEN_GRAMS
                , ConversionUtil.millimolesToGrams(Constants.PHOSPHORUS, GM_TO_MMOL_PHOSPHORUS)
                , DELTA_EIGHT
        );
    }

    @Test
    public void milligramsToMillimoles_isCorrect() {
        // phosphorus
        assertEquals(GM_TO_MMOL_PHOSPHORUS
                , ConversionUtil.milligramsToMillimoles(Constants.PHOSPHORUS, TEN_THOUSAND_MILLIGRAMS)
                , DELTA_EIGHT
        );
    }

    @Test
    public void millimolesToMilligrams_isCorrect() {
        // phosphorus
        assertEquals(TEN_THOUSAND_MILLIGRAMS
                , ConversionUtil.millimolesToMilligrams(Constants.PHOSPHORUS, GM_TO_MMOL_PHOSPHORUS)
                , DELTA_EIGHT
        );
    }
    //endregion
}
