package com.zarkorunjevac.codepathtodo.ui.fragment;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.ButterKnife;

import com.zarkorunjevac.codepathtodo.R;
import com.zarkorunjevac.codepathtodo.databinding.FragmentTodoBinding;
import com.zarkorunjevac.codepathtodo.db.entity.Todo;
import com.zarkorunjevac.codepathtodo.ui.activity.TodoItemActivity;
import com.zarkorunjevac.codepathtodo.utils.DataInputHelper;
import com.zarkorunjevac.codepathtodo.viewmodel.TodoViewModel;

import java.util.Calendar;

/**
 * Created by zarko.runjevac on 8/11/2017.
 */

public class TodoFragment extends LifecycleFragment {

  public static final String TAG="TodoViewModel";
  private static final String KEY_TODO_ID = "todo_id";

  private Todo mTodo;

  private FragmentTodoBinding mBinding;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    mBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_todo,container,false);

    Button button=mBinding.btnSave;

    mBinding.back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        ((TodoItemActivity) getActivity()).onBackPressed();
      }
    });

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getTodoValues();
        if(mTodo.getId()==0){
          mBinding.getTodoViewModel().insertTodo(mTodo );
        }else{
          mBinding.getTodoViewModel().updateTodo(mTodo );
        }

        if(getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)){
          ((TodoItemActivity) getActivity()).finish();
        }
      }
    });

    return mBinding.getRoot();

  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initContenViews(view);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(getActivity());
   }



  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);


    TodoViewModel.Factory factory=new TodoViewModel.Factory(
            getActivity().getApplication(),getArguments().getInt(KEY_TODO_ID));

    final TodoViewModel model= ViewModelProviders.of(this,factory)
            .get(TodoViewModel.class);

    mBinding.setTodoViewModel(model);

    subscribeToModel(model);
  }

  private void subscribeToModel(final TodoViewModel model){
    model.getObservableTodo().observe(this, new Observer<Todo>() {
      @Override
      public void onChanged(@Nullable Todo todo) {
        mTodo=todo;
        if(null!=mTodo){
          Calendar calendar=Calendar.getInstance();
          calendar.setTime(mTodo.getDueDate());
          mBinding.dpDueDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Calendar.DATE);
        }
        model.setTodo(todo);
      }
    });
  }

  public static TodoFragment forTodo(int todoId){
    TodoFragment fragment=new TodoFragment();
    Bundle args=new Bundle();
    args.putInt(KEY_TODO_ID,todoId);
    fragment.setArguments(args);

    return fragment;
  }


  private void getTodoValues(){
    if (mTodo==null) mTodo=new Todo();
    mTodo.setName(mBinding.txtTodoName.getText().toString());
    mTodo.setTaskNotes(mBinding.txtTodoNotes.getText().toString());
    Calendar calendar=Calendar.getInstance();

    calendar.set(Calendar.DATE, mBinding.dpDueDate.getDayOfMonth());
    calendar.set(Calendar.MONTH, mBinding.dpDueDate.getMonth());
    calendar.set(Calendar.YEAR,mBinding.dpDueDate.getYear());

    mTodo.setDueDate(calendar.getTime());

    mTodo.setPriorityLevel(mBinding.spnPriority.getSelectedItemPosition());

    mTodo.setStatus(mBinding.spnStatus.getSelectedItemPosition());
  }

  private boolean isInputDataValid(){
    return DataInputHelper.isInputDataValid(mBinding.txtTodoName.getText());
  }

  private void initContenViews(View view){
    TextWatcher textWatcher=new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        if(s.length()==0) mBinding.btnSave.setEnabled(false);
      }

      @Override
      public void afterTextChanged(Editable editable) {
          if(isInputDataValid()) mBinding.btnSave.setEnabled(true);
      }
    };

    mBinding.txtTodoName.addTextChangedListener(textWatcher);
  }


}
