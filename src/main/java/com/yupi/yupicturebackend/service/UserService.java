package com.yupi.yupicturebackend.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.yupicturebackend.model.dto.user.UserQueryRequest;
import com.yupi.yupicturebackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yupicturebackend.model.vo.LoginUserVO;
import com.yupi.yupicturebackend.model.vo.UserVO;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public interface UserService extends IService<User> {
    //用户注册
    long userRegister(String userAccount, String userPassword, String checkPassword);

    //获取加密后的密码
    String getEncryptPassword(String userPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    //获得脱敏后的登录用户信息
    LoginUserVO getLoginUserVO(User user);

    //获得脱敏后的用户信息
    UserVO getUserVO(User user);

    //获得脱敏后的用户信息列表
    List<UserVO> getUserVOList(List<User> userList);

    //获取当前登录用户
    User getLoginUser(HttpServletRequest request);

    //用户注销
    boolean userLogout(HttpServletRequest request);

    //获取查询条件
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    //判断用户是否为管理员
    boolean isAdmin(User user);

}
