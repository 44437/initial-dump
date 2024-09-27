package com.u44437.initial_dump.repository;

import com.u44437.initial_dump.error.users.UserNotFound;
import com.u44437.initial_dump.model.users.UserDB;
import com.u44437.initial_dump.model.users.UserReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class UsersRepository implements UsersDao {
  private final DataSource dataSource;

  public UsersRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Cacheable(cacheNames = "users")
  @Override
  public List<UserDB> getUsers() throws Exception {
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * from users");
        ResultSet resultSet = ps.executeQuery()) {

      List<UserDB> userDBList = new ArrayList<>();
      while (resultSet.next()) {
        userDBList.add(toUserDB(resultSet));
      }

      return userDBList;
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new SQLException(e);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new Exception(e);
    }
  }

  @CacheEvict(cacheNames = "users", allEntries = true)
  @Override
  public int createUser(UserReq userReq) throws Exception {
    // What a try-catch: they will be closed after the use, called try-with-resources
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps =
            conn.prepareStatement(
                "INSERT INTO users (name, surname) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, userReq.getName());
      ps.setString(2, userReq.getSurname());

      ps.executeUpdate();
      ResultSet generatedKeys = ps.getGeneratedKeys();
      if (generatedKeys.next()) {
        return generatedKeys.getInt(1);
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new SQLException(e);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new Exception(e);
    }

    log.error("User couldn't be created");
    throw new Exception();
  }

  @Cacheable(cacheNames = "user", key = "#userID")
  @Override
  public UserDB getUserByID(int userID) throws Exception {
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * from users WHERE id = ?")) {
      ps.setInt(1, userID);

      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return toUserDB(rs);
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new UserNotFound(e);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new Exception(e);
    }

    log.error("User not found");
    throw new UserNotFound();
  }

  @CacheEvict(cacheNames = "user", key = "#userID")
  @Override
  public void updateUser(int userID, UserReq userReq) throws Exception {
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps =
            conn.prepareStatement(
                String.format(
                    "UPDATE users SET %s%s WHERE id = %d",
                    userReq.getName() != null ? "name = '" + userReq.getName() + "'" : "",
                    userReq.getSurname() != null
                        ? ", surname = '" + userReq.getSurname() + "'"
                        : "",
                    userID))) {

      ps.executeUpdate();
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new SQLException(e);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new Exception(e);
    }
  }

  @CacheEvict(cacheNames = "user", key = "#userID")
  @Override
  public void deleteUser(int userID) throws Exception {
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
      ps.setInt(1, userID);

      ps.executeUpdate();
    } catch (SQLException e) {
      log.error(e.getMessage());
      throw new SQLException(e);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new Exception(e);
    }
  }

  protected UserDB toUserDB(ResultSet resultSet) throws Exception {
    return new UserDB(
        resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("surname"));
  }
}
