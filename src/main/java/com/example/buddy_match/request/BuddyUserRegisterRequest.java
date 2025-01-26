package com.example.buddy_match.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class BuddyUserRegisterRequest implements Serializable {
  private static final long serialVerisonUID = 3191241716373120793L;

  private String username;

  private String password;

  private String checkPassword;
}
