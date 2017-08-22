package com.zarkorunjevac.codepathtodo.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.zarkorunjevac.codepathtodo.R;
import com.zarkorunjevac.codepathtodo.databinding.TodoItemBinding;
import com.zarkorunjevac.codepathtodo.db.entity.Todo;
import com.zarkorunjevac.codepathtodo.ui.callback.TodoClickCallback;
import com.zarkorunjevac.codepathtodo.ui.callback.TodoDeleteCallback;
import com.zarkorunjevac.codepathtodo.ui.touchHelper.ItemTouchHelperAdapter;

import java.util.List;


/**
 * Created by zarko.runjevac on 8/2/2017.
 */

public class TodoItemsAdapter extends RecyclerView.Adapter<TodoItemsViewHolder> implements ItemTouchHelperAdapter {



  List<? extends Todo> mTodoList;

  @Nullable
  private final TodoClickCallback mTodoClickCallback;
  @Nullable
  private final TodoDeleteCallback mTodoDeleteCallback;

  public TodoItemsAdapter(@Nullable TodoClickCallback todoClickCallback,
                          @Nullable TodoDeleteCallback todoDeleteCallback){
    mTodoClickCallback=todoClickCallback;
    mTodoDeleteCallback=todoDeleteCallback;
  }

  public void setTodoList(final List<? extends Todo> todoList){
    if(mTodoList==null){
      mTodoList=todoList;
      notifyItemRangeChanged(0,todoList.size());
    }else{
      DiffUtil.DiffResult result=DiffUtil.calculateDiff(new DiffUtil.Callback() {
        @Override
        public int getOldListSize() {
          return mTodoList.size();
        }

        @Override
        public int getNewListSize() {
          return todoList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
          return mTodoList.get(oldItemPosition).getId() ==
              todoList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
          Todo todo=todoList.get(newItemPosition);
          Todo old=mTodoList.get(oldItemPosition);

          return todo.getId() ==old.getId()
                  && todo.getName()==old.getName()
                  && todo.getDueDate()==old.getDueDate()
                  && todo.getTaskNotes()==old.getTaskNotes()
                  && todo.getPriorityLevel()==old.getPriorityLevel()
                  && todo.getStatus()==old.getStatus();


        }
      });

      mTodoList=todoList;
      result.dispatchUpdatesTo(this);
    }
  }

  @Override
  public TodoItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


    TodoItemBinding binding= DataBindingUtil
        .inflate(LayoutInflater.from(parent.getContext()),R.layout.todo_item,
            parent,false);
    binding.setCallback(mTodoClickCallback);
    return new TodoItemsViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(TodoItemsViewHolder holder, int position) {
    //holder.bindData(mTodoList.get(position));
    holder.binding.setTodo(mTodoList.get(position));
    holder.binding.executePendingBindings();
  }

  @Override
  public int getItemCount() {
    return mTodoList== null ? 0 :mTodoList.size();
  }


  @Override
  public boolean onItemMove(int fromPosition, int toPosition) {
    return false;
  }

  @Override
  public void onItemDismiss(int position) {
    //mTodoDeleteCallback(mTodoList.get(position));
    mTodoDeleteCallback.onDelete(mTodoList.get(position));
//    mTodoList.remove(position);
//    notifyItemRemoved(position);
  }
}
