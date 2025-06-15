package com.Hao.Storage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.Hao.Storage.api.aliyunai.model.CreateOutPaintingTaskResponse;
import com.Hao.Storage.model.dto.picture.*;
import com.Hao.Storage.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Hao.Storage.model.entity.User;
import com.Hao.Storage.model.vo.PictureVO;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 86182
 * @description 针对表【picture(图片)】的数据库操作Service
 * @createDate 2025-05-28 19:35:10
 */
public interface PictureService extends IService<Picture> {
    //上传图片
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
    default void fillReviewParams(Picture picture, User loginUser) {

    }

    void fillReviewParamsPlus(Picture picture, User loginUser);

    //批量抓取和创建图片
    Integer uploadPictureByBatch(
            PictureUploadByBatchRequest pictureUploadByBatchRequest,
            User loginUser
    );

    //清理图片文件
    void clearPictureFile(Picture oldPicture);

    //校验空间图片权限
    void checkPictureAuth(User loginUser, Picture picture);

    void deletePicture(long pictureId, User loginUser);

    void editPicture(PictureEditRequest pictureEditRequest, User loginUser);

    //按照颜色搜图
    List<PictureVO> searchPictureByColor(Long spaceId, String picColor, User loginUser);

    //批量编辑图片
    @Transactional(rollbackFor = Exception.class)
    void editPictureByBatch(PictureEditByBatchRequest pictureEditByBatchRequest, User loginUser);

    //创建扩图任务
    CreateOutPaintingTaskResponse createPictureOutPaintingTask(CreatePictureOutPaintingTaskRequest pictureOutPaintingTaskRequest, User loginUser);
}
