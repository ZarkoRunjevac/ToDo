package com.zarkorunjevac.codepathtodo.model;

import static android.R.attr.name;

import android.arch.persistence.room.Entity;
import java.util.Date;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
/**
 * Created by zarko.runjevac on 8/2/2017.
 */
@Entity
public class Todo {



  public Todo(String name, Date dueDate,
              String note, int priority,
              int status){
    mId=0;
    mName=name;
    mDueDate=dueDate;
    mTaskNotes=note;
    mPriorityLevel=priority;
    mStatus=status;
  }

  @PrimaryKey(autoGenerate = true)
  private int mId;

  private String mName;

  private Date mDueDate;

  private String mTaskNotes;

  private int mPriorityLevel;

  private int mStatus;

  public String getName() {
    return mName;
  }

  public void setName(String mName) {
    this.mName = mName;
  }

  public Date getDueDate() {
    return mDueDate;
  }

  public void setDueDate(Date mDueDate) {
    this.mDueDate = mDueDate;
  }

  public String getTaskNotes() {
    return mTaskNotes;
  }

  public void setTaskNotes(String mTaskNotes) {
    this.mTaskNotes = mTaskNotes;
  }

  public int getPriorityLevel() {
    return mPriorityLevel;
  }

  public void setPriorityLevel(int mPriorityLevel) {
    this.mPriorityLevel = mPriorityLevel;
  }

  public int getStatus() {
    return mStatus;
  }

  public void setStatus(int mStatus) {
    this.mStatus = mStatus;
  }
}
