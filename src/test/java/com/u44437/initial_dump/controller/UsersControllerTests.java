package com.u44437.initial_dump.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.u44437.initial_dump.error.users.UserNotFound;
import com.u44437.initial_dump.model.users.UserReq;
import com.u44437.initial_dump.model.users.UserRes;
import com.u44437.initial_dump.service.UsersServiceImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest(UsersController.class)
public class UsersControllerTests {
  @Autowired private MockMvc mockMvc;
  @MockBean private UsersServiceImpl mockUsersService;

  @Nested
  class GetUsers {
    @Test
    void Should_ReturnUsers() throws Exception {
      when(mockUsersService.getUsers())
          .thenReturn(new ArrayList<>(Arrays.asList(new UserRes(1, "John", "Doe"))))
          .thenReturn(new ArrayList<>());

      mockMvc
          .perform(get("/users"))
          .andExpect(status().isOk())
          .andExpect(content().json("[{id: 1, name:  'John', surname:  'Doe'}]"));
      mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    void Should_Fail_When_SQLException() throws Exception {
      when(mockUsersService.getUsers()).thenThrow(new SQLException());

      mockMvc
          .perform(get("/users"))
          .andExpect(status().isBadRequest())
          .andExpect(content().json("{error:  'Something went wrong'}"));
    }

    @Test
    void Should_Fail_When_Exception() throws Exception {
      when(mockUsersService.getUsers()).thenThrow(new Exception("Exception"));

      mockMvc
          .perform(get("/users"))
          .andExpect(status().isInternalServerError())
          .andExpect(content().json("{error:  'Exception'}"));
    }
  }

  @Nested
  class CreateUser {
    @Test
    void Should_CreateUser() throws Exception {
      when(mockUsersService.createUser(any(UserReq.class))).thenReturn(1);

      mockMvc
          .perform(
              post("/users")
                  .content("{\"name\": \"John\", \"surname\": \"Doe\"}")
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated())
          .andExpect(content().json("{id: 1}"));
    }

    @Test
    void Should_Fail_When_SQLException() throws Exception {
      when(mockUsersService.createUser(any(UserReq.class))).thenThrow(new SQLException());

      mockMvc
          .perform(
              post("/users")
                  .content("{\"name\": \"John\", \"surname\": \"Doe\"}")
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isBadRequest())
          .andExpect(content().json("{error:  'Something went wrong'}"));
    }

    @Test
    void Should_Fail_When_Exception() throws Exception {
      when(mockUsersService.createUser(any(UserReq.class))).thenThrow(new Exception("Exception"));

      mockMvc
          .perform(
              post("/users")
                  .content("{\"name\": \"John\", \"surname\": \"Doe\"}")
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isInternalServerError())
          .andExpect(content().json("{error:  'Exception'}"));
    }
  }

  @Nested
  class GetUserByID {
    @Test
    void Should_ReturnUser() throws Exception {
      when(mockUsersService.getUserByID(1)).thenReturn(new UserRes(1, "John", "Doe"));

      mockMvc
          .perform(get("/users/1"))
          .andExpect(status().isOk())
          .andExpect(content().json("{id: 1, name:  'John', surname:  'Doe'}"));
    }

    @Test
    void Should_Fail_When_UserNotFound() throws Exception {
      when(mockUsersService.getUserByID(1)).thenThrow(new UserNotFound("User Not Found"));

      mockMvc
          .perform(get("/users/1"))
          .andExpect(status().isBadRequest())
          .andExpect(content().json("{error:  'User Not Found'}"));
    }

    @Test
    void Should_Fail_When_Exception() throws Exception {
      when(mockUsersService.getUserByID(1)).thenThrow(new Exception("Exception"));

      mockMvc
          .perform(get("/users/1"))
          .andExpect(status().isInternalServerError())
          .andExpect(content().json("{error:  'Exception'}"));
    }
  }

  @Nested
  class UpdateUser {
    @Test
    void Should_UpdateUser() throws Exception {
      doNothing().when(mockUsersService).updateUser(eq(1), any(UserReq.class));

      mockMvc
          .perform(
              put("/users/1")
                  .content("{\"surname\": \"Doe\"}")
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
    }

    @Test
    void Should_Fail_When_SQLException() throws Exception {
      doThrow(new SQLException()).when(mockUsersService).updateUser(eq(1), any(UserReq.class));

      mockMvc
          .perform(
              put("/users/1")
                  .content("{\"surname\": \"Doe\"}")
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isBadRequest())
          .andExpect(content().json("{error:  'Something went wrong'}"));
    }

    @Test
    void Should_Fail_When_Exception() throws Exception {
      doThrow(new Exception("Exception")).when(mockUsersService).updateUser(eq(1), any(UserReq.class));

      mockMvc
          .perform(
              put("/users/1")
                  .content("{\"surname\": \"Doe\"}")
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isInternalServerError())
          .andExpect(content().json("{error:  'Exception'}"));
    }
  }

  @Nested
  class DeleteUser {
    @Test
    void Should_DeleteUser() throws Exception {
      doNothing().when(mockUsersService).deleteUser(1);

      mockMvc.perform(delete("/users/1")).andExpect(status().isNoContent());
    }

    @Test
    void Should_Fail_When_SQLException() throws Exception {
      doThrow(new SQLException()).when(mockUsersService).deleteUser(1);

      mockMvc
          .perform(delete("/users/1"))
          .andExpect(status().isBadRequest())
          .andExpect(content().json("{error:  'Something went wrong'}"));
    }

    @Test
    void Should_Fail_When_Exception() throws Exception {
      doThrow(new Exception("Exception")).when(mockUsersService).deleteUser(1);

      mockMvc
          .perform(delete("/users/1"))
          .andExpect(status().isInternalServerError())
          .andExpect(content().json("{error:  'Exception'}"));
    }
  }
}
