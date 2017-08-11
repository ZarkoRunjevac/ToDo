package com.zarkorunjevac.codepathtodo;

import android.arch.lifecycle.LifecycleFragment;
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
import com.zarkorunjevac.codepathtodo.databinding.ActivityTaskItemBinding;
import com.zarkorunjevac.codepathtodo.db.AppDatabase;
import com.zarkorunjevac.codepathtodo.db.DatabaseCreator;
import com.zarkorunjevac.codepathtodo.db.entity.Todo;

/**
 * Created by zarko.runjevac on 8/11/2017.
 */

public class TodoFragment extends LifecycleFragment {
  private static final String KEY_TODO_ID = "todo_id";


  private Todo todo;

  private AppDatabase mDb;

  private ActivityTaskItemBinding mBinding;

  @BindView(R.id.btnSave)
  Button mSaveButton;
  @BindView(R.id.txtTodoName)
  EditText mNameEditText;
  @BindView(R.id.txtTodoNotes)
  EditText mNotesEditText;
  @BindView(R.id.dpDueDate)
  DatePicker mDueDateDatePicker;
  @BindView(R.id.spnPriority)
  AppCompatSpinner mPrioritySpinner;
  @BindView(R.id.spnStatus)
  AppCompatSpinner mStatusSpinner;

  final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(getActivity());


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(getActivity());
    //todo = new Todo();


  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
   mBinding= DataBindingUtil.inflate(inflater,R.layout.activity_task_item,container,false);
    mBinding.setTodo(todo);
    return mBinding.getRoot();

  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    //mBinding.setTodo(todo);
  }

  public static TodoFragment forTodo(int todoId){
    TodoFragment fragment=new TodoFragment();
    Bundle args=new Bundle();
    args.putInt(KEY_TODO_ID,todoId);
    fragment.setArguments(args);

    return fragment;
  }
}
