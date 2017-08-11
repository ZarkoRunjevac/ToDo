package com.zarkorunjevac.codepathtodo.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.zarkorunjevac.codepathtodo.R;
import com.zarkorunjevac.codepathtodo.databinding.TodoItemBinding;
import com.zarkorunjevac.codepathtodo.db.entity.Todo;

/**
 * Created by zarko.runjevac on 8/2/2017.
 */

class TodoItemsViewHolder extends RecyclerView.ViewHolder {

  TextView tvTodoName;

  final TodoItemBinding binding;

  public TodoItemsViewHolder(TodoItemBinding binding){
    super(binding.getRoot());
    this.binding=binding;
  }




}
