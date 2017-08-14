package com.zarkorunjevac.codepathtodo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Lifecycle.State;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zarkorunjevac.codepathtodo.databinding.FragmentTodoListBinding;
import com.zarkorunjevac.codepathtodo.db.entity.Todo;


import com.zarkorunjevac.codepathtodo.ui.TodoClickCallback;
import com.zarkorunjevac.codepathtodo.ui.TodoDeleteCallback;
import com.zarkorunjevac.codepathtodo.ui.TodoItemActivity;
import com.zarkorunjevac.codepathtodo.ui.TodoItemsAdapter;
import com.zarkorunjevac.codepathtodo.ui.touchHelper.OnStartDragListener;
import com.zarkorunjevac.codepathtodo.ui.touchHelper.SimpleItemTouchHelperCallback;
import com.zarkorunjevac.codepathtodo.viewmodel.TodoListViewModel;
import java.util.List;


public class TodoListFragment extends LifecycleFragment implements OnStartDragListener {

  public static final String TAG="TodoListViewModel";

  private TodoItemsAdapter mTodoItemsAdapter;

  private FragmentTodoListBinding mBinding;

  private ItemTouchHelper mItemTouchHelper;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    mBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list,container,false);

    mTodoItemsAdapter=new TodoItemsAdapter(mTodoClickCallback, mTodoDeleteCallback);

    mBinding.todosList.setAdapter(mTodoItemsAdapter);

    ItemTouchHelper.Callback callback= new SimpleItemTouchHelperCallback(mTodoItemsAdapter);
    mItemTouchHelper=new ItemTouchHelper(callback);
    mItemTouchHelper.attachToRecyclerView(mBinding.todosList);

    return mBinding.getRoot();
  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    final TodoListViewModel viewModel= ViewModelProviders.of(this).get(TodoListViewModel.class);
    FloatingActionButton fab = (FloatingActionButton) mBinding.fab;
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getActivity(), TodoItemActivity.class).putExtra(TodoItemActivity.KEY_TODO_ID,0));
      }
    });
    subscribeToViewModel(viewModel);
  }



  private void subscribeToViewModel(final TodoListViewModel viewModel){
    viewModel.getTodos().observe(this, new Observer<List<Todo>>() {
      @Override
      public void onChanged(@Nullable List<Todo> todos) {
        if(todos!=null){
          mBinding.setIsLoading(false);

          mTodoItemsAdapter.setTodoList(todos);
        }else {
          mBinding.setIsLoading(true);
        }
      }
    });
  }

  private final TodoClickCallback mTodoClickCallback=new TodoClickCallback() {
    @Override
    public void onClick(Todo todo) {
      if(getLifecycle().getCurrentState().isAtLeast(State.STARTED)){
        startActivity(new Intent(getActivity(),TodoItemActivity.class).putExtra(TodoItemActivity.KEY_TODO_ID,todo.getId()));
      }
    }
  };

  private final TodoDeleteCallback mTodoDeleteCallback=new TodoDeleteCallback() {
    @Override
    public void onDelete(Todo todo) {
      final TodoListViewModel viewModel= ViewModelProviders.of(TodoListFragment.this).get(TodoListViewModel.class);
      viewModel.deleteTodo(todo);
    }
  };

  @Override
  public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
    mItemTouchHelper.startDrag(viewHolder);
  }
}
