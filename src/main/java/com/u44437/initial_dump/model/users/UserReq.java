package com.u44437.initial_dump.model.users;

import lombok.Data;

@Data
public class UserReq {
  private String name;
  private String surname;

  public UserReq(String name, String surname) {
    this.name = name;
    this.surname = surname;
  }
}
