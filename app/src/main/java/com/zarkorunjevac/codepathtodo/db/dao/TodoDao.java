package com.zarkorunjevac.codepathtodo.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
import com.zarkorunjevac.codepathtodo.db.converter.DateConverter;
import com.zarkorunjevac.codepathtodo.db.entity.Todo;
import java.util.List;

/**
 * Created by zarko.runjevac on 8/7/2017.
 */
@Dao
@TypeConverters(DateConverter.class)
public interface TodoDao {

  @Query("Select * FROM Todo")
  public LiveData<List<Todo>> findAllTodos();

  @Query("Select * FROM Todo")
  public List<Todo> findAllTodosSync();

  @Insert(onConflict = IGNORE)
  void insertTodo(Todo todo);

  @Update(onConflict = REPLACE)
  void updateTodo(Todo todo);

  @Query("DELETE FROM Todo")
  void deleteAll();

  @Query("DELETE FROM Todo WHERE Todo.mid=:id")
  void deleteTodo(int id);
}
