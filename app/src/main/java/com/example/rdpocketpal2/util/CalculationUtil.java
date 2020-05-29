package com.example.rdpocketpal2.util;

import com.example.rdpocketpal2.util.Constants.Sex;
import com.example.rdpocketpal2.util.Constants.Unit;

public class CalculationUtil {

    //region Predictive Equations
    public static double calculateBmrMifflin(@Unit int unit, @Sex int sex, double weight, double height, int age) {
        switch (unit) {
            case Constants.METRIC:
                return MetricEquationUtil.calculateBmrMifflin(sex, weight, height, age);
            case Constants.STANDARD:
                return MetricEquationUtil.calculateBmrMifflin(sex
                        , ConversionUtil.poundsToKilograms(weight)
                        , ConversionUtil.inchesToCentimeters(height)
                        , age
                );
            default:
                return 0.0;
        }
    }

    public static double calculateBmrBenedict(@Unit int unit, @Sex int sex, double weight, double height, int age) {
        switch (unit) {
            case Constants.METRIC:
                return MetricEquationUtil.calculateBmrBenedict(sex, weight, height, age);
            case Constants.STANDARD:
                return MetricEquationUtil.calculateBmrBenedict(sex
                        , ConversionUtil.poundsToKilograms(weight)
                        , ConversionUtil.inchesToCentimeters(height)
                        , age
                );
            default:
                return 0.0;
        }
    }

    public static double calculateBmrPennState2003b(@Unit int unit, double mifflinBmr, double tmax, double ve) {
        switch (unit) {
            case Constants.METRIC:
                return MetricEquationUtil.calculateBmrPennState2003b(mifflinBmr, tmax, ve);
            case Constants.STANDARD:
                return MetricEquationUtil.calculateBmrPennState2003b(mifflinBmr
                        , ConversionUtil.fahrenheitToCelsius(tmax)
                        , ConversionUtil.gallonsToLiters(ve)
                );
            default:
                return 0.0;
        }
    }

    public static double calculateBmrPennState2010(@Unit int unit, double mifflinBmr, double tmax, double ve) {
        switch (unit) {
            case Constants.METRIC:
                return MetricEquationUtil.calculateBmrPennState2010(mifflinBmr, tmax, ve);
            case Constants.STANDARD:
                return MetricEquationUtil.calculateBmrPennState2010(mifflinBmr
                        , ConversionUtil.fahrenheitToCelsius(tmax)
                        , ConversionUtil.gallonsToLiters(ve)
                );
            default:
                return 0.0;
        }
    }

    public static double calculateBmrBrandi(@Unit int unit, double benedictBmr, double heartRate, double ve) {
        switch (unit) {
            case Constants.METRIC:
                return MetricEquationUtil.calculateBmrBrandi(benedictBmr, heartRate, ve);
            case Constants.STANDARD:
                return MetricEquationUtil.calculateBmrBrandi(benedictBmr
                        , heartRate
                        , ConversionUtil.gallonsToLiters(ve)
                );
            default:
                return 0.0;
        }
    }

    public static double calculateCalorieMin(double bmr, double activityLevelMin) {
        return bmr * activityLevelMin;
    }

    public static double calculateCalorieMax(double bmr, double activityLevelMax) {
        return bmr * activityLevelMax;
    }
    //endregion

    //region QuickMethod
//    public static double calculateQuickMethod(@Unit int unit, double weight, double factor) {
//        switch (unit) {
//            case Constants.METRIC:
//                return MetricEquationUtil.calculateQuickMethod(weight, factor);
//            case Constants.STANDARD:
//                return MetricEquationUtil.calculateQuickMethod(
//                        ConversionUtil.poundsToKilograms(weight), factor);
//            default:
//                return 0.0;
//        }
//    }

    public static double calculateQuickMethod(@Unit int unit, double weight, double factor) {
        switch (unit) {
            case Constants.METRIC:
                return MetricEquationUtilKotlin.calculateQuickMethod(weight, factor);
            case Constants.STANDARD:
                return MetricEquationUtilKotlin.calculateQuickMethod(
                        ConversionUtilKotlin.poundsToKilograms(weight), factor);
            default:
                return 0.0;
        }
    }
    //endregion
}
