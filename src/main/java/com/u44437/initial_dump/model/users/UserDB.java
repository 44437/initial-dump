package com.u44437.initial_dump.model.users;

import lombok.SneakyThrows;

public record UserDB(int id, String name, String surname) {
  @SneakyThrows
  public UserDB(int id, String name, String surname) {
    if (id == 0) {
      throw new Exception();
    }

    this.id = id;
    this.name = name;
    this.surname = surname;
  }
}
