package com.zarkorunjevac.codepathtodo.ui;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zarkorunjevac.codepathtodo.R;

import com.zarkorunjevac.codepathtodo.TodoFragment;


public class TodoItemActivity extends AppCompatActivity {

  public static final String KEY_TODO_ID = "todo_id";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task_item);



    int id=getIntent().getExtras().getInt(KEY_TODO_ID,0);
    if(savedInstanceState == null){
      TodoFragment fragment= TodoFragment.forTodo(id);
      getSupportFragmentManager().beginTransaction().
              add(R.id.fragment_container, fragment, TodoFragment.TAG).commit();
    }
  }

}
