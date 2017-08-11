package com.zarkorunjevac.codepathtodo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import com.zarkorunjevac.codepathtodo.db.entity.Todo;
import com.zarkorunjevac.codepathtodo.db.dao.TodoDao;
import com.zarkorunjevac.codepathtodo.db.converter.DateConverter;

/**
 * Created by zarko.runjevac on 8/7/2017.
 */
@Database(entities = {Todo.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

  static final String DATABASE_NAME="todo_db";

  public abstract TodoDao todoModel();


}
