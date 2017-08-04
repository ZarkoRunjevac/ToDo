package com.zarkorunjevac.codepathtodo.model;

import java.util.Date;

/**
 * Created by zarko.runjevac on 8/2/2017.
 */

public class Todo {

  public Todo(String name){
    mName=name;
  }

  private String mName;

  private Date mDueDate;

  private String mTaskNotes;

  private String mPriorityLevel;

  private String mStatus;

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

  public String getPriorityLevel() {
    return mPriorityLevel;
  }

  public void setPriorityLevel(String mPriorityLevel) {
    this.mPriorityLevel = mPriorityLevel;
  }

  public String getStatus() {
    return mStatus;
  }

  public void setStatus(String mStatus) {
    this.mStatus = mStatus;
  }
}
