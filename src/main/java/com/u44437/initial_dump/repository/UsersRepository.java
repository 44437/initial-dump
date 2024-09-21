package com.u44437.initial_dump.repository;

import com.u44437.initial_dump.model.users.UserDB;
import com.u44437.initial_dump.model.users.UserReq;
import com.u44437.initial_dump.util.USERS_REQUEST_STATUS;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsersRepository implements UsersDao{
  private final DataSource dataSource;

  public UsersRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public List<UserDB> getUsers() {
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement("SELECT * from users");
         ResultSet resultSet = ps.executeQuery()
    ) {

      List<UserDB> userDBList = new ArrayList<>();
      while (resultSet.next()){
        userDBList.add(toUserDB(resultSet));
      }

      return userDBList;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int createUser(UserReq userReq) {
    // What a try-catch: they will be closed after the use, called try-with-resources
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement("INSERT INTO users (name, surname) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
         //there is something bizarre here
        ) {
        ps.setString(1, userReq.getName());
        ps.setString(2, userReq.getSurname());

        ps.executeUpdate();
         ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
          return generatedKeys.getInt(1);
        }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return 0;
  }

  @Override
  public UserDB getUserByID(int userID) {
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement("SELECT * from users WHERE id = ?")
    ) {
      ps.setInt(1, userID);

      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return toUserDB(rs);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return null;
  }

  @Override
  public USERS_REQUEST_STATUS updateUser(int userID, UserReq userReq) {
    String sql = String.format("UPDATE users SET %s%s WHERE id = %d",
            userReq.getName() != null ? "name = '" + userReq.getName() + "'" : "",
            userReq.getSurname() != null ? ", surname = '" + userReq.getSurname() + "'" : "",
            userID);

    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)
    ) {

      ps.executeUpdate();

      return USERS_REQUEST_STATUS.SUCCESS;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public USERS_REQUEST_STATUS deleteUser(int userID) {
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id = ?")
    ) {
      ps.setInt(1, userID);

      ps.executeUpdate();
      return USERS_REQUEST_STATUS.SUCCESS;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private UserDB toUserDB(ResultSet resultSet) throws SQLException {
    return new UserDB(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("surname"));
  }
}
