package com.example.rdpocketpal2.util;

import com.example.rdpocketpal2.predictiveequations.ValidationException;

import java.util.Objects;

import androidx.lifecycle.MutableLiveData;

public class NumberUtil {
    public static double parseDouble(MutableLiveData<String> liveData) throws NumberFormatException {
//        if (liveData.getValue() != null) {
//            return Double.parseDouble(liveData.getValue());
//        } else {
//            throw new ValidationException("Passed liveData was not a double");
//        }

//        if (liveData.getValue() != null) {
//            try {
//                return Double.parseDouble(liveData.getValue());
//            } catch (NumberFormatException | NullPointerException e) {
//                e.printStackTrace();
//                throw new ValidationException("Passed liveData was not a double");
//            }
//        } else {
//            throw new ValidationException("Passed liveData.getValue() was null");
//        }

        return Double.parseDouble(Objects.requireNonNull(liveData.getValue()));
    }

    public static int parseInt(MutableLiveData<String> liveData) throws NumberFormatException {
//        if (liveData.getValue() != null) {
//            return Integer.parseInt(liveData.getValue());
//        } else {
//            throw new ValidationException("Passed liveData was not a double");
//        }

//        if (liveData.getValue() != null) {
//            try {
//                return Integer.parseInt(liveData.getValue());
//            } catch (NumberFormatException | NullPointerException e) {
//                e.printStackTrace();
//                throw new ValidationException("Passed liveData was not a double");
//            }
//        } else {
//            throw new ValidationException("Passed liveData.getValue() was null");
//        }

        return Integer.parseInt(Objects.requireNonNull(liveData.getValue()));
    }

    public static boolean isDouble(MutableLiveData<String> liveData) {
        try {
            Double.parseDouble(Objects.requireNonNull(liveData.getValue()));
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isInt(MutableLiveData<String> liveData) throws ValidationException {
        try {
            if (liveData.getValue() != null) {
                Integer.parseInt(liveData.getValue());
                return true;
            } else {
                throw new ValidationException("Passed liveData was null");
            }
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
