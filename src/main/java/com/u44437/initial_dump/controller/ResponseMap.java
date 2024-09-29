package com.u44437.initial_dump.controller;

import java.sql.SQLException;
import java.util.Map;

class ResponseMap {
  protected static Map<String, String> getErrorResponse(Exception e, String... messages) {
    String errorMessage = e.getMessage();

    if (e instanceof SQLException) {
      errorMessage = "Something went wrong";
    }

    return Map.of(
        "error", (messages.length != 0 ? String.join(" ", messages) + " - " : "") + errorMessage);
  }
}
