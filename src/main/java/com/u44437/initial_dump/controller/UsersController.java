package com.u44437.initial_dump.controller;

import com.u44437.initial_dump.model.users.UserReq;
import com.u44437.initial_dump.model.users.UserRes;
import com.u44437.initial_dump.service.UsersService;
import com.u44437.initial_dump.util.USERS_REQUEST_STATUS;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UsersController {
  private final UsersService usersService;
  public UsersController(UsersService usersService) {
    this.usersService = usersService;
  }

  @GetMapping({"", "/"})
  public ResponseEntity<Optional<List<UserRes>>> getUsers(){
    return ResponseEntity.ok(Optional.of(usersService.getUsers()));
  }

  @PostMapping({"", "/"})
  public ResponseEntity<Optional<Map<String, Integer>>> createUser(@RequestBody UserReq userReq){
    return ResponseEntity.status(HttpStatus.CREATED).body(Optional.of(Map.of("id", usersService.createUser(userReq))));
  }

  @GetMapping( "/{userID}")
  public ResponseEntity<Optional<UserRes>> getUserByID(@PathVariable int userID){

    final UserRes userRes = usersService.getUserByID(userID);
    if (userRes != null){
      return ResponseEntity.ok(Optional.of(userRes));
    }

    return ResponseEntity.badRequest().build();
  }

  @PutMapping( "/{userID}")
  public ResponseEntity updateUser(@PathVariable int userID, @RequestBody UserReq userReq){

    if (USERS_REQUEST_STATUS.SUCCESS == usersService.updateUser(userID, userReq)){
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.badRequest().build();
  }

  @DeleteMapping( "/{userID}")
  public ResponseEntity deleteUser(@PathVariable int userID){
    if (USERS_REQUEST_STATUS.SUCCESS == usersService.deleteUser(userID)){
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.badRequest().build();
  }
}
