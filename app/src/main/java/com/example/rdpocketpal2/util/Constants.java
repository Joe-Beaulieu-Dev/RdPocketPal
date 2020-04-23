package com.example.rdpocketpal2.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

public class Constants {
    public static final int ERROR = -1;

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
}
