package com.u44437.initial_dump.repository;

import com.u44437.initial_dump.model.users.UserDB;
import com.u44437.initial_dump.model.users.UserReq;
import com.u44437.initial_dump.util.USERS_REQUEST_STATUS;

import java.util.List;

public interface UsersDao {
  List<UserDB> getUsers();
  int createUser(UserReq userReq);
  UserDB getUserByID(int userID);
  USERS_REQUEST_STATUS updateUser(int userID, UserReq userReq);
  USERS_REQUEST_STATUS deleteUser(int userID);
}
