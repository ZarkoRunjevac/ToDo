package com.zarkorunjevac.codepathtodo;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.zarkorunjevac.codepathtodo.db.entity.Todo;
import com.zarkorunjevac.codepathtodo.ui.TodoItemActivity;
import com.zarkorunjevac.codepathtodo.ui.TodoItemsAdapter;
import com.zarkorunjevac.codepathtodo.viewmodel.TodoListViewModel;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private RecyclerView rvItems;
  private TodoItemsAdapter mTodoItemsAdapter;




  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    if(savedInstanceState == null){
      TodoListFragment fragment= new TodoListFragment();
      getSupportFragmentManager().beginTransaction().
              add(R.id.fragment_container, fragment, TodoListFragment.TAG).commit();
    }


//    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//    setSupportActionBar(toolbar);
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, TodoItemActivity.class).putExtra(TodoItemActivity.KEY_TODO_ID,0));
      }
    });
 }
}
