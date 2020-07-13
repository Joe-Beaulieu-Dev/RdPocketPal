package com.example.rdpocketpal2.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MetricEquationUtilTest {
    // User input values
    private static final double WEIGHT = 75;
    private static final double HEIGHT = 175;
    private static final int AGE = 25;
    private static final double TMAX = 37;
    private static final double HEART_RATE = 75;
    private static final double VE = 7;
    private static final double DELTA = 0.001;

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
    private static final double BRANDI_BENEDICT_INPUT = 1798.5;
    private static final double BRANDI_ANSWER = 1885.56;

    @Test
    public void calculateBmrMifflin_male_isCorrect() {
        assertEquals(MIFFLIN_MALE_ANSWER
                , MetricEquationUtil.calculateBmrMifflin(Constants.MALE, WEIGHT, HEIGHT, AGE)
                , DELTA
        );
    }

    @Test
    public void calculateBmrMifflin_female_isCorrect() {
        assertEquals(MIFFLIN_FEMALE_ANSWER
                , MetricEquationUtil.calculateBmrMifflin(Constants.FEMALE, WEIGHT, HEIGHT, AGE)
                , DELTA
        );
    }

    @Test
    public void calculateBmrBenedict_male_isCorrect() {
        assertEquals(BENEDICT_MALE_ANSWER
                , MetricEquationUtil.calculateBmrBenedict(Constants.MALE, WEIGHT, HEIGHT, AGE)
                , DELTA
        );
    }

    @Test
    public void calculateBmrBenedict_female_isCorrect() {
        assertEquals(BENEDICT_FEMALE_ANSWER
                , MetricEquationUtil.calculateBmrBenedict(Constants.FEMALE, WEIGHT, HEIGHT, AGE)
                , DELTA
        );
    }

    @Test
    public void calculateBmrPennState2003b_isCorrect() {
        assertEquals(PENN_STATE_2003b_ANSWER
                , MetricEquationUtil.calculateBmrPennState2003b(PENN_STATE_2003B_MIFFLIN_INPUT, TMAX, VE)
                , DELTA
        );
    }

    @Test
    public void calculateBmrPennState2010_isCorrect() {
        assertEquals(PENN_STATE_2010_ANSWER
                , MetricEquationUtil.calculateBmrPennState2010(PENN_STATE_2010_MIFFLIN_INPUT, TMAX, VE)
                , DELTA
        );
    }

    @Test
    public void calculateBmrBrandi_isCorrect() {
        assertEquals(BRANDI_ANSWER
                , MetricEquationUtil.calculateBmrBrandi(BRANDI_BENEDICT_INPUT, HEART_RATE, VE)
                , DELTA
        );
    }
}
