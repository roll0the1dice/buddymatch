package com.example.buddy_match.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@DynamicInsert
public class TeamUser {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team_user.id
     *
     * @mbg.generated Sat Jan 25 15:41:16 CST 2025
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team_user.user_id
     *
     * @mbg.generated Sat Jan 25 15:41:16 CST 2025
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team_user.team_id
     *
     * @mbg.generated Sat Jan 25 15:41:16 CST 2025
     */
    private Long teamId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team_user.create_time
     *
     * @mbg.generated Sat Jan 25 15:41:16 CST 2025
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team_user.update_time
     *
     * @mbg.generated Sat Jan 25 15:41:16 CST 2025
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column team_user.is_delete
     *
     * @mbg.generated Sat Jan 25 15:41:16 CST 2025
     */
    private Byte isDelete;
}