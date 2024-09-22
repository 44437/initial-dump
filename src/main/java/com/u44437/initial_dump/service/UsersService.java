package com.u44437.initial_dump.service;

import com.u44437.initial_dump.model.users.UserReq;
import com.u44437.initial_dump.model.users.UserRes;
import java.util.List;

public interface UsersService {
  List<UserRes> getUsers() throws Exception;

  int createUser(UserReq userReq) throws Exception;

  UserRes getUserByID(int userID) throws Exception;

  void updateUser(int userID, UserReq userReq) throws Exception;

  void deleteUser(int userID) throws Exception;
}
