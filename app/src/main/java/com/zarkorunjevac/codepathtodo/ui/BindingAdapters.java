package com.zarkorunjevac.codepathtodo.ui;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by zarko.runjevac on 8/11/2017.
 */

public class BindingAdapters {
  @BindingAdapter("visibleGone")
  public static void showHide(View view, boolean show) {
    view.setVisibility(show ? View.VISIBLE : View.GONE);
  }
}
