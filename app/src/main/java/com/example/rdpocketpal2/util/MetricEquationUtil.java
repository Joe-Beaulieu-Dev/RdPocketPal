package com.example.rdpocketpal2.util;

import com.example.rdpocketpal2.util.Constants.Sex;

//region Predictive Equations
public class MetricEquationUtil {
    public static double calculateBmrMifflin(@Sex int sex, double weight, double height, int age) {
        switch (sex) {
            case Constants.MALE:
                return (10 * weight) + (6.25 * height) - (5 * age) + 5;
            case Constants.FEMALE:
                return (10 * weight) + (6.25 * height) - (5 * age) - 161;
            default:
                return 0.0;
        }
    }

    public static double calculateBmrBenedict(@Sex int sex, double weight, double height, int age) {
        switch (sex) {
            case Constants.MALE:
                return 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
            case Constants.FEMALE:
                return 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
            default:
                return 0.0;
        }
    }

    public static double calculateBmrPennState2003b(double mifflinBmr, double tmax, double ve) {
        return (mifflinBmr * 0.96) + (tmax * 167) + (ve * 31) - 6212;
    }

    public static double calculateBmrPennState2010(double mifflinBmr, double tmax, double ve) {
        return (mifflinBmr * 0.71) + (tmax * 85) + (ve * 64) - 3085;
    }

    public static double calculateBmrBrandi(double harrisBmr, double heartRate, double ve) {
        return (harrisBmr * 0.96) + (heartRate * 7) + (ve * 48) - 702;
    }
    //endregion

    //region Quick Method
    public static double calculateQuickMethod(double weight, double factor) {
        return weight * factor;
    }
    //endregion
}
