package com.u44437.initial_dump.controller;

import com.u44437.initial_dump.error.users.UserNotFound;
import com.u44437.initial_dump.model.users.UserReq;
import com.u44437.initial_dump.service.UsersService;
import com.u44437.initial_dump.service.UsersServiceImpl;
import java.sql.SQLException;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
  private final UsersService usersService;

  public UsersController(UsersServiceImpl usersService) {
    this.usersService = usersService;
  }

  @GetMapping({"", "/"})
  public ResponseEntity getUsers() {
    try {
      return ResponseEntity.ok(Optional.of(usersService.getUsers()));
    } catch (SQLException e) {
      return ResponseEntity.badRequest().body(ResponseMap.getErrorResponse(e));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(ResponseMap.getErrorResponse(e));
    }
  }

  @PostMapping({"", "/"})
  public ResponseEntity createUser(@RequestBody UserReq userReq) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(Optional.of(Map.of("id", usersService.createUser(userReq))));
    } catch (SQLException e) {
      return ResponseEntity.badRequest().body(ResponseMap.getErrorResponse(e));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(ResponseMap.getErrorResponse(e));
    }
  }

  @GetMapping("/{userID}")
  public ResponseEntity getUserByID(@PathVariable int userID) {
    try {
      return ResponseEntity.ok(Optional.ofNullable(usersService.getUserByID(userID)));
    } catch (UserNotFound e) {
      return ResponseEntity.badRequest().body(ResponseMap.getErrorResponse(e));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(ResponseMap.getErrorResponse(e));
    }
  }

  @PutMapping("/{userID}")
  public ResponseEntity updateUser(@PathVariable int userID, @RequestBody UserReq userReq) {
    try {
      usersService.updateUser(userID, userReq);

      return ResponseEntity.noContent().build();
    } catch (SQLException e) {
      return ResponseEntity.badRequest().body(ResponseMap.getErrorResponse(e));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(ResponseMap.getErrorResponse(e));
    }
  }

  @DeleteMapping("/{userID}")
  public ResponseEntity deleteUser(@PathVariable int userID) {
    try {
      usersService.deleteUser(userID);

      return ResponseEntity.noContent().build();
    } catch (SQLException e) {
      return ResponseEntity.badRequest().body(ResponseMap.getErrorResponse(e));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(ResponseMap.getErrorResponse(e));
    }
  }
}
