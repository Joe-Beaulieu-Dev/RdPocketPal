package com.example.rdpocketpal2.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculationUtilTest {
    // general values
    private static final double DELTA_THREE = 0.001;
    private static final double DELTA_SIX = 0.000001;

    // User input values
    private static final double WEIGHT_METRIC = 75;
    private static final double WEIGHT_STANDARD = 165.3466966387;
    private static final double HEIGHT_METRIC = 175;
    private static final double HEIGHT_STANDARD = 68.8976377953;
    private static final int AGE = 25;
    private static final double TMAX_METRIC = 37;
    private static final double TMAX_STANDARD = 98.6;
    private static final double HEART_RATE = 75;
    private static final double VE_METRIC = 7;
    private static final double VE_STANDARD = 1.849205238;

    // Mifflin values
    private static final double MIFFLIN_MALE_ANSWER = 1723.75;
    private static final double MIFFLIN_FEMALE_ANSWER = 1557.75;

    // Benedict values
    private static final double BENEDICT_MALE_ANSWER = 1798.5;
    private static final double BENEDICT_FEMALE_ANSWER = 1572.5;

    // Penn State 2003b values
    private static final double PENN_STATE_2003B_MIFFLIN_INPUT = 1723.75;
    private static final double PENN_STATE_2003b_ANSWER = 1838.8;

    // Penn State 2010 values
    private static final double PENN_STATE_2010_MIFFLIN_INPUT = 1723.75;
    private static final double PENN_STATE_2010_ANSWER = 1731.8625;

    // Brandi values
    private static final double BRANDI_BENEDICT_INPUT = 1572.5;
    private static final double BRANDI_ANSWER = 1668.6;

    // Quick Method values
    private static final double QUICK_METHOD_ANSWER_STANDARD = 90.718474;
    private static final double QUICK_METHOD_ANSWER_METRIC = 200;
    private static final double QUICK_METHOD_WEIGHT = 100;
    private static final double QUICK_METHOD_FACTOR = 2;

    @Test
    public void calculateBmrMifflin_metric_male_isCorrect() {
        assertEquals(MIFFLIN_MALE_ANSWER
                , CalculationUtil.calculateBmrMifflin(Constants.METRIC
                        , Constants.MALE, WEIGHT_METRIC, HEIGHT_METRIC, AGE)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateBmrMifflin_metric_female_isCorrect() {
        assertEquals(MIFFLIN_FEMALE_ANSWER
                , CalculationUtil.calculateBmrMifflin(Constants.METRIC
                        , Constants.FEMALE, WEIGHT_METRIC, HEIGHT_METRIC, AGE)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateBmrMifflin_standard_male_isCorrect() {
        assertEquals(MIFFLIN_MALE_ANSWER
                , CalculationUtil.calculateBmrMifflin(Constants.STANDARD
                        , Constants.MALE, WEIGHT_STANDARD, HEIGHT_STANDARD, AGE)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateBmrMifflin_standard_female_isCorrect() {
        assertEquals(MIFFLIN_FEMALE_ANSWER
                , CalculationUtil.calculateBmrMifflin(Constants.STANDARD
                        , Constants.FEMALE, WEIGHT_STANDARD, HEIGHT_STANDARD, AGE)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBenedict_metric_male_isCorrect() {
        assertEquals(BENEDICT_MALE_ANSWER
                , CalculationUtil.calculateBmrBenedict(Constants.METRIC
                        , Constants.MALE, WEIGHT_METRIC, HEIGHT_METRIC, AGE)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBenedict_metric_female_isCorrect() {
        assertEquals(BENEDICT_FEMALE_ANSWER
                , CalculationUtil.calculateBmrBenedict(Constants.METRIC
                        , Constants.FEMALE, WEIGHT_METRIC, HEIGHT_METRIC, AGE)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBenedict_standard_male_isCorrect() {
        assertEquals(BENEDICT_MALE_ANSWER
                , CalculationUtil.calculateBmrBenedict(Constants.STANDARD
                        , Constants.MALE, WEIGHT_STANDARD, HEIGHT_STANDARD, AGE)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBenedict_standard_female_isCorrect() {
        assertEquals(BENEDICT_FEMALE_ANSWER
                , CalculationUtil.calculateBmrBenedict(Constants.STANDARD
                        , Constants.FEMALE, WEIGHT_STANDARD, HEIGHT_STANDARD, AGE)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateBmrPennState2003b_metric_isCorrect() {
        assertEquals(PENN_STATE_2003b_ANSWER
                , CalculationUtil.calculateBmrPennState2003b(Constants.METRIC
                        , PENN_STATE_2003B_MIFFLIN_INPUT, TMAX_METRIC, VE_METRIC)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateBmrPennState2003b_standard_isCorrect() {
        assertEquals(PENN_STATE_2003b_ANSWER
                , CalculationUtil.calculateBmrPennState2003b(Constants.STANDARD
                        , PENN_STATE_2003B_MIFFLIN_INPUT, TMAX_STANDARD, VE_STANDARD)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateBmrPennState2010_metric_isCorrect() {
        assertEquals(PENN_STATE_2010_ANSWER
                , CalculationUtil.calculateBmrPennState2010(Constants.METRIC
                        , PENN_STATE_2010_MIFFLIN_INPUT, TMAX_METRIC, VE_METRIC)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateBmrPennState2010_standard_isCorrect() {
        assertEquals(PENN_STATE_2010_ANSWER
                , CalculationUtil.calculateBmrPennState2010(Constants.STANDARD
                        , PENN_STATE_2010_MIFFLIN_INPUT, TMAX_STANDARD, VE_STANDARD)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBrandi_metric_isCorrect() {
        assertEquals(BRANDI_ANSWER
                , CalculationUtil.calculateBmrBrandi(Constants.METRIC
                        , BRANDI_BENEDICT_INPUT, HEART_RATE, VE_METRIC)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBrandi_standard_isCorrect() {
        assertEquals(BRANDI_ANSWER
                , CalculationUtil.calculateBmrBrandi(Constants.STANDARD
                        , BRANDI_BENEDICT_INPUT, HEART_RATE, VE_STANDARD)
                , DELTA_THREE
        );
    }

    @Test
    public void calculateQuickMethod_metric_isCorrect() {
        assertEquals(QUICK_METHOD_ANSWER_METRIC
                , CalculationUtil.calculateQuickMethod(Constants.METRIC
                        , QUICK_METHOD_WEIGHT, QUICK_METHOD_FACTOR)
                , DELTA_SIX);
    }

    @Test
    public void calculateQuickMethod_standard_isCorrect() {
        assertEquals(QUICK_METHOD_ANSWER_STANDARD
                , CalculationUtil.calculateQuickMethod(Constants.STANDARD
                        , QUICK_METHOD_WEIGHT, QUICK_METHOD_FACTOR)
                , DELTA_SIX);
    }
}
