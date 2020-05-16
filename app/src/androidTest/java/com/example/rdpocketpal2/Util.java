package com.example.rdpocketpal2;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.test.rule.ActivityTestRule;

public class Util {

    /**
     * Return string literal from {@link ActivityTestRule}
     *
     * @param rule     current activity
     * @param stringId resource id
     * @return string literal
     */
    public static String getString(@NonNull ActivityTestRule rule, @IdRes int stringId) {
        return rule.getActivity().getString(stringId);
    }

}