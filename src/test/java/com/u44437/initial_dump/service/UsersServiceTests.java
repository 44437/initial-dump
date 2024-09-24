package com.u44437.initial_dump.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.u44437.initial_dump.model.users.UserDB;
import com.u44437.initial_dump.model.users.UserReq;
import com.u44437.initial_dump.model.users.UserRes;
import com.u44437.initial_dump.repository.UsersRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@AutoConfigureMockMvc
@WebMvcTest(UsersService.class)
public class UsersServiceTests {
  @MockBean private UsersRepository mockUsersRepository;
  UsersService usersService;

  @BeforeEach
  void createUserService() {
    usersService = new UsersServiceImpl(mockUsersRepository);
  }

  @Nested
  class GetUsers {
    @Test
    void Should_ReturnUsers() throws Exception {
      when(mockUsersRepository.getUsers())
          .thenReturn(new ArrayList<>(Arrays.asList(new UserDB(1, "John", "Doe"))));

      List<UserRes> users = usersService.getUsers();

      assertEquals(1, users.size());
      assertEquals(users.get(0), new UserRes(1, "John", "Doe"));
    }
  }

  @Nested
  class CreateUser {
    @Test
    void Should_CreateUser() throws Exception {
      when(mockUsersRepository.createUser(new UserReq("John", "Doe"))).thenReturn(1);

      assertEquals(1, usersService.createUser(new UserReq("John", "Doe")));
    }
  }

  @Nested
  class GetUserByID {
    @Test
    void Should_ReturnUser() throws Exception {
      when(mockUsersRepository.getUserByID(1)).thenReturn(new UserDB(1, "John", "Doe"));

      UserRes userRes = usersService.getUserByID(1);

      assertEquals(userRes, new UserRes(1, "John", "Doe"));
    }

    @Test
    void Should_ReturnNull_When_UserNull() throws Exception {
      when(mockUsersRepository.getUserByID(1)).thenReturn(null);

      UserRes userRes = usersService.getUserByID(1);

      assertNull(userRes);
    }
  }

  @Nested
  class UpdateUser {
    @Test
    void Should_UpdateUser() throws Exception {
      doNothing().when(mockUsersRepository).updateUser(eq(1), any(UserReq.class));

      usersService.updateUser(1, new UserReq("Jane", "Doe"));

      verify(mockUsersRepository, times(1)).updateUser(eq(1), any(UserReq.class));
    }
  }

  @Nested
  class DeleteUser {
    @Test
    void Should_DeleteUser() throws Exception {
      doNothing().when(mockUsersRepository).deleteUser(1);

      usersService.deleteUser(1);

      verify(mockUsersRepository, times(1)).deleteUser(1);
    }
  }
}
