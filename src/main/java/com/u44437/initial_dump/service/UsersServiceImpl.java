package com.u44437.initial_dump.service;

import com.u44437.initial_dump.model.users.UserDB;
import com.u44437.initial_dump.model.users.UserReq;
import com.u44437.initial_dump.model.users.UserRes;
import com.u44437.initial_dump.repository.UsersRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
  private final UsersRepository usersRepository;
  private final Logger logger;

  public UsersServiceImpl(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
    this.logger = LoggerFactory.getLogger(UsersServiceImpl.class);
  }

  @Override
  public List<UserRes> getUsers() throws Exception {
    final ArrayList<UserRes> userResList = new ArrayList<>();
    for (UserDB userDB : usersRepository.getUsers()) {
      userResList.add(new UserRes(userDB.id(), userDB.name(), userDB.surname()));
    }

    return userResList;
  }

  @Override
  public int createUser(UserReq userReq) throws Exception {
    return usersRepository.createUser(userReq);
  }

  @Override
  public UserRes getUserByID(int userID) throws Exception {
    final UserDB userDB = usersRepository.getUserByID(userID);
    if (userDB != null) {
      return new UserRes(userDB.id(), userDB.name(), userDB.surname());
    }

    logger.info("User not found");
    return null;
  }

  @Override
  public void updateUser(int userID, UserReq userReq) throws Exception {
    usersRepository.updateUser(userID, userReq);
  }

  @Override
  public void deleteUser(int userID) throws Exception {
    usersRepository.deleteUser(userID);
  }
}
