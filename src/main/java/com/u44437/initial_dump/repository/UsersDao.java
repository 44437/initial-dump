package com.u44437.initial_dump.repository;

import com.u44437.initial_dump.model.users.UserDB;
import com.u44437.initial_dump.model.users.UserReq;
import java.util.List;

public interface UsersDao {
  List<UserDB> getUsers() throws Exception;

  int createUser(UserReq userReq) throws Exception;

  UserDB getUserByID(int userID) throws Exception;

  void updateUser(int userID, UserReq userReq) throws Exception;

  void deleteUser(int userID) throws Exception;
}
