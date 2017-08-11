package com.zarkorunjevac.codepathtodo.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.zarkorunjevac.codepathtodo.R;

import com.zarkorunjevac.codepathtodo.databinding.ActivityTaskItemBinding;
import com.zarkorunjevac.codepathtodo.db.AppDatabase;
import com.zarkorunjevac.codepathtodo.db.DatabaseCreator;
import com.zarkorunjevac.codepathtodo.db.entity.Todo;
import java.util.Calendar;

public class TodoItemActivity extends AppCompatActivity {

  private Todo todo;

  private AppDatabase mDb;

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

  final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_task_item);
    ActivityTaskItemBinding binding = DataBindingUtil
        .setContentView(this, R.layout.activity_task_item);
    ButterKnife.bind(this);
    todo = new Todo();

    binding.setTodo(todo);



    mSaveButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        setTodo();
        Runnable saveRunnable=new Runnable() {
          @Override
          public void run() {
            databaseCreator.getDatabase().todoModel().insertTodo(todo);
          }
        };
       new Thread(saveRunnable).start();
        finish();
      }
    });
  }

  @Override
  protected void onDestroy() {
    //AppDatabase.destroyInstance();
    super.onDestroy();
  }

  private void setTodo() {
    todo.setName(mNameEditText.getText().toString());
    todo.setTaskNotes(mNotesEditText.getText().toString());
    Calendar calendar=Calendar.getInstance();

    calendar.set(Calendar.DATE, mDueDateDatePicker.getDayOfMonth());
    calendar.set(Calendar.MONTH, mDueDateDatePicker.getMonth());
    calendar.set(Calendar.YEAR,mDueDateDatePicker.getYear());

    todo.setDueDate(calendar.getTime());

    todo.setPriorityLevel(mPrioritySpinner.getSelectedItemPosition());

    todo.setStatus(mStatusSpinner.getSelectedItemPosition());


  }
}
