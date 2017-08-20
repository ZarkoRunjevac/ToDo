package com.zarkorunjevac.codepathtodo.utils;

/**
 * Created by zarkorunjevac on 20/08/17.
 */
import android.text.TextUtils;

public class DataInputHelper {
    public static boolean isInputDataValid(CharSequence todoName) {
        return !TextUtils.isEmpty(todoName);
    }
}
