package com.u44437.initial_dump.model.users;

public class UserReq {
  private int id;
  private String name;
  private String surname;

  public UserReq(int id, String name, String surname) {
    this.id = id;
    this.name = name;
    this.surname = surname;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }
}
