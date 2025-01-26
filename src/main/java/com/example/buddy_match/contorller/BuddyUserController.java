package com.example.buddy_match.contorller;

import com.example.buddy_match.dto.CustomPageImpl;
import com.example.buddy_match.model.BuddyUser;
import com.example.buddy_match.request.BuddyUserLoginRequest;
import com.example.buddy_match.request.BuddyUserRegisterRequest;
import com.example.buddy_match.request.BuddyUserUpdateRequest;
import com.example.buddy_match.service.BuddyUserServiceImpl;
import com.example.buddy_match.service.baseService.BadRequestException;
import com.example.buddy_match.service.baseService.BuddyUserNotFoundException;
import com.example.buddy_match.util.ApiResponse;

import ch.qos.logback.core.util.StringUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

/**
 * This is a generated interface for demonstration purposes.
 */
@RestController
@RequestMapping("/buddyuser")
public class BuddyUserController {
    /** This is an example service. */
    @Resource
    private BuddyUserServiceImpl service;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public BuddyUserController(BuddyUserServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        return ApiResponse.success(service.all());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody BuddyUser newBuddyUser) {
        return ApiResponse.success(service.create(newBuddyUser));
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<?> one(@PathVariable Long id) {
        return ApiResponse.success(service.one(id));
    }

    @PutMapping("/replaceBuddyUser/{id}")
    public ResponseEntity<?> replaceBuddyUser(@RequestBody BuddyUser newBuddyUser, @PathVariable Long id) {
        return service.replaceBuddyUser(newBuddyUser,id);
    }

    @DeleteMapping("/deleteBuddyUser/{id}")
    public ResponseEntity<?> deleteBuddyUser(@PathVariable Long id) {
        return service.deleteBuddyUser(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody BuddyUserRegisterRequest buddyUserRegisterRequest) {
        //TODO: process POST request
        if (buddyUserRegisterRequest == null)
        return ResponseEntity.badRequest().build();

        String userName = buddyUserRegisterRequest.getUsername();
        String userPassword = buddyUserRegisterRequest.getPassword();
        String checkPassword = buddyUserRegisterRequest.getCheckPassword();

        if (StringUtil.isNullOrEmpty(userName) || StringUtil.isNullOrEmpty(userPassword) || StringUtil.isNullOrEmpty(checkPassword))
            return ResponseEntity.badRequest().build();
        
        return ApiResponse.success(service.registerUser(userName, userPassword, checkPassword));
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody BuddyUserLoginRequest testUserLoginRequest, HttpServletRequest request) {
        //TODO: process POST request
        if (testUserLoginRequest == null)
            return ApiResponse.fail(40010, "登录请求参数错误");

        String userName = testUserLoginRequest.getUsername();
        String userPassword = testUserLoginRequest.getPassword();

        if (StringUtil.isNullOrEmpty(userName) || StringUtil.isNullOrEmpty(userPassword))
            return ApiResponse.fail(40010, "账号或密码错误");

        BuddyUser  loginUser = service.doLogin(userName, userPassword, request);

        if (loginUser == null)
            return ApiResponse.fail(40010, "账号或密码错误");

        return ApiResponse.success(loginUser);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUser(@RequestParam String username, HttpServletRequest request) {

        Object userObj = request.getSession().getAttribute(BuddyUserServiceImpl.USER_LOGIN_STATE);
        BuddyUser user = (BuddyUser)userObj;

        if (user == null)
            ApiResponse.fail(40010, "用户未登录");


        if(!StringUtils.isNotBlank(username)) 
            return ResponseEntity.noContent().build();

        return ApiResponse.success(service.searchUser(username)) ;
    }

    @GetMapping("/logout")
    public ResponseEntity<?> userLogout(HttpServletRequest httpServletRequest) {
       int res = service.userLogout(httpServletRequest);

       if (res == 1)
            return ApiResponse.success("注销成功");

        return ApiResponse.fail(40050, "注销失败");
    }
    

    @GetMapping("/current")
    public ResponseEntity<?> getCurrent(HttpServletRequest httpServletRequest) {

        BuddyUser buddyUser = service.getCurrent(httpServletRequest);

        if (buddyUser == null)
            return ApiResponse.fail(40010, "用户还没有登录");

        return ApiResponse.success(buddyUser);
    }

    @GetMapping("/search/tags")
    public ResponseEntity<?> searchUserByTags(@RequestParam List<String> tagNameList, HttpServletRequest httpServletRequest) {
        BuddyUser buddyUser = service.getCurrent(httpServletRequest);

        if (buddyUser == null)
            return ApiResponse.fail(40010, "用户还没有登录");

        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BuddyUserNotFoundException();
        }
        // for (var tag : tagNameList)
        //     System.out.println(tag);

        CollectionModel<EntityModel<BuddyUser>> userList = service.searchUserByTags(tagNameList);

        if (userList.getContent().isEmpty())
            throw new BuddyUserNotFoundException();

        return ResponseEntity.ok().body(userList);
    }

    @PostMapping("/updateBuddyUser/")
    public ResponseEntity<?> updateBuddyUser(@RequestBody BuddyUserUpdateRequest buddyUserUpdateRequest, HttpServletRequest httpServletRequest) {
        //service.userLogout(httpServletRequest);
        
         try {
            EntityModel<BuddyUser> res = (EntityModel<BuddyUser>)service.updateBuddyUser(buddyUserUpdateRequest).getBody();

            if (res == null)
                throw new BadRequestException("更新用户信息失败");
            
            BuddyUser safetyUser = service.getSafetyUser(Optional.of(res.getContent()));

            httpServletRequest.getSession().setAttribute(BuddyUserServiceImpl.USER_LOGIN_STATE, safetyUser);

            return ApiResponse.success(res);
         } catch (Exception e) {
            // TODO: handle exception
         }

         return ApiResponse.fail(40020, "更新用户失败");
        
    }    



    @GetMapping("/getUsersByPage")
    public ResponseEntity<?> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        BuddyUser loginUser = service.getCurrent(request);
        if (loginUser == null)
            return ApiResponse.fail(40010, "用户未登录");
        // systemId.moduleId.func
        // local.buddyuser.getusers.id
        String redisKey = String.format("local.buddyuser.getusers.%s", loginUser.getId());
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        
        CustomPageImpl<BuddyUser> userPage = (CustomPageImpl<BuddyUser>) valueOperations.get(redisKey);
        if (userPage != null) {
            // System.out.println("缓存命中");
            return ApiResponse.success(userPage);
        }

        try {
            userPage = service.getUsers(page, size);
            valueOperations.set(redisKey, userPage, 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return  ApiResponse.success(service.getUsers(page, size));
    }

    @GetMapping("/match")
    public ResponseEntity<?> matchUsers(@RequestParam(defaultValue = "3") long num, HttpServletRequest request) {
        if (num <= 0 || num > 20) {
            throw new BadRequestException("函数matchUsers参数错误");
        }
        BuddyUser currentUser = service.getCurrent(request);
        if (currentUser == null)
            throw new BadRequestException("用户未登录");
            
        return ApiResponse.success(service.matchUsers(num, currentUser));
    }

}