package com.zarkorunjevac.codepathtodo.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by zarko.runjevac on 8/7/2017.
 */
@Database(entities = {Todo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

  private static AppDatabase INSTANCE;

  public abstract TodoDao todoModel();

  public static AppDatabase getInMemoryDatabase(Context context) {
    if (INSTANCE == null) {
      INSTANCE =
          Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
              // To simplify the codelab, allow queries on the main thread.
              // Don't do this on a real app! See PersistenceBasicSample for an example.
              .allowMainThreadQueries()
              .build();
    }
    return INSTANCE;
  }

  public static void destroyInstance() {
    INSTANCE = null;
  }
}
