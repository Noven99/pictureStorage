package com.Hao.Storage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.Hao.Storage.model.dto.spaceuser.SpaceUserAddRequest;
import com.Hao.Storage.model.dto.spaceuser.SpaceUserQueryRequest;
import com.Hao.Storage.model.entity.SpaceUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Hao.Storage.model.vo.SpaceUserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 86182
 * @description 针对表【space_user(空间用户关联)】的数据库操作Service
 * @createDate 2025-06-07 11:31:46
 */
public interface SpaceUserService extends IService<SpaceUser> {

    //创建空间成员
    long addSpaceUser(SpaceUserAddRequest spaceUserAddRequest);

    //校验空间成员
    void validSpaceUser(SpaceUser spaceUser, boolean add);

    
    //获得单个空间成员封装
    SpaceUserVO getSpaceUserVO(SpaceUser spaceUser, HttpServletRequest request);

    //列表获取空间成员封装
    List<SpaceUserVO> getSpaceUserVOList( List<SpaceUser> spaUsererList);

    //获取查询对象
    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);


    SpaceUser getSpaceUser(Long spaceId, Long userId);
}
