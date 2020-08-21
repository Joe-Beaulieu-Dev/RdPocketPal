package com.example.rdpocketpal2.util;

import com.example.rdpocketpal2.testutil.TestConstants;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculationUtilTest {
    //region Test data
    // Brandi values
    private static final double BRANDI_BENEDICT_INPUT = 1572.5;
    private static final double BRANDI_ANSWER = 1668.6;

    // Calorie Min/Max values
    private static final double CALORIE_MIN_MAX_BMR = 1234.5;
    private static final double CALORIE_ACTIVITY_FACTOR_MIN = 1;
    private static final double CALORIE_ACTIVITY_FACTOR_MAX = 2;
    private static final double CALORIE_MIN_ANSWER = 1234.5;
    private static final double CALORIE_MAX_ANSWER = 2469;

    // Quick Method values
    private static final double QUICK_METHOD_ANSWER_STANDARD = 90.718474;
    private static final double QUICK_METHOD_ANSWER_METRIC = 200;
    private static final double QUICK_METHOD_WEIGHT = 100;
    private static final double QUICK_METHOD_FACTOR = 2;
    //endregion

    //region Predictive Equations
    @Test
    public void calculateBmrMifflin_metric_male_isCorrect() {
        assertEquals(TestConstants.MIFFLIN_MALE_ANSWER
                , CalculationUtil.calculateBmrMifflin(Constants.METRIC
                        , Constants.MALE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrMifflin_metric_female_isCorrect() {
        assertEquals(TestConstants.MIFFLIN_FEMALE_ANSWER
                , CalculationUtil.calculateBmrMifflin(Constants.METRIC
                        , Constants.FEMALE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrMifflin_standard_male_isCorrect() {
        assertEquals(TestConstants.MIFFLIN_MALE_ANSWER
                , CalculationUtil.calculateBmrMifflin(Constants.STANDARD
                        , Constants.MALE
                        , TestConstants.WEIGHT_STANDARD
                        , TestConstants.HEIGHT_STANDARD
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrMifflin_standard_female_isCorrect() {
        assertEquals(TestConstants.MIFFLIN_FEMALE_ANSWER
                , CalculationUtil.calculateBmrMifflin(Constants.STANDARD
                        , Constants.FEMALE
                        , TestConstants.WEIGHT_STANDARD
                        , TestConstants.HEIGHT_STANDARD
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBenedict_metric_male_isCorrect() {
        assertEquals(TestConstants.BENEDICT_MALE_ANSWER
                , CalculationUtil.calculateBmrBenedict(Constants.METRIC
                        , Constants.MALE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBenedict_metric_female_isCorrect() {
        assertEquals(TestConstants.BENEDICT_FEMALE_ANSWER
                , CalculationUtil.calculateBmrBenedict(Constants.METRIC
                        , Constants.FEMALE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBenedict_standard_male_isCorrect() {
        assertEquals(TestConstants.BENEDICT_MALE_ANSWER
                , CalculationUtil.calculateBmrBenedict(Constants.STANDARD
                        , Constants.MALE
                        , TestConstants.WEIGHT_STANDARD
                        , TestConstants.HEIGHT_STANDARD
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBenedict_standard_female_isCorrect() {
        assertEquals(TestConstants.BENEDICT_FEMALE_ANSWER
                , CalculationUtil.calculateBmrBenedict(Constants.STANDARD
                        , Constants.FEMALE
                        , TestConstants.WEIGHT_STANDARD
                        , TestConstants.HEIGHT_STANDARD
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrPennState2003b_metric_isCorrect() {
        assertEquals(TestConstants.PENN_STATE_2003b_ANSWER
                , CalculationUtil.calculateBmrPennState2003b(Constants.METRIC
                        , TestConstants.PENN_STATE_2003B_MIFFLIN_INPUT
                        , TestConstants.TMAX_METRIC
                        , TestConstants.VE_METRIC)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrPennState2003b_standard_isCorrect() {
        assertEquals(TestConstants.PENN_STATE_2003b_ANSWER
                , CalculationUtil.calculateBmrPennState2003b(Constants.STANDARD
                        , TestConstants.PENN_STATE_2003B_MIFFLIN_INPUT
                        , TestConstants.TMAX_STANDARD
                        , TestConstants.VE_METRIC)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrPennState2010_metric_isCorrect() {
        assertEquals(TestConstants.PENN_STATE_2010_ANSWER
                , CalculationUtil.calculateBmrPennState2010(Constants.METRIC
                        , TestConstants.PENN_STATE_2010_MIFFLIN_INPUT
                        , TestConstants.TMAX_METRIC
                        , TestConstants.VE_METRIC)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrPennState2010_standard_isCorrect() {
        assertEquals(TestConstants.PENN_STATE_2010_ANSWER
                , CalculationUtil.calculateBmrPennState2010(Constants.STANDARD
                        , TestConstants.PENN_STATE_2010_MIFFLIN_INPUT
                        , TestConstants.TMAX_STANDARD
                        , TestConstants.VE_METRIC)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBrandi_isCorrect() {
        assertEquals(BRANDI_ANSWER
                , CalculationUtil.calculateBmrBrandi(BRANDI_BENEDICT_INPUT
                        , TestConstants.HEART_RATE
                        , TestConstants.VE_METRIC)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateCalorieMin_isCorrect() {
        assertEquals(CALORIE_MIN_ANSWER
                , CalculationUtil.calculateCalorieMin(CALORIE_MIN_MAX_BMR
                        , CALORIE_ACTIVITY_FACTOR_MIN)
                , TestConstants.DELTA_THREE);
    }

    @Test
    public void calculateCalorieMax_isCorrect() {
        assertEquals(CALORIE_MAX_ANSWER
                , CalculationUtil.calculateCalorieMax(CALORIE_MIN_MAX_BMR
                        , CALORIE_ACTIVITY_FACTOR_MAX)
                , TestConstants.DELTA_THREE);
    }
    // endregion

    //region Quick Method
    @Test
    public void calculateQuickMethod_metric_isCorrect() {
        assertEquals(QUICK_METHOD_ANSWER_METRIC
                , CalculationUtil.calculateQuickMethod(Constants.METRIC
                        , QUICK_METHOD_WEIGHT
                        , QUICK_METHOD_FACTOR)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateQuickMethod_standard_isCorrect() {
        assertEquals(QUICK_METHOD_ANSWER_STANDARD
                , CalculationUtil.calculateQuickMethod(Constants.STANDARD
                        , QUICK_METHOD_WEIGHT
                        , QUICK_METHOD_FACTOR)
                , TestConstants.DELTA_SIX);
    }
    //endregion

    //region Anthropometrics
    @Test
    public void calculateBmi_metric_isCorrect() {

    }

    @Test
    public void calculateBmi_standard_isCorrect() {

    }

    @Test
    public void calculateIbwHamwi_metric_male_adult_isCorrect() {

    }

    @Test
    public void calculateIbwHamwi_metric_male_child_isCorrect() {

    }

    @Test
    public void calculateIbwHamwi_metric_female_adult_isCorrect() {

    }

    @Test
    public void calculateIbwHamwi_metric_female_child_isCorrect() {

    }

    @Test
    public void calculateIbwHamwi_standard_male_adult_isCorrect() {

    }

    @Test
    public void calculateIbwHamwi_standard_male_child_isCorrect() {

    }

    @Test
    public void calculateIbwHamwi_standard_female_adult_isCorrect() {

    }

    @Test
    public void calculateIbwHamwi_standard_female_child_isCorrect() {

    }

    @Test
    public void calculatePercentIbw_isCorrect() {

    }

    @Test
    public void calculateAdjustedIbw_metric_isCorrect() {

    }

    @Test
    public void calculateAdjustedIbw_standard_isCorrect() {

    }
    //endregion
}
