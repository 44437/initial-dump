package com.u44437.initial_dump.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.u44437.initial_dump.error.users.UserNotFound;
import com.u44437.initial_dump.model.users.UserDB;
import com.u44437.initial_dump.model.users.UserReq;
import java.sql.*;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@AutoConfigureMockMvc
@WebMvcTest(UsersRepository.class)
class UsersRepositoryTests {
  UsersRepository usersRepository;

  @MockBean private DataSource mockDataSource;
  @Mock private Connection mockConnection;
  @Mock private PreparedStatement mockPreparedStatement;
  @Mock private ResultSet mockResultSet;
  @Mock private UserReq mockUserReq;

  @BeforeEach
  void createUsersRepository() throws SQLException {
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockPreparedStatement.executeUpdate()).thenReturn(1);
    when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
        .thenReturn(mockPreparedStatement);
    when(mockDataSource.getConnection()).thenReturn(mockConnection);

    usersRepository = new UsersRepository(mockDataSource);
  }

  @Nested
  class GetUsers {
    @Test
    void Should_ReturnUsers() throws Exception {
      when(mockResultSet.next()).thenReturn(true, false);
      when(mockResultSet.getInt("id")).thenReturn(1);
      when(mockResultSet.getString("name")).thenReturn("John");
      when(mockResultSet.getString("surname")).thenReturn("Doe");

      List<UserDB> users = usersRepository.getUsers();

      assertEquals(1, users.size());
      assertEquals(users.get(0), new UserDB(1, "John", "Doe"));
    }

    @Test
    void Should_Fail_When_SQLException() throws Exception {
      doThrow(new SQLException()).when(mockResultSet).next();
      assertThrows(SQLException.class, () -> usersRepository.getUsers());
    }

    @Test
    void Should_Fail_When_Exception() throws Exception {
      when(mockResultSet.next()).thenReturn(true, false);
      assertThrows(Exception.class, () -> usersRepository.getUsers());
    }
  }

  @Nested
  class CreateUser {
    @Test
    void Should_CreateUser() throws Exception {
      when(mockResultSet.next()).thenReturn(true, false);
      when(mockResultSet.getInt(1)).thenReturn(12);

      assertEquals(12, usersRepository.createUser(new UserReq("John", "Doe")));
    }

    @Test
    void Should_Fail_When_SQLException() throws Exception {
      doThrow(new SQLException()).when(mockPreparedStatement).getGeneratedKeys();
      assertThrows(
          SQLException.class, () -> usersRepository.createUser(new UserReq("John", "Doe")));
    }

    @Test
    void Should_Fail_When_Exception() throws Exception {
      doThrow(new RuntimeException()).when(mockUserReq).getSurname();
      assertThrows(Exception.class, () -> usersRepository.createUser(mockUserReq));
    }

    @Test
    void Should_Fail_When_UserIDNotFound() throws Exception {
      when(mockResultSet.next()).thenReturn(false);
      assertThrows(Exception.class, () -> usersRepository.createUser(mockUserReq));
    }
  }

  @Nested
  class GetUserByID {
    @Test
    void Should_ReturnUser() throws Exception {
      when(mockResultSet.next()).thenReturn(true, false);
      when(mockResultSet.getInt("id")).thenReturn(1);
      when(mockResultSet.getString("name")).thenReturn("John");
      when(mockResultSet.getString("surname")).thenReturn("Doe");

      assertEquals(new UserDB(1, "John", "Doe"), usersRepository.getUserByID(1));
    }

    @Test
    void Should_Fail_When_SQLException() throws Exception {
      doThrow(new SQLException()).when(mockPreparedStatement).executeQuery();
      assertThrows(UserNotFound.class, () -> usersRepository.getUserByID(1));
    }

    @Test
    void Should_Fail_When_Exception() throws Exception {
      when(mockResultSet.next()).thenReturn(true, false);
      assertThrows(Exception.class, () -> usersRepository.getUserByID(1));
    }

    @Test
    void Should_Fail_When_UserNotFound() throws Exception {
      when(mockResultSet.next()).thenReturn(false);
      assertThrows(UserNotFound.class, () -> usersRepository.getUserByID(1));
    }
  }

  @Nested
  class UpdateUser {
    @Test
    void Should_UpdateUser() throws Exception {
      usersRepository.updateUser(1, new UserReq("Jane", "Doe"));
      verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void Should_Fail_When_SQLException() throws Exception {
      doThrow(new SQLException()).when(mockPreparedStatement).executeUpdate();
      assertThrows(
          SQLException.class, () -> usersRepository.updateUser(1, new UserReq("Jane", "Doe")));
    }

    @Test
    void Should_Fail_When_Exception() throws Exception {
      doThrow(new RuntimeException()).when(mockUserReq).getSurname();
      assertThrows(Exception.class, () -> usersRepository.updateUser(1, mockUserReq));
    }
  }

  @Nested
  class DeleteUser {
    @Test
    void Should_DeleteUser() throws Exception {
      usersRepository.deleteUser(1);
      verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void Should_Fail_When_SQLException() throws Exception {
      doThrow(new SQLException()).when(mockPreparedStatement).executeUpdate();
      assertThrows(SQLException.class, () -> usersRepository.deleteUser(1));
    }
  }

  @SneakyThrows
  @Test
  void ToUserDB() {
    when(mockResultSet.getInt("id")).thenReturn(1);
    when(mockResultSet.getString("name")).thenReturn("John");
    when(mockResultSet.getString("surname")).thenReturn("Doe");

    assertEquals(new UserDB(1, "John", "Doe"), usersRepository.toUserDB(mockResultSet));
  }
}
