package com.u44437.initial_dump.service;

import com.u44437.initial_dump.model.users.UserReq;
import com.u44437.initial_dump.model.users.UserRes;
import com.u44437.initial_dump.util.USERS_REQUEST_STATUS;
import java.util.List;

public interface UsersService {
  List<UserRes> getUsers();

  int createUser(UserReq userReq);

  UserRes getUserByID(int userID);

  USERS_REQUEST_STATUS updateUser(int userID, UserReq userReq);

  USERS_REQUEST_STATUS deleteUser(int userID);
}
