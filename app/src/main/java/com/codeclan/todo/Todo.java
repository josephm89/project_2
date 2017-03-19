package com.codeclan.todo;

import java.util.Date;

public class Todo {
  private String name;
  private Boolean chore;
  private Date dueDate;

  public Todo(String name, Boolean chore, Date dueDate) {
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
