package com.example.buddy_match.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.buddy_match.constant.UserConstant;
import com.example.buddy_match.contorller.BuddyUserController;
import com.example.buddy_match.dto.CustomPageImpl;
import com.example.buddy_match.model.BuddyUser;
import com.example.buddy_match.request.BuddyUserUpdateRequest;
import com.example.buddy_match.service.baseService.BadRequestException;
import com.example.buddy_match.service.baseService.BuddyUserBaseService;
import com.example.buddy_match.service.baseService.BuddyUserModelAssembler;
import com.example.buddy_match.service.baseService.BuddyUserNotFoundException;
import com.example.buddy_match.service.baseService.BuddyUserRepository;
import com.example.buddy_match.util.AlgorithmUtils;
import com.example.buddy_match.util.CustomSpecs;
import com.example.buddy_match.util.ValidationUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ch.qos.logback.core.util.StringUtil;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;

/**
 * This is a generated Service for demonstration purposes.
 */
@Service
public class BuddyUserServiceImpl extends BuddyUserBaseService implements BuddyUserService {
    /** This is an example repository. */
    private BuddyUserRepository repository;

    /** This is an example modelAssembler. */
    private BuddyUserModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<BuddyUser> pagedResourcesAssembler;

    public static final String USER_LOGIN_STATE = "USER_LOGIN_STATE";

    public BuddyUserServiceImpl() {
        super();
    }

    @Autowired
    public BuddyUserServiceImpl(BuddyUserRepository repository, BuddyUserModelAssembler assembler) {
        super(repository, assembler);
        this.repository = repository;
        this.assembler = assembler;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);

        return 1;
    }

    public CollectionModel<EntityModel<BuddyUser>> searchUserByTags(List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BuddyUserNotFoundException();
        }

        CustomSpecs<BuddyUser> customSpecs = new CustomSpecs<BuddyUser>();
        for (var tagName : tagNameList) {
            customSpecs = customSpecs._like("tagName", tagName);
        }

        List<EntityModel<BuddyUser>> userList = repository.findAll(customSpecs._generateSpecifications()).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(userList, linkTo(methodOn(BuddyUserController.class).all()).withSelfRel());

    }

    // @Override
    public String registerUser(String userName, String userPassword, String checkPassword) {
        // 验证用户名和密码是否包含特殊字符
        if (ValidationUtil.hasSpecialCharacters(userName)) {
            return "用户名不能包含特殊字符";
        }
        if (ValidationUtil.hasSpecialCharacters(userPassword)) {
            return "密码不能包含特殊字符";
        }
        CustomSpecs<BuddyUser> customSpecs = new CustomSpecs<BuddyUser>();
        customSpecs._equal("username", userName);
        // 检查用户名是否已经存在
        if (!repository.findAll(customSpecs._generateSpecifications()).isEmpty()) {
            return "用户名已存在";
        }

        // 创建并保存用户
        BuddyUser buddyUser = new BuddyUser();
        buddyUser.setUsername(userName);
        buddyUser.setPassword(userPassword); // 注意：建议实际应用中对密码进行加密存储

        repository.save(buddyUser);

        return "用户注册成功";
    }

    // @Override
    public BuddyUser doLogin(String userName, String userPassword, HttpServletRequest httpServletRequest) {
        if (StringUtil.isNullOrEmpty(userName))
            return null;

        if (userName.length() < 4)
            return null;

        if (userPassword.length() < 5)
            return null;

        if (ValidationUtil.hasSpecialCharacters(userName))
            return null;

        if (ValidationUtil.hasSpecialCharacters(userPassword))
            return null;

        CustomSpecs<BuddyUser> customSpecs = new CustomSpecs<BuddyUser>();
        customSpecs._equal("username", userName)._equal("password", userPassword);
        // customSpecs._like("userpassword", "123");

        Optional<BuddyUser> user = repository.findOne(customSpecs._generateSpecifications());

        if (user.isEmpty())
            return null;

        BuddyUser safetyUser = getSafetyUser(user);

        httpServletRequest.getSession().setAttribute(USER_LOGIN_STATE, user.get());

        return safetyUser;
    }

    public BuddyUser getSafetyUser(Optional<BuddyUser> user) {
        BuddyUser safetyUser = new BuddyUser();
        safetyUser.setId(user.get().getId());
        safetyUser.setUsername(user.get().getUsername());
        safetyUser.setPassword("");
        safetyUser.setCreateTime(new Date());
        safetyUser.setUpdateTime(new Date());
        safetyUser.setIsDelete((byte) 0);
        safetyUser.setParentId(0L);
        safetyUser.setIsParent((byte) 0);
        safetyUser.setPersonalBio(user.get().getPersonalBio());
        safetyUser.setTagName(user.get().getTagName());
        safetyUser.setUserId(user.get().getUserId());
        safetyUser.setDisplayName(user.get().getDisplayName());
        safetyUser.setAvatar(user.get().getAvatar());
        return safetyUser;
    }

    public BuddyUser getCurrent(HttpServletRequest httpServletRequest) {

        Object obj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);

        // 检查类型并进行转换
        if (obj instanceof BuddyUser) {
            BuddyUser user = (BuddyUser)obj;
            return  getSafetyUser(Optional.of(user)); // 安全类型转换
        } else {

            return null; // 处理未找到或类型不匹配的情况
        }
    }

    public CollectionModel<EntityModel<BuddyUser>> searchUser(@RequestParam String username) {

        if (!StringUtils.isNotBlank(username))
            return CollectionModel.of(Collections.emptyList());

        CustomSpecs<BuddyUser> customSpecs = new CustomSpecs<BuddyUser>();
        customSpecs._like("username", username);

        List<EntityModel<BuddyUser>> testusers = repository.findAll(customSpecs._generateSpecifications()).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (testusers.isEmpty())
            return CollectionModel.of(Collections.emptyList());

        return CollectionModel.of(testusers, linkTo(methodOn(BuddyUserController.class).all()).withSelfRel());
    }

    public ResponseEntity<?> updateBuddyUser(@RequestBody BuddyUserUpdateRequest buddyUserUpdateRequest) throws IllegalAccessException, NoSuchFieldException  {

        if (StringUtils.isEmpty(buddyUserUpdateRequest.getId().toString()))
            throw new BadRequestException("参数错误");

        CustomSpecs<BuddyUser> customSpecs = new CustomSpecs<>();
        customSpecs._equal("id", buddyUserUpdateRequest.getId());

        Optional<BuddyUser> _findModel = repository.findOne(customSpecs._generateSpecifications());

        if (_findModel.isEmpty())
            throw new BadRequestException("参数错误");

        BuddyUser buddyUser = _findModel.get();

        // System.out.println(buddyUserUpdateRequest.getFieldKey());
        // System.out.println(buddyUserUpdateRequest.getFieldValue());

        try {
            Field BuddyUserFields = BuddyUser.class.getDeclaredField(buddyUserUpdateRequest.getFieldKey());
            BuddyUserFields.setAccessible(true);
            if (buddyUserUpdateRequest.getFieldValue() != null && StringUtils.isNotEmpty(buddyUserUpdateRequest.getFieldValue())) {
                BuddyUserFields.set(buddyUser, buddyUserUpdateRequest.getFieldValue());
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        BuddyUser _updateModel = repository.save(buddyUser);

        EntityModel<BuddyUser> entityModel = assembler.toModel(_updateModel);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public CustomPageImpl<BuddyUser> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        List<BuddyUser> pageUserList = repository.findAll(pageable).toList();

        return new CustomPageImpl<BuddyUser>(pageUserList, pageable, pageUserList.size());
    }

    public boolean isAdmin(HttpServletRequest httpServletRequest) {
        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        BuddyUser buddyUser = (BuddyUser) userObj;
        return buddyUser != null && buddyUser.getUserRole() == UserConstant.ADMIN_ROLE;
    }

    public boolean isAdmin(BuddyUser loginUser) {
        return loginUser != null && loginUser.getUserRole() == UserConstant.ADMIN_ROLE;
    }

    public CollectionModel<BuddyUser> matchUsers(Long num, BuddyUser loginUser) {
        CustomSpecs<BuddyUser> customSpecs = new CustomSpecs<>();
        customSpecs._isNotNull("tagName");
        List<BuddyUser> userList = repository.findAll(customSpecs._generateSpecifications());
        String tags = loginUser.getTagName();
        Gson gson = new Gson();
        List<String> tagList = gson.fromJson(tags, new TypeToken<List<String>>() {
        }.getType());

        List<Pair<BuddyUser, Long>> list = new ArrayList<>();

        for (int i = 0; i < userList.size(); i++) {
            BuddyUser buddyUser = userList.get(i);
            String userTags = buddyUser.getTagName();
            if (StringUtils.isBlank(userTags) || buddyUser.getUserId() == loginUser.getId()) {
                continue;
            }
            List<String> userTagList = gson.fromJson(userTags, new TypeToken<List<String>>() {
            }.getType());

            Long dist = (long) AlgorithmUtils.minDistance(tagList, userTagList);
            list.add(Pair.of(buddyUser, dist));
        }

        List<Pair<BuddyUser, Long>> topUserPairList = list.stream()
                .sorted((a, b) -> (int) (a.getValue() - b.getValue()))
                .limit(num)
                .collect(Collectors.toList());

        List<Long> userIdList = topUserPairList.stream()
                        .map(pair -> pair.getKey().getId())
                        .collect(Collectors.toList());

        CustomSpecs<BuddyUser> customSpecs2 = new CustomSpecs<>();  
        customSpecs2._in("id", userIdList);              
        Map<Long, List<BuddyUser>> userIdUserListMap = repository.findAll(customSpecs2._generateSpecifications())
                            .stream()
                            .map(buddyUser -> getSafetyUser(Optional.of(buddyUser)))
                            .collect(Collectors.groupingBy(BuddyUser::getId));

        List<BuddyUser> finalUserList = new ArrayList<>();
        for (Long userId : userIdList) {
            finalUserList.add(userIdUserListMap.get(userId).get(0));
        }

        return CollectionModel.of(finalUserList);
    }

    public Optional<BuddyUser> getUserByUserId(Long userId) {
        CustomSpecs<BuddyUser> customSpecs = new CustomSpecs<>();
        customSpecs._equal("userId", userId);

        return repository.findOne(customSpecs._generateSpecifications());
    }

}