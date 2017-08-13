package com.zarkorunjevac.codepathtodo.ui;

import com.zarkorunjevac.codepathtodo.db.entity.Todo;

/**
 * Created by zarkorunjevac on 13/08/17.
 */

public interface TodoDeleteCallback {
    void onDelete(Todo todo);
}
