package com.yupi.yupicturebackend.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yupicturebackend.model.dto.picture.*;
import com.yupi.yupicturebackend.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yupicturebackend.model.entity.User;
import com.yupi.yupicturebackend.model.vo.PictureVO;
import com.yupi.yupicturebackend.service.impl.PictureServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 86182
 * @description 针对表【picture(图片)】的数据库操作Service
 * @createDate 2025-05-28 19:35:10
 */
public interface PictureService extends IService<Picture> {
    /**
     * 上传图片
     *
     * @param inputSource
     * @param pictureUploadRequest
     * @param loginUser
     * @return
     */
    PictureVO uploadPicture(Object inputSource,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);


    //获取查询对象
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    //获得单个图片封装
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    //分页获取图片封装
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    void validPicture(Picture picture);


     //图片审核
    void doPictureReview(PictureReviewRequest pictureReviewRequest, User loginUser);

    //填充审核参数
    void fillReviewParams(Picture picture, User loginUser);

    //批量抓取和创建图片
    Integer uploadPictureByBatch(
            PictureUploadByBatchRequest pictureUploadByBatchRequest,
            User loginUser
    );

}
