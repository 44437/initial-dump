package com.u44437.initial_dump.model.users;

import lombok.Data;

@Data
public class UserRes {
  private int id;
  private String name;
  private String surname;

  public UserRes(int id, String name, String surname) {
    this.id = id;
    this.name = name;
    this.surname = surname;
  }
}
