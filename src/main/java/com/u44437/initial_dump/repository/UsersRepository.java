package com.u44437.initial_dump.repository;

import com.u44437.initial_dump.model.users.UserDB;
import com.u44437.initial_dump.model.users.UserReq;
import com.u44437.initial_dump.util.USERS_REQUEST_STATUS;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsersRepository {
  static ArrayList<UserDB> users = new ArrayList<>();

  public List<UserDB> getUsers() {
    return users;
  }

  public int createUser(UserReq userReq) {
    final int id = users.size();

    users.add(new UserDB(id, userReq.getName(), userReq.getSurname()));

    return id;
  }

  public UserDB getUserByID(int userID) {
    for (UserDB user : users) {
      if (user.getId() == userID) {
        return user;
      }
    }

    return null;
  }

  public USERS_REQUEST_STATUS updateUser(int userID, UserReq userReq) {
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getId() == userID) {
        final UserDB oldUser = users.get(i);
        users.set(i, new UserDB(
          userID,
          userReq.getName() == null ? oldUser.getName(): userReq.getName(),
          userReq.getSurname() == null ? oldUser.getSurname(): userReq.getSurname()
        ));

        return USERS_REQUEST_STATUS.SUCCESS;
      }
    }

    return USERS_REQUEST_STATUS.ERROR;
  }

  public USERS_REQUEST_STATUS deleteUser(int userID) {
    for (UserDB userDB: users) {
      if (userDB.getId() == userID) {
        users.remove(users.get(users.indexOf(userDB)));

        return USERS_REQUEST_STATUS.SUCCESS;
      }
    }

    return USERS_REQUEST_STATUS.ERROR;
  }
}
