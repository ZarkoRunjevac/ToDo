package com.zarkorunjevac.codepathtodo.db.converter;

import android.arch.persistence.room.TypeConverter;
import java.util.Date;

/**
 * Created by zarko.runjevac on 8/7/2017.
 */

public class DateConverter {
  @TypeConverter
  public static Date toDate(Long timestamp) {
    return timestamp == null ? null : new Date(timestamp);
  }

  @TypeConverter
  public static Long toTimestamp(Date date) {
    return date == null ? null : date.getTime();
  }
}
