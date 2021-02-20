package com.octrobi.rdpocketpal.util;

import com.octrobi.rdpocketpal.testutil.TestConstants;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MetricEquationUtilTest {

    //region Predictive Equations
    @Test
    public void calculateBmrMifflin_male_isCorrect() {
        assertEquals(TestConstants.MIFFLIN_MALE_ANSWER
                , MetricEquationUtil.calculateBmrMifflin(Sex.MALE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrMifflin_female_isCorrect() {
        assertEquals(TestConstants.MIFFLIN_FEMALE_ANSWER
                , MetricEquationUtil.calculateBmrMifflin(Sex.FEMALE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBenedict_male_isCorrect() {
        assertEquals(TestConstants.BENEDICT_MALE_ANSWER
                , MetricEquationUtil.calculateBmrBenedict(Sex.MALE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBenedict_female_isCorrect() {
        assertEquals(TestConstants.BENEDICT_FEMALE_ANSWER
                , MetricEquationUtil.calculateBmrBenedict(Sex.FEMALE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrPennState2003b_isCorrect() {
        assertEquals(TestConstants.PENN_STATE_2003b_ANSWER
                , MetricEquationUtil.calculateBmrPennState2003b(
                        TestConstants.PENN_STATE_2003B_MIFFLIN_INPUT
                        , TestConstants.TMAX_METRIC
                        , TestConstants.VE_METRIC)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrPennState2010_isCorrect() {
        assertEquals(TestConstants.PENN_STATE_2010_ANSWER
                , MetricEquationUtil.calculateBmrPennState2010(
                        TestConstants.PENN_STATE_2010_MIFFLIN_INPUT
                        , TestConstants.TMAX_METRIC
                        , TestConstants.VE_METRIC)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBrandi_isCorrect() {
        assertEquals(TestConstants.BRANDI_ANSWER
                , MetricEquationUtil.calculateBmrBrandi(TestConstants.BRANDI_BENEDICT_INPUT
                        , TestConstants.HEART_RATE
                        , TestConstants.VE_METRIC)
                , TestConstants.DELTA_THREE
        );
    }
    //endregion

    //region Quick Method
    @Test
    public void calculateQuickMethod_isCorrect() {
        assertEquals(TestConstants.QUICK_METHOD_ANSWER_METRIC
                , MetricEquationUtil.calculateQuickMethod(TestConstants.QUICK_METHOD_WEIGHT
                        , TestConstants.QUICK_METHOD_FACTOR)
                , TestConstants.DELTA_THREE
        );
    }
    //endregion

    //region Anthropometrics
    @Test
    public void calculateBmi_isCorrect() {
        assertEquals(TestConstants.BMI_ANSWER
                , MetricEquationUtil.calculateBmi(TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_male_adult_isCorrect() {
        assertEquals(TestConstants.IBW_MALE_STANDARD_ANSWER
                , MetricEquationUtil.calculateIbwHamwi(Sex.MALE
                        , TestConstants.HEIGHT_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_female_adult_isCorrect() {
        assertEquals(TestConstants.IBW_FEMALE_STANDARD_ANSWER
                , MetricEquationUtil.calculateIbwHamwi(Sex.FEMALE
                        , TestConstants.HEIGHT_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_male_child_isCorrect() {
        assertEquals(TestConstants.IBW_MALE_CHILD_STANDARD_ANSWER
                , MetricEquationUtil.calculateIbwHamwi(Sex.MALE
                        , TestConstants.HEIGHT_CHILD_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_female_child_isCorrect() {
        assertEquals(TestConstants.IBW_FEMALE_CHILD_STANDARD_ANSWER
                , MetricEquationUtil.calculateIbwHamwi(Sex.FEMALE
                        , TestConstants.HEIGHT_CHILD_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculatePercentIbw_isCorrect() {
        assertEquals(TestConstants.PERCENT_IBW_ANSWER
                , MetricEquationUtil.calculatePercentIbw(TestConstants.IBW_MALE_METRIC_ANSWER
                        , TestConstants.WEIGHT_METRIC)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateAdjustedBw_isCorrect() {
        assertEquals(TestConstants.ADJUSTED_BW_METRIC_ANSWER
                , MetricEquationUtil.calculateAdjustedBw(TestConstants.IBW_MALE_METRIC_ANSWER
                        , TestConstants.WEIGHT_METRIC)
                , TestConstants.DELTA_SIX);
    }
    //endregion
}
