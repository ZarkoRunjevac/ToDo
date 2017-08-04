package com.zarkorunjevac.codepathtodo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.zarkorunjevac.codepathtodo.model.Todo;
import org.w3c.dom.Text;

/**
 * Created by zarko.runjevac on 8/2/2017.
 */

class TodoItemsViewHolder extends RecyclerView.ViewHolder {

  TextView tvTodoName;

  public TodoItemsViewHolder(View itemView) {
    super(itemView);
    tvTodoName=(TextView) itemView.findViewById(R.id.todo_name);
  }

  public void bindData(Todo item){
    tvTodoName.setText(item.getName());
  }
}
