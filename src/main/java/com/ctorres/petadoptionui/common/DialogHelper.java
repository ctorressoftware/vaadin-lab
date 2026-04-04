package com.ctorres.petadoptionui.common;

import com.ctorres.petadoptionui.common.ui.ConfirmDialog;
import com.ctorres.petadoptionui.common.ui.ResultModal;

public class DialogHelper {

    public static ConfirmDialog showConfirmDialog(Runnable action, Runnable onSuccess) {
        return new ConfirmDialog(userConfirmation -> {
            if (userConfirmation) {
                action.run();
                onSuccess.run();
            }
        });
    }

    public static ResultModal showResultDialog(String title, String result, float width) {
        return new ResultModal(title, result, width);
    }
}
