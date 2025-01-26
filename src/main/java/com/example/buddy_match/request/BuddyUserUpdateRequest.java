package com.example.buddy_match.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BuddyUserUpdateRequest implements Serializable {
  private static final long serialVerisonUID = 3191241716373120790L;

  private Long id;

  private String fieldKey;

  private String fieldValue;
}
