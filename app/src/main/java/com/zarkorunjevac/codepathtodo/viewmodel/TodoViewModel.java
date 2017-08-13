package com.zarkorunjevac.codepathtodo.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.zarkorunjevac.codepathtodo.db.DatabaseCreator;
import com.zarkorunjevac.codepathtodo.db.entity.Todo;

/**
 * Created by zarkorunjevac on 12/08/17.
 */

public class TodoViewModel extends AndroidViewModel {

    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

    private final LiveData<Todo> mObservableTodo;

    public ObservableField<Todo> todo=new ObservableField<>();

    private final int mTodoId;


    public TodoViewModel(@NonNull Application application,
                         final int todoId){
        super(application);
        mTodoId=todoId;

        final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());

        mObservableTodo= Transformations.switchMap(databaseCreator.isDatabaseCreated(),
                new Function<Boolean, LiveData<Todo>>() {
                    @Override
                    public LiveData<Todo> apply(Boolean isDbCreated) {
                        if(!isDbCreated){
                            return ABSENT;
                        }else{
                            return databaseCreator.getDatabase().todoModel().loadTodo(mTodoId);
                        }
                    }
                });

        databaseCreator.createDb(this.getApplication());
    }

    public LiveData<Todo> getObservableTodo(){
        return mObservableTodo;
    }

    public void setTodo(Todo todo){
        this.todo.set(todo);
    }

    public void insertTodo(final Todo todo){
        final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());
        Runnable saveRunnable=new Runnable() {
            @Override
            public void run() {
                databaseCreator.getDatabase().todoModel().insertTodo(todo);
            }
        };
        new Thread(saveRunnable).start();
        //databaseCreator.getDatabase().todoModel().insertTodo(todo);
    }

    public void updateTodo(final Todo todo){
        final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());
        Runnable saveRunnable=new Runnable() {
            @Override
            public void run() {
                databaseCreator.getDatabase().todoModel().updateTodo(todo);
            }
        };
        new Thread(saveRunnable).start();

    }



    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application mApplication;

        private final int mTodoId;

        public Factory(@NonNull Application application, int todoId){
            mApplication=application;
            mTodoId=todoId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T)new TodoViewModel(mApplication,mTodoId);
        }
    }
}
