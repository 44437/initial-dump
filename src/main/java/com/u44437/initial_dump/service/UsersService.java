package com.u44437.initial_dump.service;

import com.u44437.initial_dump.model.users.UserDB;
import com.u44437.initial_dump.model.users.UserReq;
import com.u44437.initial_dump.model.users.UserRes;
import com.u44437.initial_dump.repository.UsersRepository;
import com.u44437.initial_dump.util.USERS_REQUEST_STATUS;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
  private final UsersRepository usersRepository;

  public UsersService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  public List<UserRes> getUsers() {
    final ArrayList<UserRes> userResList = new ArrayList<>();
    for (UserDB userDB: usersRepository.getUsers()) {
      userResList.add(new UserRes(
        userDB.id(),
        userDB.name(),
        userDB.surname()
      ));
    }

    return userResList;
  }

  public int createUser(UserReq userReq) {
    return usersRepository.createUser(userReq);
  }

  public UserRes getUserByID(int userID) {
    final UserDB userDB = usersRepository.getUserByID(userID);
    if (userDB != null){
      return new UserRes(
        userDB.id(),
        userDB.name(),
        userDB.surname()
      );
    }

    return null;
  }

  public USERS_REQUEST_STATUS updateUser(int userID, UserReq userReq) {
    return usersRepository.updateUser(userID, userReq);
  }
  public USERS_REQUEST_STATUS deleteUser(int userID) {
    return usersRepository.deleteUser(userID);
  }
}
