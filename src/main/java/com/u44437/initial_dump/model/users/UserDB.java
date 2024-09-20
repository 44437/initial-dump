package com.u44437.initial_dump.model.users;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDB {
  private int id;
  private String name;
  private String surname;

  public UserDB(int id, String name, String surname) {
    this.id = id;
    this.name = name;
    this.surname = surname;
  }
}
