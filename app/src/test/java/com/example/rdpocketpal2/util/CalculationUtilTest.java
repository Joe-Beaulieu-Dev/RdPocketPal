package com.example.rdpocketpal2.util;

import com.example.rdpocketpal2.testutil.TestConstants;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculationUtilTest {
    //region Test data
    // Calorie Min/Max
    private static final double CALORIE_MIN_MAX_BMR = 1234.5;
    private static final double CALORIE_ACTIVITY_FACTOR_MIN = 1;
    private static final double CALORIE_ACTIVITY_FACTOR_MAX = 2;
    private static final double CALORIE_MIN_ANSWER = 1234.5;
    private static final double CALORIE_MAX_ANSWER = 2469;

    // Quick Method
    private static final double QUICK_METHOD_ANSWER_STANDARD = 90.718474;
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
        assertEquals(TestConstants.BRANDI_ANSWER
                , CalculationUtil.calculateBmrBrandi(TestConstants.BRANDI_BENEDICT_INPUT
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
        assertEquals(TestConstants.QUICK_METHOD_ANSWER_METRIC
                , CalculationUtil.calculateQuickMethod(Constants.METRIC
                        , TestConstants.QUICK_METHOD_WEIGHT
                        , TestConstants.QUICK_METHOD_FACTOR)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateQuickMethod_standard_isCorrect() {
        assertEquals(QUICK_METHOD_ANSWER_STANDARD
                , CalculationUtil.calculateQuickMethod(Constants.STANDARD
                        , TestConstants.QUICK_METHOD_WEIGHT
                        , TestConstants.QUICK_METHOD_FACTOR)
                , TestConstants.DELTA_SIX);
    }
    //endregion

    //region Anthropometrics
    @Test
    public void calculateBmi_metric_isCorrect() {
        assertEquals(TestConstants.BMI_ANSWER
                , CalculationUtil.calculateBmi(UnitK.Metric.INSTANCE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateBmi_standard_isCorrect() {
        assertEquals(TestConstants.BMI_ANSWER
                , CalculationUtil.calculateBmi(UnitK.Standard.INSTANCE
                        , TestConstants.WEIGHT_STANDARD
                        , TestConstants.HEIGHT_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_metric_male_adult_isCorrect() {
        assertEquals(TestConstants.IBW_MALE_METRIC_ANSWER
                , CalculationUtil.calculateIbwHamwi(UnitK.Metric.INSTANCE
                        , SexK.Male.INSTANCE
                        , TestConstants.HEIGHT_METRIC)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_metric_male_child_isCorrect() {
        assertEquals(TestConstants.IBW_MALE_CHILD_METRIC_ANSWER
                , CalculationUtil.calculateIbwHamwi(UnitK.Metric.INSTANCE
                        , SexK.Male.INSTANCE
                        , TestConstants.HEIGHT_CHILD_METRIC)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_metric_female_adult_isCorrect() {
        assertEquals(TestConstants.IBW_FEMALE_METRIC_ANSWER
                , CalculationUtil.calculateIbwHamwi(UnitK.Metric.INSTANCE
                        , SexK.Female.INSTANCE
                        , TestConstants.HEIGHT_METRIC)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_metric_female_child_isCorrect() {
        assertEquals(TestConstants.IBW_FEMALE_CHILD_METRIC_ANSWER
                , CalculationUtil.calculateIbwHamwi(UnitK.Metric.INSTANCE
                        , SexK.Female.INSTANCE
                        , TestConstants.HEIGHT_CHILD_METRIC)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_standard_male_adult_isCorrect() {
        assertEquals(TestConstants.IBW_MALE_STANDARD_ANSWER
                , CalculationUtil.calculateIbwHamwi(UnitK.Standard.INSTANCE
                        , SexK.Male.INSTANCE
                        , TestConstants.HEIGHT_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_standard_male_child_isCorrect() {
        assertEquals(TestConstants.IBW_MALE_CHILD_STANDARD_ANSWER
                , CalculationUtil.calculateIbwHamwi(UnitK.Standard.INSTANCE
                        , SexK.Male.INSTANCE
                        , TestConstants.HEIGHT_CHILD_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_standard_female_adult_isCorrect() {
        assertEquals(TestConstants.IBW_FEMALE_STANDARD_ANSWER
                , CalculationUtil.calculateIbwHamwi(UnitK.Standard.INSTANCE
                        , SexK.Female.INSTANCE
                        , TestConstants.HEIGHT_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_standard_female_child_isCorrect() {
        assertEquals(TestConstants.IBW_FEMALE_CHILD_STANDARD_ANSWER
                , CalculationUtil.calculateIbwHamwi(UnitK.Standard.INSTANCE
                        , SexK.Female.INSTANCE
                        , TestConstants.HEIGHT_CHILD_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculatePercentIbw_metric_isCorrect() {
        assertEquals(TestConstants.PERCENT_IBW_ANSWER
                , CalculationUtil.calculatePercentIbw(UnitK.Metric.INSTANCE
                        , SexK.Male.INSTANCE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculatePercentIbw_standard_isCorrect() {
        assertEquals(TestConstants.PERCENT_IBW_ANSWER
                , CalculationUtil.calculatePercentIbw(UnitK.Standard.INSTANCE
                        , SexK.Male.INSTANCE
                        , TestConstants.WEIGHT_STANDARD
                        , TestConstants.HEIGHT_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateAdjustedIbw_metric_isCorrect() {
        assertEquals(TestConstants.ADJUSTED_IBW_METRIC_ANSWER
                , CalculationUtil.calculateAdjustedIbw(UnitK.Metric.INSTANCE
                        , SexK.Male.INSTANCE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateAdjustedIbw_standard_isCorrect() {
        assertEquals(TestConstants.ADJUSTED_IBW_STANDARD_ANSWER
                , CalculationUtil.calculateAdjustedIbw(UnitK.Standard.INSTANCE
                        , SexK.Male.INSTANCE
                        , TestConstants.WEIGHT_STANDARD
                        , TestConstants.HEIGHT_STANDARD)
                , TestConstants.DELTA_SIX);
    }
    //endregion
}
