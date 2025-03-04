package com.example.buddy_match.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class TeamAddedRequest implements Serializable {
  private static final long serialVerisonUID = 3191241232371631710L;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column team.description
   *
   * @mbg.generated Sat Jan 25 15:41:16 CST 2025
   */
  private String description;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column team.max_num
   *
   * @mbg.generated Sat Jan 25 15:41:16 CST 2025
   */
  private Integer maxNum;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column team.expire_time
   *
   * @mbg.generated Sat Jan 25 15:41:16 CST 2025
   */
  private Date expireTime;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column team.user_id
   *
   * @mbg.generated Sat Jan 25 15:41:16 CST 2025
   */
  private Long userId;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column team.team_status
   *
   * @mbg.generated Sat Jan 25 15:41:16 CST 2025
   */
  private Byte teamStatus;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column team.team_name
   *
   * @mbg.generated Sat Jan 25 15:41:16 CST 2025
   */
  private String teamName;

  /**
   *
   * This field was generated by MyBatis Generator.
   * This field corresponds to the database column team.password
   *
   * @mbg.generated Sat Jan 25 15:41:16 CST 2025
   */
  private String password;
}
