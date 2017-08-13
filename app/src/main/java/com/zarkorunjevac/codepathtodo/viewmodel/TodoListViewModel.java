package com.zarkorunjevac.codepathtodo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import com.zarkorunjevac.codepathtodo.db.DatabaseCreator;
import com.zarkorunjevac.codepathtodo.db.entity.Todo;
import java.util.List;
import android.arch.core.util.Function;

/**
 * Created by zarko.runjevac on 8/10/2017.
 */

public class TodoListViewModel extends AndroidViewModel{

  private static final MutableLiveData ABSENT = new MutableLiveData();
  {
    //noinspection unchecked
    ABSENT.setValue(null);
  }

  private final LiveData<List<Todo>> mObservableTodos;

  public TodoListViewModel(Application application){
    super(application);

    final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());

    LiveData<Boolean> databaseCreated = databaseCreator.isDatabaseCreated();

    mObservableTodos= Transformations.switchMap(databaseCreated,
        new Function<Boolean, LiveData<List<Todo>>>() {
          @Override
          public LiveData<List<Todo>> apply(Boolean isDbCreated) {
            if (!Boolean.TRUE.equals(isDbCreated)) { // Not needed here, but watch out for null
              //noinspection unchecked
              return ABSENT;
            } else {
              //noinspection ConstantConditions
              return databaseCreator.getDatabase().todoModel().findAllTodos();
            }
          }
        });

    databaseCreator.createDb(this.getApplication());

  }

  public LiveData<List<Todo>> getTodos(){
    return mObservableTodos;
  }

  public void deleteTodo(final Todo todo){
    final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());
    Runnable saveRunnable=new Runnable() {
      @Override
      public void run() {
        databaseCreator.getDatabase().todoModel().deleteTodo(todo.getId());
      }
    };
    new Thread(saveRunnable).start();

  }

}
