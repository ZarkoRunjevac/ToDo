package com.zarkorunjevac.codepathtodo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Lifecycle.State;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zarkorunjevac.codepathtodo.databinding.FragmentTodoListBinding;
import com.zarkorunjevac.codepathtodo.db.entity.Todo;
import com.zarkorunjevac.codepathtodo.dummy.DummyContent;
import com.zarkorunjevac.codepathtodo.dummy.DummyContent.DummyItem;

import com.zarkorunjevac.codepathtodo.ui.TodoClickCallback;
import com.zarkorunjevac.codepathtodo.ui.TodoItemsAdapter;
import com.zarkorunjevac.codepathtodo.viewmodel.TodoListViewModel;
import java.util.List;


public class TodoListFragment extends LifecycleFragment {

  public static final String TAG="TodoListViewModel";

  private TodoItemsAdapter mTodoItemsAdapter;

  private FragmentTodoListBinding mBinding;



  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    mBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list,container,false);

    mTodoItemsAdapter=new TodoItemsAdapter(mTodoClickCallback);

    mBinding.todosList.setAdapter(mTodoItemsAdapter);

    return mBinding.getRoot();
  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    final TodoListViewModel viewModel= ViewModelProviders.of(this).get(TodoListViewModel.class);

    subscribeToViewModel(viewModel);
  }

  private void subscribeToViewModel(TodoListViewModel viewModel){
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
        ((MainActivity) getActivity()).show(todo);
      }
    }
  };

}
