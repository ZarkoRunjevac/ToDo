package com.zarkorunjevac.codepathtodo.ui;

import com.zarkorunjevac.codepathtodo.db.entity.Todo;

/**
 * Created by zarko.runjevac on 8/11/2017.
 */

public interface TodoClickCallback {
  void onClick(Todo todo);
}
