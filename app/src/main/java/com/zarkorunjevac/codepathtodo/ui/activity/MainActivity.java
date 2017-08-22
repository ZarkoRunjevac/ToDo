package com.zarkorunjevac.codepathtodo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.zarkorunjevac.codepathtodo.R;
import com.zarkorunjevac.codepathtodo.ui.fragment.TodoListFragment;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    if(savedInstanceState == null){
      TodoListFragment fragment= new TodoListFragment();
      getSupportFragmentManager().beginTransaction().
              add(R.id.fragment_container, fragment, TodoListFragment.TAG).commit();
    }


 }
}
