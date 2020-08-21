package com.example.rdpocketpal2.util;

import com.example.rdpocketpal2.testutil.TestConstants;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MetricEquationUtilTest {
    //region Test data
    // Brandi values
    private static final double BRANDI_BENEDICT_INPUT = 1798.5;
    private static final double BRANDI_ANSWER = 1885.56;

    // Quick Method values
    private static final double QUICK_METHOD_WEIGHT_INPUT = 75;
    private static final double QUICK_METHOD_FACTOR_INPUT = 2;
    private static final double QUICK_METHOD_ANSWER = 150;

    // BMI values
    private static final double BMI_ANSWER = 24.4897959;

    // IBW values
    private static final double IBW_MALE_ANSWER = 159.38582677;
    private static final double IBW_FEMALE_ANSWER = 144.488188975;
    private static final double IBW_MALE_CHILD_ANSWER = 88.992125983;
    private static final double IBW_FEMALE_CHILD_ANSWER = 85.8267716525;

    // Percent IBW values
    private static final double PERCENT_IBW_ANSWER = 103.73989958;

    // Adjusted IBW values
    private static final double ADJUSTED_IBW_ANSWER = 160.8760442375;
    //endregion

    //region Predictive Equations
    @Test
    public void calculateBmrMifflin_male_isCorrect() {
        assertEquals(TestConstants.MIFFLIN_MALE_ANSWER
                , MetricEquationUtil.calculateBmrMifflin(Constants.MALE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrMifflin_female_isCorrect() {
        assertEquals(TestConstants.MIFFLIN_FEMALE_ANSWER
                , MetricEquationUtil.calculateBmrMifflin(Constants.FEMALE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBenedict_male_isCorrect() {
        assertEquals(TestConstants.BENEDICT_MALE_ANSWER
                , MetricEquationUtil.calculateBmrBenedict(Constants.MALE
                        , TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC
                        , TestConstants.AGE)
                , TestConstants.DELTA_THREE
        );
    }

    @Test
    public void calculateBmrBenedict_female_isCorrect() {
        assertEquals(TestConstants.BENEDICT_FEMALE_ANSWER
                , MetricEquationUtil.calculateBmrBenedict(Constants.FEMALE
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
        assertEquals(BRANDI_ANSWER
                , MetricEquationUtil.calculateBmrBrandi(BRANDI_BENEDICT_INPUT
                        , TestConstants.HEART_RATE
                        , TestConstants.VE_METRIC)
                , TestConstants.DELTA_THREE
        );
    }
    //endregion

    //region Quick Method
    @Test
    public void calculateQuickMethod_isCorrect() {
        assertEquals(QUICK_METHOD_ANSWER
                , MetricEquationUtil.calculateQuickMethod(QUICK_METHOD_WEIGHT_INPUT
                        , QUICK_METHOD_FACTOR_INPUT)
                , TestConstants.DELTA_THREE
        );
    }
    //endregion

    //region Anthropometrics
    @Test
    public void calculateBmi_isCorrect() {
        assertEquals(BMI_ANSWER
                , MetricEquationUtil.calculateBmi(TestConstants.WEIGHT_METRIC
                        , TestConstants.HEIGHT_METRIC)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_male_adult_isCorrect() {
        assertEquals(IBW_MALE_ANSWER
                , MetricEquationUtil.calculateIbwHamwi(SexK.Male.INSTANCE
                        , TestConstants.HEIGHT_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_female_adult_isCorrect() {
        assertEquals(IBW_FEMALE_ANSWER
                , MetricEquationUtil.calculateIbwHamwi(SexK.Female.INSTANCE
                        , TestConstants.HEIGHT_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_male_child_isCorrect() {
        assertEquals(IBW_MALE_CHILD_ANSWER
                , MetricEquationUtil.calculateIbwHamwi(SexK.Male.INSTANCE
                        , TestConstants.HEIGHT_CHILD_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateIbwHamwi_female_child_isCorrect() {
        assertEquals(IBW_FEMALE_CHILD_ANSWER
                , MetricEquationUtil.calculateIbwHamwi(SexK.Female.INSTANCE
                        , TestConstants.HEIGHT_CHILD_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculatePercentIbw_isCorrect() {
        assertEquals(PERCENT_IBW_ANSWER
                , MetricEquationUtil.calculatePercentIbw(IBW_MALE_ANSWER
                        , TestConstants.WEIGHT_STANDARD)
                , TestConstants.DELTA_SIX);
    }

    @Test
    public void calculateAdjustedIbw_isCorrect() {
        assertEquals(ADJUSTED_IBW_ANSWER
                , MetricEquationUtil.calculateAdjustedIbw(IBW_MALE_ANSWER
                        , TestConstants.WEIGHT_STANDARD)
                , TestConstants.DELTA_SIX);
    }
    //endregion
}
