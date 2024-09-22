package com.u44437.initial_dump.controller;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

class ResponseMap {
  protected static Optional getErrorResponse(Exception e, String... messages) {
    String errorMessage = e.getMessage();

    if (e instanceof SQLException) {
      errorMessage = "Something went wrong";
    }

    return Optional.of(Map.of("error", String.join(" ", messages) + " - " + errorMessage));
  }
}
