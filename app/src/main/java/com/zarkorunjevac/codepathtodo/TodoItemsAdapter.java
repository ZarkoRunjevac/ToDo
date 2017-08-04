package com.zarkorunjevac.codepathtodo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zarkorunjevac.codepathtodo.model.Todo;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zarko.runjevac on 8/2/2017.
 */

class TodoItemsAdapter extends RecyclerView.Adapter<TodoItemsViewHolder> {

  List<Todo> items= Arrays.asList(new Todo("one"), new Todo("two"), new Todo("three"));



  @Override
  public TodoItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rvitems,parent,false);
    return new TodoItemsViewHolder(view);
  }

  @Override
  public void onBindViewHolder(TodoItemsViewHolder holder, int position) {
    holder.bindData(items.get(position));
  }

  @Override
  public int getItemCount() {
    return items.size();
  }
}
