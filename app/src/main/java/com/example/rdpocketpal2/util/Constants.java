package com.example.rdpocketpal2.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

public class Constants {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MALE, FEMALE})
    public @interface Sex{}
    public static final int MALE = 0;
    public static final int FEMALE = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({METRIC, STANDARD})
    public @interface Unit{}
    public static final int METRIC = 0;
    public static final int STANDARD = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CALCIUM, CHLORINE, MAGNESIUM, PHOSPHORUS, POTASSIUM, SODIUM})
    public @interface Element {}
    public static final int CALCIUM = 0;
    public static final int CHLORINE = 1;
    public static final int MAGNESIUM = 2;
    public static final int PHOSPHORUS = 3;
    public static final int POTASSIUM = 4;
    public static final int SODIUM = 5;

    public static final double CALCIUM_ATOMIC_WEIGHT = 40.078;
    public static final double CHLORINE_ATOMIC_WEIGHT = 35.45;
    public static final double MAGNESIUM_ATOMIC_WEIGHT = 24.305;
    public static final double PHOSPHORUS_ATOMIC_WEIGHT = 30.974;
    public static final double POTASSIUM_ATOMIC_WEIGHT = 39.098;
    public static final double SODIUM_ATOMIC_WEIGHT = 22.99;

    public static final int CALCIUM_VALENCE = 2;
    public static final int CHLORINE_VALENCE = 1;
    public static final int MAGNESIUM_VALENCE = 2;
    // can be 3 or 5
    public static final int PHOSPHORUS_VALENCE = 3;
    public static final int POTASSIUM_VALENCE = 1;
    public static final int SODIUM_VALENCE = 1;
}
