package com.example.buddy_match.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class BuddyUserLoginRequest implements Serializable {
  private static final long serialVerisonUID = 3191241716373120791L;

  private String username;

  private String password;

}
