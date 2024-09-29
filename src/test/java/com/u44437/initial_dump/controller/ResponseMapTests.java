package com.u44437.initial_dump.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(ResponseMap.class)
class ResponseMapTests {
  @Test
  void CreateResponseMapInstance() {
    new ResponseMap();
  }

  @Nested
  class GetErrorResponse {
    @Test
    void Should_ReturnExceptionWithMessages() {
      Map<String, String> response =
          ResponseMap.getErrorResponse(
              new Exception("Exception"), "Something was terrible", "Something was bizarre");
      assertEquals(
          Map.of("error", "Something was terrible Something was bizarre - Exception"), response);
    }

    @Test
    void Should_ReturnExceptionWithoutMessages() {
      Map<String, String> response = ResponseMap.getErrorResponse(new Exception("Exception"));
      assertEquals(Map.of("error", "Exception"), response);
    }

    @Test
    void Should_ReturnWithCustomErrorMessage_When_SQLException() {
      Map<String, String> response = ResponseMap.getErrorResponse(new SQLException("SQLException"));
      assertEquals(Map.of("error", "Something went wrong"), response);
    }
  }
}
