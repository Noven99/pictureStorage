package com.yupi.yupicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yupicturebackend.model.dto.sapce.SpaceAddRequest;
import com.yupi.yupicturebackend.model.dto.sapce.SpaceQueryRequest;
import com.yupi.yupicturebackend.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yupicturebackend.model.entity.User;
import com.yupi.yupicturebackend.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 86182
 * @description 针对表【space(空间)】的数据库操作Service
 * @createDate 2025-06-02 17:00:56
 */
public interface SpaceService extends IService<Space> {
    //创建空间
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    //校验空间
    void validSpace(Space space, boolean add);

    //获取查询对象
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    //获得单个空间封装
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    //分页获取空间封装
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    //根据空间级别填充空间对象
    void fillSpaceBySpaceLevel(Space space);

    //校验空间权限
    void checkSpaceAuth(User loginUser, Space space);

}
