package com.Hao.Storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Hao.Storage.model.dto.sapce.analyze.*;
import com.Hao.Storage.model.entity.Space;
import com.Hao.Storage.model.entity.User;
import com.Hao.Storage.model.vo.space.analyze.*;

import java.util.List;

/**
 * @author 86182
 * @description 针对表【space(空间)】的数据库操作Service
 * @createDate 2025-06-02 17:00:56
 */
public interface SpaceAnalyzeService extends IService<Space> {


    // 获取空间使用情况
    SpaceUsageAnalyzeResponse getSpaceUsageAnalyze(SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest, User loginUser);


    //获取空间图片分类使用分析数据
    List<SpaceCategoryAnalyzeResponse> getSpaceCategoryAnalyze(SpaceCategoryAnalyzeRequest spaceCategoryAnalyzeRequest, User loginUser);

    //  空间图片标签分析
    List<SpaceTagAnalyzeResponse> getSpaceTagAnalyze(SpaceTagAnalyzeRequest spaceTagAnalyzeRequest, User loginUser);

    //空间图片大小分析
    List<SpaceSizeAnalyzeResponse> getSpaceSizeAnalyze(SpaceSizeAnalyzeRequest spaceSizeAnalyzeRequest, User loginUser);

    //空间用户上传行为分析
    List<SpaceUserAnalyzeResponse> getSpaceUserAnalyze(SpaceUserAnalyzeRequest spaceUserAnalyzeRequest, User loginUser);

    //空间使用排行分析
    List<Space> getSpaceRankAnalyze(SpaceRankAnalyzeRequest spaceRankAnalyzeRequest, User loginUser);
}
