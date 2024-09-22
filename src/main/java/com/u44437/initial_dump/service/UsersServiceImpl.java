package com.u44437.initial_dump.service;

import com.u44437.initial_dump.model.users.UserDB;
import com.u44437.initial_dump.model.users.UserReq;
import com.u44437.initial_dump.model.users.UserRes;
import com.u44437.initial_dump.repository.UsersRepository;
import com.u44437.initial_dump.util.USERS_REQUEST_STATUS;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
  private final UsersRepository usersRepository;

  public UsersServiceImpl(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  @Override
  public List<UserRes> getUsers() {
    final ArrayList<UserRes> userResList = new ArrayList<>();
    for (UserDB userDB : usersRepository.getUsers()) {
      userResList.add(new UserRes(userDB.id(), userDB.name(), userDB.surname()));
    }

    return userResList;
  }

  @Override
  public int createUser(UserReq userReq) {
    return usersRepository.createUser(userReq);
  }

  @Override
  public UserRes getUserByID(int userID) {
    final UserDB userDB = usersRepository.getUserByID(userID);
    if (userDB != null) {
      return new UserRes(userDB.id(), userDB.name(), userDB.surname());
    }

    return null;
  }

  @Override
  public USERS_REQUEST_STATUS updateUser(int userID, UserReq userReq) {
    return usersRepository.updateUser(userID, userReq);
  }

  @Override
  public USERS_REQUEST_STATUS deleteUser(int userID) {
    return usersRepository.deleteUser(userID);
  }
}
