package com.zarkorunjevac.codepathtodo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.zarkorunjevac.codepathtodo.databinding.FragmentTodoBinding;
import com.zarkorunjevac.codepathtodo.db.AppDatabase;
import com.zarkorunjevac.codepathtodo.db.DatabaseCreator;
import com.zarkorunjevac.codepathtodo.db.entity.Todo;
import com.zarkorunjevac.codepathtodo.ui.TodoItemActivity;
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
    mBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_todo,container,false);

    Button button=mBinding.btnSave;
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


}
