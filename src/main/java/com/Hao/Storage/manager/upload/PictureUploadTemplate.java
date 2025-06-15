package com.Hao.Storage.manager.upload;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.CIObject;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import com.qcloud.cos.model.ciModel.persistence.ProcessResults;
import com.Hao.Storage.config.CosClientConfig;
import com.Hao.Storage.exception.BusinessException;
import com.Hao.Storage.exception.ErrorCode;
import com.Hao.Storage.manager.CosManager;
import com.Hao.Storage.model.dto.file.UploadPictureResult;
import com.Hao.Storage.utils.OptimizedMainColorCalculator;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

@Slf4j
public abstract class PictureUploadTemplate {

    @Resource
    protected CosManager cosManager;

    @Resource
    protected CosClientConfig cosClientConfig;

    /**
     * 模板方法，定义上传流程
     */
    public final UploadPictureResult uploadPicture(Object inputSource, String uploadPathPrefix) {
        // 1. 校验图片
        validPicture(inputSource);

        // 2. 图片上传地址
        String uuid = RandomUtil.randomString(16);
        String originFilename = getOriginFilename(inputSource);
        String uploadFilename = String.format("%s_%s.%s", DateUtil.formatDate(new Date()), uuid,
                FileUtil.getSuffix(originFilename));
        String uploadPath = String.format("/%s/%s", uploadPathPrefix, uploadFilename);

        File file = null;
        try {
            // 3. 创建临时文件
            file = File.createTempFile(uploadPath, null);
            log.info("临时文件已创建，路径: {}", file.getAbsolutePath()); // 添加日志记录文件路径
            // 处理文件来源（本地或 URL）
            processFile(inputSource, file);

            // 4. 上传图片到对象存储
            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
            log.info("图片已上传到对象存储，路径: {}", uploadPath); // 添加上传路径日志
            // 5. 封装返回结果
            ImageInfo  imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();

            ProcessResults processResults = putObjectResult.getCiUploadResult().getProcessResults();
            List<CIObject> objectList = processResults.getObjectList();
            UploadPictureResult uploadPictureResult;
            if (CollUtil.isNotEmpty(objectList)) {
                // 获取压缩图、缩略图
                CIObject compressedCiObject = objectList.get(0);
                CIObject thumbnailCiObject  = objectList.size() > 1 ? objectList.get(1) : compressedCiObject;

                // 传入真实的临时文件 file（新增参数）
                uploadPictureResult = buildResult(originFilename,
                        compressedCiObject,
                        thumbnailCiObject,
                        imageInfo,
                        file);
            } else {
                // 没有经过数据万象处理时走原来的逻辑
                uploadPictureResult = buildResult(originFilename,
                        file,
                        uploadPath,
                        imageInfo);
            }
            return uploadPictureResult;
        } catch (Exception e) {
            log.error("图片上传到对象存储失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Upload failed");
        } finally {
            // 6. 清理临时文件
            deleteTempFile(file);
        }
    }

    /**
     * 封装返回结果
     *
     * @param originalFilename   原始文件名
     * @param compressedCiObject 压缩后的对象
     * @param thumbnailCiObject  缩略图对象
     * @param imageInfo          图片信息
     * @return
     */
    private UploadPictureResult buildResult(String originalFilename, CIObject compressedCiObject, CIObject thumbnailCiObject,
                                            ImageInfo imageInfo, File tempFile) {
        // 计算宽高
        int picWidth = compressedCiObject.getWidth();
        int picHeight = compressedCiObject.getHeight();
        double picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
        // 封装返回结果
        UploadPictureResult uploadPictureResult = new UploadPictureResult();
        // 设置压缩后的原图地址
        uploadPictureResult.setUrl(cosClientConfig.getHost() + "/" + compressedCiObject.getKey());
        uploadPictureResult.setPicName(FileUtil.mainName(originalFilename));
        uploadPictureResult.setPicSize(compressedCiObject.getSize().longValue());
        uploadPictureResult.setPicWidth(picWidth);
        uploadPictureResult.setPicHeight(picHeight);
        uploadPictureResult.setPicScale(picScale);
        uploadPictureResult.setPicFormat(compressedCiObject.getFormat());
//        uploadPictureResult.setPicColor(imageInfo.getAve());

        // 计算主色调——关键修复点：使用真正的临时文件
        try {
            log.info("开始计算主色调，临时文件路径: {}", tempFile.getAbsolutePath());
            String mainColor = OptimizedMainColorCalculator.calculateMainColor(tempFile);
            log.info("计算完成的主色调: {}", mainColor);
            uploadPictureResult.setPicColor(mainColor);
        } catch (Exception e) {
            log.error("主色调计算失败，设置为默认值 #000000", e);
            uploadPictureResult.setPicColor("#000000");
        }

        // 设置缩略图地址
        uploadPictureResult.setThumbnailUrl(cosClientConfig.getHost() + "/" + thumbnailCiObject.getKey());
        // 返回可访问的地址
        return uploadPictureResult;
    }


    /**
     * 校验输入源（本地文件或 URL）
     */
    protected abstract void validPicture(Object inputSource);

    /**
     * 获取输入源的原始文件名
     */
    protected abstract String getOriginFilename(Object inputSource);

    /**
     * 处理输入源并生成本地临时文件
     */
    protected abstract void processFile(Object inputSource, File file) throws Exception;

    /**
     * 封装返回结果
     *
     * @param originalFilename
     * @param file
     * @param uploadPath
     * @param imageInfo        对象存储返回的图片信息
     * @return
     */
    private UploadPictureResult buildResult(String originalFilename, File file, String uploadPath, ImageInfo imageInfo) {
        // 计算宽高
        int picWidth = imageInfo.getWidth();
        int picHeight = imageInfo.getHeight();
        double picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
        // 封装返回结果
        UploadPictureResult uploadPictureResult = new UploadPictureResult();
        uploadPictureResult.setUrl(cosClientConfig.getHost() + "/" + uploadPath);
        uploadPictureResult.setPicName(FileUtil.mainName(originalFilename));
        uploadPictureResult.setPicSize(FileUtil.size(file));
        uploadPictureResult.setPicWidth(picWidth);
        uploadPictureResult.setPicHeight(picHeight);
        uploadPictureResult.setPicScale(picScale);
        uploadPictureResult.setPicFormat(imageInfo.getFormat());
//        uploadPictureResult.setPicColor(imageInfo.getAve());

        // 使用自定义主色调计算方法
        try {
            log.info("开始计算主色调，临时文件路径: {}", file.getAbsolutePath());
            String mainColor = OptimizedMainColorCalculator.calculateMainColor(file); // 使用正确的临时文件
            log.info("计算完成的主色调: {}", mainColor);
            uploadPictureResult.setPicColor(mainColor);
        } catch (Exception e) {
            log.error("主色调计算失败，设置为默认值 #000000", e);
            uploadPictureResult.setPicColor("#000000");
        }

        // 返回可访问的地址
        return uploadPictureResult;
    }

    /**
     * 删除临时文件
     */
    public void deleteTempFile(File file) {
        if (file == null) {
            return;
        }
        boolean deleteResult = file.delete();
        if (!deleteResult) {
            log.error("file delete error, filepath = {}", file.getAbsolutePath());
        }
    }
}
