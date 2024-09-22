package com.u44437.initial_dump.repository;

import com.u44437.initial_dump.error.users.UserNotFound;
import com.u44437.initial_dump.model.users.UserDB;
import com.u44437.initial_dump.model.users.UserReq;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public class UsersRepository implements UsersDao {
  private final DataSource dataSource;

  public UsersRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

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
      throw new SQLException(e);
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

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
      throw new SQLException(e);
    } catch (Exception e) {
      throw new Exception(e);
    }

    throw new Exception();
  }

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
      throw new UserNotFound(e);
    } catch (Exception e) {
      throw new Exception(e);
    }

    throw new UserNotFound();
  }

  @Override
  public void updateUser(int userID, UserReq userReq) throws Exception {
    String sql =
        String.format(
            "UPDATE users SET %s%s WHERE id = %d",
            userReq.getName() != null ? "name = '" + userReq.getName() + "'" : "",
            userReq.getSurname() != null ? ", surname = '" + userReq.getSurname() + "'" : "",
            userID);

    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.executeUpdate();
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  @Override
  public void deleteUser(int userID) throws Exception {
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
      ps.setInt(1, userID);

      ps.executeUpdate();
    } catch (SQLException e) {
      throw new SQLException(e);
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  private UserDB toUserDB(ResultSet resultSet) throws SQLException {
    return new UserDB(
        resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("surname"));
  }
}
