package com.example.buddy_match.contorller;


import com.example.buddy_match.exception.ErrorCode;
import com.example.buddy_match.model.atest.BuddyUser;
import com.example.buddy_match.model.atest.Team;
import com.example.buddy_match.model.atest.TeamUser;
import com.example.buddy_match.request.TeamAddedRequest;
import com.example.buddy_match.service.BuddyUserServiceImpl;
import com.example.buddy_match.service.TeamServiceImpl;
import com.example.buddy_match.service.TeamUserService;
import com.example.buddy_match.service.TeamUserServiceImpl;
import com.example.buddy_match.util.ApiResponse;
import com.example.buddy_match.util.CustomPageImpl;
import com.example.buddy_match.util.ThrowUtils;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a generated interface for demonstration purposes.
 */
@RestController
@RequestMapping("/team")
public class TeamController {
    /** This is an example service. */
    @Resource
    private BuddyUserServiceImpl buddyUserServiceImpl;

    @Resource
    private TeamUserServiceImpl teamUserService;

    @Resource
    private TeamServiceImpl service;

    public TeamController(TeamServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<CustomPageImpl<Team>>> all(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ApiResponse.success(service.all(page,size));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Team>> create(@RequestBody Team newTeam) {
        return ApiResponse.success(service.create(newTeam));
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<ApiResponse<Team>> one(@PathVariable Long id) {
        return ApiResponse.success(service.one(id));
    }

    @PutMapping("/replaceTeam/{id}")
    public ResponseEntity<ApiResponse<Team>> replaceTeam(@RequestBody Team newTeam, @PathVariable Long id) {
        return ApiResponse.success(service.replaceTeam(newTeam,id));
    }

    @DeleteMapping("/deleteTeam/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteTeam(@PathVariable Long id) {
        return ApiResponse.success(service.deleteTeam(id));
    }

    @GetMapping("/getTeamByPage")
    public ResponseEntity<ApiResponse<CustomPageImpl<Team>>> getTeamByPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int status,
            HttpServletRequest request) {

        BuddyUser loginUser = buddyUserServiceImpl.getCurrent(request);
        if (loginUser == null)
            return ApiResponse.fail(40010, "用户未登录");

        // String redisKey = String.format("local.team.getTeamByPage.%s.%s",
        // loginUser.getId(), status);
        // ValueOperations<String, Object> valueOperations =
        // redisTemplate.opsForValue();

        // CustomPageImpl<Team> teamList = (CustomPageImpl<Team>)
        // valueOperations.get(redisKey);
        // if (teamList != null) {
        // // System.out.println("命中缓存");
        // return ApiResponse.success(teamList);
        // }

        // System.out.println("命中未缓存");
        System.out.println(status);

        // try {
        CustomPageImpl<Team> teamList = service.getTeamByPage(page, size, status);
        // valueOperations.set(redisKey, teamList, 60, TimeUnit.SECONDS);

        // } catch (Exception e) {
        // // TODO: handle exception
        // e.printStackTrace();
        // }

        return ApiResponse.success(teamList);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Team>> addTeam(@RequestBody TeamAddedRequest entity, HttpServletRequest request) {
        BuddyUser loginUser = buddyUserServiceImpl.getCurrent(request);
        if (loginUser == null)
            return ApiResponse.fail(40010, "用户未登录");

        BuddyUser user = buddyUserServiceImpl.getCurrent(request);

        Team team = new Team();
        team.setDescription(entity.getDescription());
        team.setExpireTime(entity.getExpireTime());
        team.setMaxNum(entity.getMaxNum());
        team.setPassword(entity.getPassword());
        team.setTeamName(entity.getTeamName());
        team.setTeamStatus(entity.getTeamStatus());
        team.setCreateTime(new Date());
        team.setUpdateTime(new Date());
        team.setUserId(user.getId());

        Team  _team = service.create(team);
        //_team = service.one(_team.getId());
        _team.setHasJoin((byte) 1);
        _team.setHasJoinNum((byte) 1);
        
        TeamUser teamUser = teamUserService.joinTeam(user.getId(), _team.getId());

        ThrowUtils.throwIf(teamUser==null, ErrorCode.PARAMS_ERROR);

        return ApiResponse.success(team);
    }

}