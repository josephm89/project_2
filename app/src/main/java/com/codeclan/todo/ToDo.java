package com.codeclan.todo;

import java.util.Date;

public class ToDo {
  private String name;
  private Boolean chore;
  private String dueDate;

  public ToDo(String name, Boolean chore, String dueDate) {
    this.name = name;
    this.chore = chore;
    this.dueDate = dueDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getChore() {
    return chore;
  }

  public void setChore(Boolean chore) {
    this.chore = chore;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }
}
