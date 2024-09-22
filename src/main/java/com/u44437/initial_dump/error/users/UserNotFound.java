package com.u44437.initial_dump.error.users;

public class UserNotFound extends Exception {
  public UserNotFound() {}

  public UserNotFound(String message) {
    super(message);
  }

  public UserNotFound(Throwable cause) {
    super(cause);
  }
}
